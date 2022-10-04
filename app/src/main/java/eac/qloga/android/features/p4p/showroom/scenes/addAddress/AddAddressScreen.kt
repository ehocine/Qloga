package eac.qloga.android.features.p4p.showroom.scenes.addAddress

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.SaveButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.components.address.AddressCard
import eac.qloga.android.core.shared.components.address.AddressSearchBar
import eac.qloga.android.core.shared.theme.dangerRed
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.ParkingSelection
import eac.qloga.android.features.p4p.showroom.shared.viewModels.AddressViewModel
import eac.qloga.bare.dto.person.Address
import eac.qloga.bare.enums.Parking
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AddAddressScreen(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val containerHorizontalPadding = 24.dp

    val activity = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val addressSuggestionsLoadingState by viewModel.getAddressSuggestionsLoadingState.collectAsState()
    val addressSuggestions by viewModel.addressSuggestionsList
    val currentUser by ApiViewModel.userProfile
    var expanded by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    val selectedAddress by remember {
        mutableStateOf(
            AddressViewModel.selectedAddress
        )
    }

    val fullAddressLoadingState by AddressViewModel.fullAddressLoadingState.collectAsState()

    val saveFamilyAddressLoadingState by viewModel.saveFamilyAddressLoadingState.collectAsState()
    val updateAddressLoadingState by viewModel.updateAddressLoadingState.collectAsState()
    val switchToAddressLoadingState by viewModel.switchToAddressLoadingState.collectAsState()
    val userProfileLoadingState by apiViewModel.userProfileLoadingState.collectAsState()

    var oldSelectedAddress by remember { mutableStateOf(Address()) }

    var parkingType by remember {
        mutableStateOf(viewModel.parkingType.value)
    }

    var postCodeState by remember {
        mutableStateOf(viewModel.postCodeState.value)
    }
    var line1State by remember {
        mutableStateOf(viewModel.line1State.value)
    }
    var line2State by remember {
        mutableStateOf(viewModel.line2State.value)
    }
    var cityState by remember {
        mutableStateOf(viewModel.cityState.value)
    }
    var line3State by remember {
        mutableStateOf(viewModel.line3State.value)
    }


    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    LaunchedEffect(key1 = Unit) {
        if (AddressViewModel.searchAddress.value) {
            if (AddressViewModel.selectedAddressSuggestion.value.id.isNotEmpty()) {
                viewModel.getFullAddress(id = AddressViewModel.selectedAddressSuggestion.value.id)
            } else {
                viewModel.getAddressSuggestions(AddressViewModel.selectedAddressSuggestion.value.address)
            }
            AddressViewModel.searchAddress.value = false
        }
    }
    LaunchedEffect(key1 = true) {
        apiViewModel.userProfileLoadingState.emit(LoadingState.IDLE)
    }

    when (fullAddressLoadingState) {
        LoadingState.LOADED -> {
            selectedAddress.value =
                Address(
                    currentUser.familyId,
                    "GB",
                    AddressViewModel.fullAddress.value.line1,
                    AddressViewModel.fullAddress.value.line2,
                    AddressViewModel.fullAddress.value.line3,
                    AddressViewModel.fullAddress.value.line4,
                    AddressViewModel.fullAddress.value.townOrCity,
                    AddressViewModel.fullAddress.value.postcode,
                    AddressViewModel.fullAddress.value.latitude,
                    AddressViewModel.fullAddress.value.longitude,
                    Parking.FREE,
                    "",
                    0L,
                    currentUser.verifications,
                    true
                )

            parkingType = viewModel.parkingType.value
            postCodeState = InputFieldState(
                text = AddressViewModel.fullAddress.value.postcode,
                hint = "Postcode"
            )
            line1State =
                InputFieldState(text = AddressViewModel.fullAddress.value.line1, hint = "Line 1")
            line2State =
                InputFieldState(text = AddressViewModel.fullAddress.value.line2, hint = "Line 2")
            cityState =
                InputFieldState(text = AddressViewModel.fullAddress.value.townOrCity, hint = "City")
            line3State =
                InputFieldState(text = AddressViewModel.fullAddress.value.line3, hint = "Line 3")

            LaunchedEffect(key1 = true) {
                AddressViewModel.fullAddressLoadingState.emit(LoadingState.IDLE)
            }
        }
    }
    var newAddress by remember { mutableStateOf(Address()) }

    var gpsCoords by remember {
        mutableStateOf(
            AddressViewModel.fullAddress.value.latitude != 0.0 && AddressViewModel.fullAddress.value.longitude != 0.0
        )
    }
    val saveAddressResponse by viewModel.saveAddressResponse

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            ParkingSelection(
                onSelect = {
                    AddressViewModel.addressSaved.value = false
                    parkingType = it
                    viewModel.onClickParkingType(it)
                    coroutineScope.launch {
                        modalBottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
                    }
                },
                selected = viewModel.parkingType.value
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pShowroomScreens.AddAddress.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    actions = {
                        SaveButton(
                            isLoading = switchToAddressLoadingState == LoadingState.LOADING,
                            onClick = {
                                if (!loading && AddressViewModel.addressSaved.value) {
                                    coroutineScope.launch {
                                        apiViewModel.getUserProfile()
                                    }
                                } else {
                                    if (!AddressViewModel.addressSaved.value) Toast.makeText(
                                        context,
                                        "Save the address first",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    }
                ) {
                    val isLoading = switchToAddressLoadingState == LoadingState.LOADING

                    if (!isLoading) {
                        navController.navigateUp()
                    }
                }
            }
        ) { paddingValues ->
            when (switchToAddressLoadingState) {
                LoadingState.LOADING -> loading = true
                LoadingState.LOADED -> {
                    loading = false
                    when (userProfileLoadingState) {
                        LoadingState.LOADING -> loading = true
                        LoadingState.LOADED -> {
                            loading = false
                            navController.navigate(P4pShowroomScreens.NotEnrolled.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                        else -> Unit
                    }
                }
                else -> Unit
            }
            val titleBarHeight = paddingValues.calculateTopPadding()
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = containerHorizontalPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(titleBarHeight))
                    Spacer(modifier = Modifier.height(containerTopPadding))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                                    value = viewModel.addressInputFieldState.value.text,
                                    hint = viewModel.addressInputFieldState.value.hint,
                                    isFocused = viewModel.addressInputFieldState.value.isFocused,
                                    onValueChange = {
                                        viewModel.onTriggerEvent(AddressEvent.EnterText(it))
                                        viewModel.getAddressSuggestions(
                                            term = it
                                        )
                                    },
                                    onSubmit = {
                                    },
                                    onClear = { viewModel.onTriggerEvent(AddressEvent.ClearInput) },
                                    onFocusedChanged = {
                                        viewModel.onTriggerEvent(
                                            AddressEvent.FocusInput(
                                                it
                                            )
                                        )
                                    }
                                )
                                when (addressSuggestionsLoadingState) {
                                    LoadingState.LOADED -> {
                                        expanded = true
                                        LaunchedEffect(key1 = true) {
                                            viewModel.getAddressSuggestionsLoadingState.value =
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
                                                    viewModel.onTriggerEvent(
                                                        AddressEvent.AddressChosen(
                                                            addressSuggestion.address
                                                        )
                                                    )
                                                    AddressViewModel.selectedAddressSuggestion.value =
                                                        addressSuggestion
                                                    viewModel.getFullAddress(id = addressSuggestion.id)
                                                    AddressViewModel.addressSaved.value = false
                                                }
                                            ) {
                                                Text(text = addressSuggestion.address)
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
                                    if (AddressViewModel.addressSaved.value) {
                                        coroutineScope.launch {
                                            if (gpsCoords && AddressViewModel.addressSaved.value) {
                                                navController.navigate(P4pShowroomScreens.AddressOnMap.route)
                                            } else {
                                                if (!AddressViewModel.addressSaved.value) Toast
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
                                }
                                .padding(4.dp)
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_location_point
                                ),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(color = if (AddressViewModel.addressSaved.value) green1 else gray30)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AddressCard(
                        parkingType = parkingType,
                        line1State = line1State,
                        line2State = line2State,
                        line3State = line3State,
                        postcodeState = postCodeState,
                        cityState = cityState,
                        onChangeLine1 = {
                            oldSelectedAddress = selectedAddress.value
                            line1State = InputFieldState(text = it)
                            viewModel.onTriggerEvent(AddressEvent.EnterLine1(it))
                            AddressViewModel.addressSaved.value = false
                        },
                        onChangeLine3 = {
                            oldSelectedAddress = selectedAddress.value
                            line3State = InputFieldState(text = it)
                            viewModel.onTriggerEvent(AddressEvent.EnterLine3(it))
                            AddressViewModel.addressSaved.value = false
                        },
                        onChangeCity = {
                            oldSelectedAddress = selectedAddress.value
                            cityState = InputFieldState(text = it)
                            viewModel.onTriggerEvent(AddressEvent.EnterCity(it))
                            AddressViewModel.addressSaved.value = false
                        },
                        onChangeLine2 = {
                            oldSelectedAddress = selectedAddress.value
                            line2State = InputFieldState(text = it)
                            viewModel.onTriggerEvent(AddressEvent.EnterLine2(it))
                            AddressViewModel.addressSaved.value = false
                        },
                        onChangePostcode = {
                            oldSelectedAddress = selectedAddress.value
                            postCodeState = InputFieldState(text = it)
                            viewModel.onTriggerEvent(
                                AddressEvent.EnterPostcode(
                                    it
                                )
                            )
                            AddressViewModel.addressSaved.value = false
                        },
                        onFocusLine1 = { viewModel.onTriggerEvent(AddressEvent.FocusLine1(it)) },
                        onFocusLine3 = { viewModel.onTriggerEvent(AddressEvent.FocusLine3(it)) },
                        onFocusCity = { viewModel.onTriggerEvent(AddressEvent.FocusCity(it)) },
                        onFocusLine2 = { viewModel.onTriggerEvent(AddressEvent.FocusLine2(it)) },
                        onFocusPostcode = {
                            viewModel.onTriggerEvent(
                                AddressEvent.FocusPostcode(
                                    it
                                )
                            )
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
                                androidx.compose.material3.Text(
                                    text = "No GPS coords",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = dangerRed
                                )
                            }
                        }
                    }
                }
                when {
                    saveFamilyAddressLoadingState == LoadingState.LOADING || updateAddressLoadingState == LoadingState.LOADING -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            color = green1
                        )
                    }
                    saveFamilyAddressLoadingState == LoadingState.LOADED || updateAddressLoadingState == LoadingState.LOADED -> {
                        if (saveFamilyAddressLoadingState == LoadingState.LOADED) AddressViewModel.hasAddressSaved.value =
                            true
                        oldSelectedAddress = selectedAddress.value
                        AddressViewModel.selectedAddress.value = viewModel.saveAddressResponse.value
                        selectedAddress.value = viewModel.saveAddressResponse.value

                        viewModel.onTriggerEvent(
                            AddressEvent.EnterLine1(
                                selectedAddress.value.line1 ?: ""
                            )
                        )
                        viewModel.onTriggerEvent(
                            AddressEvent.EnterLine2(
                                selectedAddress.value.line2 ?: ""
                            )
                        )
                        viewModel.onTriggerEvent(
                            AddressEvent.EnterLine3(
                                selectedAddress.value.line3 ?: ""
                            )
                        )
                        viewModel.onTriggerEvent(
                            AddressEvent.EnterCity(
                                selectedAddress.value.town ?: ""
                            )
                        )
                        viewModel.onTriggerEvent(
                            AddressEvent.EnterPostcode(
                                selectedAddress.value.postcode ?: ""
                            )
                        )

                        gpsCoords =
                            saveAddressResponse.lat != null && saveAddressResponse.lat != 0.0
                                    && saveAddressResponse.lng != null && saveAddressResponse.lng != 0.0

                        AddressViewModel.addressSaved.value = true

                        LaunchedEffect(key1 = true) {
                            viewModel.saveFamilyAddressLoadingState.emit(LoadingState.IDLE)
                            viewModel.updateAddressLoadingState.emit(LoadingState.IDLE)
                        }
                        if (gpsCoords && AddressViewModel.addressSaved.value) {
                            LaunchedEffect(key1 = true) {
                                viewModel.switchToAddress(viewModel.saveAddressResponse.value.id)
                            }
                        } else {
                            if (!AddressViewModel.addressSaved.value) Toast.makeText(
                                context,
                                "Error saving the address, try again",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (!gpsCoords) Toast.makeText(
                                context,
                                "GPS coords invalid",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else -> {
                        Buttons.FullRoundedButton(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(
                                    horizontal = containerHorizontalPadding,
                                    vertical = 16.dp
                                ),
                            enabled = !AddressViewModel.addressSaved.value,
                            buttonText = "Save",
                            textColor = MaterialTheme.colorScheme.background,
                            backgroundColor = MaterialTheme.colorScheme.primary,
                        ) {
                            if (!AddressViewModel.addressSaved.value) {
                                val parking = when (parkingType.label) {
                                    "Free" -> Parking.FREE
                                    else -> Parking.PAID
                                }
                                if (line1State.text != oldSelectedAddress.line1
                                    && postCodeState.text != oldSelectedAddress.postcode
                                    || !AddressViewModel.hasAddressSaved.value
                                ) {
                                    newAddress = Address(
                                        currentUser.familyId,
                                        "GB",
                                        line1State.text,
                                        line2State.text,
                                        line3State.text,
                                        AddressViewModel.fullAddress.value.line4,
                                        cityState.text,
                                        postCodeState.text,
                                        AddressViewModel.fullAddress.value.latitude,
                                        AddressViewModel.fullAddress.value.longitude,
                                        parking,
                                        "",
                                        0L,
                                        currentUser.verifications,
                                        true
                                    )
                                    viewModel.onSaveNewAddress(newAddress = newAddress)
                                } else {
                                    // If the user is trying to correct the existing address, we update it
                                    selectedAddress.value.parking = parking
                                    selectedAddress.value.line1 = line1State.text
                                    selectedAddress.value.line2 = line2State.text
                                    selectedAddress.value.line3 = line3State.text
                                    selectedAddress.value.town = cityState.text
                                    selectedAddress.value.postcode = postCodeState.text
                                    viewModel.updateAddress(selectedAddress.value)
                                }
                            } else {
                                Toast.makeText(context, "Address already saved", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }
}