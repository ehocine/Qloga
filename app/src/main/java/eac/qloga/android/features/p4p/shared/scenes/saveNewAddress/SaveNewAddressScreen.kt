package eac.qloga.android.features.p4p.shared.scenes.saveNewAddress

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.DotCircleArcCanvas
import eac.qloga.android.core.shared.components.DottedLine
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.components.address.AddressCard
import eac.qloga.android.core.shared.components.address.AddressSearchBar
import eac.qloga.android.core.shared.theme.dangerRed
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.ParkingType
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.EnrollmentEvent
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.ParkingSelection
import eac.qloga.android.features.p4p.showroom.shared.viewModels.AddressViewModel
import eac.qloga.bare.dto.person.Address
import eac.qloga.bare.enums.Parking
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SaveNewAddressScreen(
    navController: NavController,
    viewModel: EnrollmentViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val infoMsg = "Address of your business is required to proceed with the order."
    val containerHorizontalPadding = 24.dp
    val focusManager = LocalFocusManager.current

    val currentUser by ApiViewModel.userProfile

    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    val selectedAddress by remember {
        mutableStateOf(EnrollmentViewModel.selectedAddress)
    }
    var oldSelectedAddress by remember { mutableStateOf(Address()) }

    var currentAddress by remember { mutableStateOf(selectedAddress.value.shortAddress) }
    val coroutineScope = rememberCoroutineScope()

    val saveAddressResponse by addressViewModel.saveAddressResponse

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var line1 by remember {
        mutableStateOf(InputFieldState(text = selectedAddress.value.line1 ?: ""))
    }
    var line2 by remember {
        mutableStateOf(InputFieldState(text = selectedAddress.value.line2 ?: ""))
    }
    var line3 by remember {
        mutableStateOf(InputFieldState(text = selectedAddress.value.line3 ?: ""))
    }
    var city by remember {
        mutableStateOf(
            InputFieldState(
                text = selectedAddress.value.town ?: ""
            )
        )
    }
    var postcode by remember {
        mutableStateOf(
            InputFieldState(
                text = selectedAddress.value.postcode ?: ""
            )
        )
    }

    var parking by remember {
        mutableStateOf(
            when (selectedAddress.value.parking) {
                Parking.FREE -> ParkingType.FreeType
                else -> ParkingType.PaidType
            }
        )
    }

    var gpsCoords by remember {
        mutableStateOf(
            selectedAddress.value.lat != null && selectedAddress.value.lat != 0.0
                    && selectedAddress.value.lng != null && selectedAddress.value.lng != 0.0
        )
    }

    val addressSuggestionsLoadingState by addressViewModel.getAddressSuggestionsLoadingState.collectAsState()
    val fullAddressLoadingState by AddressViewModel.fullAddressLoadingState.collectAsState()
    val saveFamilyAddressLoadingState by addressViewModel.saveFamilyAddressLoadingState.collectAsState()
    val updateAddressLoadingState by addressViewModel.updateAddressLoadingState.collectAsState()

    val addressSuggestions by addressViewModel.addressSuggestionsList
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        if (AddressViewModel.searchAddress.value) {
            addressViewModel.getFullAddress(id = AddressViewModel.selectedAddressSuggestion.value.id)
            AddressViewModel.searchAddress.value = false
        }
    }
    var newAddress by remember { mutableStateOf(Address()) }
    when (fullAddressLoadingState) {
        LoadingState.LOADED -> {
            line1 = InputFieldState(text = AddressViewModel.fullAddress.value.line1, hint = "Line 1")
            line2 = InputFieldState(text = AddressViewModel.fullAddress.value.line2, hint = "Line 2")
            line3 = InputFieldState(text = AddressViewModel.fullAddress.value.line3, hint = "Line 3")
            city = InputFieldState(text = AddressViewModel.fullAddress.value.townOrCity, hint = "City")
            postcode = InputFieldState(text = AddressViewModel.fullAddress.value.postcode,hint = "Postcode")
            LaunchedEffect(key1 = true) {
                AddressViewModel.fullAddressLoadingState.emit(LoadingState.IDLE)
            }
        }
        else -> Unit
    }
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            ParkingSelection(
                onSelect = {
                    oldSelectedAddress = selectedAddress.value
                    EnrollmentViewModel.addressSaved.value = false
                    parking = it
                    coroutineScope.launch {
                        modalBottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
                    }
                },
                selected = parking
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pScreens.SaveNewAddress.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                ) {
                    navController.navigateUp()
                }
            }
        ) { paddingValues ->

            val topPadding = paddingValues.calculateTopPadding()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { focusManager.clearFocus() }
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(topPadding + 4.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .height(20.dp)
                                .align(Alignment.CenterStart)
                                .padding(end = 20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            DottedLine(
                                arcStrokeColor = gray1,
                                vertical = false
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        ) {
                            DotCircleArcCanvas(
                                arcStrokeColor = gray1,
                                circleColor = MaterialTheme.colorScheme.primary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .height(20.dp)
                                .align(Alignment.CenterEnd)
                                .padding(start = 20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            DottedLine(
                                arcStrokeColor = gray1,
                                vertical = false
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(modifier = Modifier.padding(horizontal = containerHorizontalPadding)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, gray1, RoundedCornerShape(16.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = infoMsg,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned {
                                        parentSize = it.size
                                    }) {
                                    AddressSearchBar(
                                        value = currentAddress,
                                        hint = addressViewModel.addressInputFieldState.value.hint,
                                        isFocused = addressViewModel.addressInputFieldState.value.isFocused,
                                        onValueChange = {
                                            currentAddress = it
                                            addressViewModel.getAddressSuggestions(
                                                term = it
                                            )
                                        },
                                        onSubmit = {

                                        },
                                        onClear = { currentAddress = "" },
                                        onFocusedChanged = {
                                            viewModel.onTriggerEvent(
                                                EnrollmentEvent.FocusAddressInput(
                                                    it
                                                )
                                            )
                                        }
                                    )
                                    when (addressSuggestionsLoadingState) {
                                        LoadingState.LOADED -> {
                                            expanded = true
                                            LaunchedEffect(key1 = true) {
                                                addressViewModel.getAddressSuggestionsLoadingState.value =
                                                    LoadingState.IDLE
                                            }
                                        }
                                    }

                                    if (addressSuggestions.isNotEmpty()) {
                                        DropdownMenu(
                                            modifier = Modifier
                                                .width(with(LocalDensity.current) { parentSize.width.toDp() }),
                                            expanded = expanded,
                                            properties = PopupProperties(focusable = false),
                                            onDismissRequest = { expanded = false }
                                        ) {
                                            addressSuggestions.forEach { addressSuggestion ->
                                                DropdownMenuItem(
                                                    onClick = {
                                                        expanded = false
                                                        currentAddress = addressSuggestion.address
                                                        oldSelectedAddress = selectedAddress.value
                                                        AddressViewModel.selectedAddressSuggestion.value =
                                                            addressSuggestion
                                                        addressViewModel.getFullAddress(id = addressSuggestion.id)
                                                        EnrollmentViewModel.addressSaved.value =
                                                            false
                                                    }
                                                ) {
                                                    androidx.compose.material.Text(text = addressSuggestion.address)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        coroutineScope.launch {
                                            if (gpsCoords && EnrollmentViewModel.addressSaved.value) {
                                                navController.navigate(P4pScreens.SelectLocationMap.route)
                                            } else {
                                                if (!EnrollmentViewModel.addressSaved.value) Toast
                                                    .makeText(
                                                        context,
                                                        "Save the address first",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                                if (!gpsCoords) Toast
                                                    .makeText(
                                                        context,
                                                        "GPS coords invalid",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            }
                                        }
                                    }
                                    .padding(4.dp)
                            ) {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(
                                        id = R.drawable.ic_location_point
                                    ),
                                    contentDescription = ""
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        AddressCard(
                            parkingType = parking,
                            line1State = line1,
                            line2State = line2,
                            line3State = line3,
                            cityState = city,
                            postcodeState = postcode,
                            onChangeLine1 = {
                                oldSelectedAddress = selectedAddress.value
                                EnrollmentViewModel.addressSaved.value = false
                                line1 = InputFieldState(text = it)
                            },
                            onChangeLine3 = {
                                oldSelectedAddress = selectedAddress.value
                                EnrollmentViewModel.addressSaved.value = false
                                line3 = InputFieldState(text = it)
                            },
                            onChangeCity = {
                                oldSelectedAddress = selectedAddress.value
                                EnrollmentViewModel.addressSaved.value = false
                                city = InputFieldState(text = it)
                            },
                            onChangeLine2 = {
                                oldSelectedAddress = selectedAddress.value
                                EnrollmentViewModel.addressSaved.value = false
                                line2 = InputFieldState(text = it)
                            },
                            onChangePostcode = {
                                oldSelectedAddress = selectedAddress.value
                                EnrollmentViewModel.addressSaved.value = false
                                postcode = InputFieldState(text = it)
                            },
                            onFocusLine1 = {
                            },
                            onFocusLine3 = {
                            },
                            onFocusCity = {
                            },
                            onFocusLine2 = {
                            },
                            onFocusPostcode = {
                            },
                            onClickParkingType = {
                                coroutineScope.launch {
                                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            },
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (!gpsCoords) {
                            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "",
                                        tint = dangerRed
                                    )
                                    Spacer(modifier = Modifier.padding(4.dp))
                                    Text(
                                        text = "No GPS coords",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = dangerRed
                                    )
                                }

                            }
                        }
                    }
                }
                //next button
                when {
                    saveFamilyAddressLoadingState == LoadingState.LOADING || updateAddressLoadingState == LoadingState.LOADING -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            color = green1
                        )
                    }
                    saveFamilyAddressLoadingState == LoadingState.LOADED || updateAddressLoadingState == LoadingState.LOADED -> {
                        EnrollmentViewModel.addressSaved.value = true
                        EnrollmentViewModel.selectedAddress.value =
                            addressViewModel.saveAddressResponse.value
                        selectedAddress.value = addressViewModel.saveAddressResponse.value



                        gpsCoords =
                            ((saveAddressResponse.lat != null) && (saveAddressResponse.lat != 0.0)
                                    && (saveAddressResponse.lng != null) && (saveAddressResponse.lng != 0.0))

                        LaunchedEffect(key1 = true) {
                            viewModel.getAddresses(currentUser.familyId)
                            addressViewModel.saveFamilyAddressLoadingState.emit(LoadingState.IDLE)
                            addressViewModel.updateAddressLoadingState.emit(LoadingState.IDLE)
                        }
                    }
                    else -> {
                        FullRoundedButton(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(
                                    horizontal = containerHorizontalPadding,
                                    vertical = 16.dp
                                ),
                            buttonText = if (!EnrollmentViewModel.addressSaved.value) "Save" else "Proceed",
                            enabled = gpsCoords,
                            textColor = MaterialTheme.colorScheme.background,
                            backgroundColor = MaterialTheme.colorScheme.primary,
                        ) {
                            if (!EnrollmentViewModel.addressSaved.value) {
                                val parkingType = when (parking.label) {
                                    "Free" -> Parking.FREE
                                    else -> Parking.PAID
                                }
                                if (line1.text != oldSelectedAddress.line1
                                    && postcode.text != oldSelectedAddress.postcode
                                ) {
                                    //If the user enters a whole different address, we save this new
                                    newAddress = Address(
                                        currentUser.familyId,
                                        "GB",
                                        line1.text,
                                        line2.text,
                                        line3.text,
                                        AddressViewModel.fullAddress.value.line4,
                                        city.text,
                                        postcode.text,
                                        AddressViewModel.fullAddress.value.latitude,
                                        AddressViewModel.fullAddress.value.longitude,
                                        parkingType,
                                        "",
                                        0L,
                                        currentUser.verifications,
                                        true
                                    )
                                    addressViewModel.onSaveNewAddress(newAddress)
                                } else {
                                    // If the user is trying to correct the existing address, we update it
                                    selectedAddress.value.parking = parkingType
                                    selectedAddress.value.line1 = line1.text
                                    selectedAddress.value.line2 = line2.text
                                    selectedAddress.value.line3 = line3.text
                                    selectedAddress.value.town = city.text
                                    selectedAddress.value.postcode = postcode.text
                                    addressViewModel.updateAddress(selectedAddress.value)
                                }
                            } else {
                                if (gpsCoords) {
                                    addressViewModel.switchToAddress(selectedAddress.value.id)
                                    coroutineScope.launch {
                                        navController.navigate(P4pScreens.IdVerification.route)
                                    }
                                    apiViewModel.getUserProfile()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "GPS coords invalid",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}