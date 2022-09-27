package eac.qloga.android.features.p4p.showroom.scenes.addAddress

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
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
import eac.qloga.android.core.shared.components.SaveButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.components.address.AddressCard
import eac.qloga.android.core.shared.components.address.AddressSearchBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.shared.models.address_suggestions.Suggestion
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.ParkingSelection
import eac.qloga.android.features.p4p.showroom.shared.components.SearchBar
import eac.qloga.android.features.p4p.showroom.shared.viewModels.AddressViewModel
import eac.qloga.android.features.platform.landing.scenes.noAddress.NoAddressEvent
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
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val containerHorizontalPadding = 24.dp

    val activity = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val addressSuggestionsLoadingState by viewModel.getAddressSuggestionsLoadingState.collectAsState()
    val addressSuggestions by viewModel.addressSuggestionsList

    var expanded by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    val currentUser by ApiViewModel.userProfile

    val fullAddressLoadingState by viewModel.fullAddressLoadingState.collectAsState()

    val saveFamilyAddressLoadingState by viewModel.saveFamilyAddressLoadingState.collectAsState()
    val switchToAddressLoadingState by viewModel.switchToAddressLoadingState.collectAsState()
    val userProfileLoadingState by apiViewModel.userProfileLoadingState.collectAsState()

    var parkingType by remember {
        mutableStateOf(viewModel.parkingType.value)
    }

    var postCodeState by remember {
        mutableStateOf(viewModel.postCodeState.value)
    }
    var townState by remember {
        mutableStateOf(viewModel.townState.value)
    }
    var streetState by remember {
        mutableStateOf(viewModel.streetState.value)
    }
    var buildingState by remember {
        mutableStateOf(viewModel.buildingState.value)
    }
    var apartmentsState by remember {
        mutableStateOf(viewModel.apartmentsState.value)
    }

    LaunchedEffect(Unit){
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    LaunchedEffect(key1 = true) {
        if (AddressViewModel.selectedAddressSuggestion.value.id.isNotEmpty()) {
            viewModel.getFullAddress(id = AddressViewModel.selectedAddressSuggestion.value.id)
        } else {
            viewModel.getAddressSuggestions(AddressViewModel.selectedAddressSuggestion.value.address)
        }
    }
    LaunchedEffect(key1 = true) {
        apiViewModel.userProfileLoadingState.emit(LoadingState.IDLE)
    }

    if (fullAddressLoadingState == LoadingState.LOADED) {
        parkingType = viewModel.parkingType.value
        postCodeState = InputFieldState(text = AddressViewModel.fullAddress.value.postcode)
        townState = InputFieldState(text = AddressViewModel.fullAddress.value.townOrCity)
        streetState = InputFieldState(text = AddressViewModel.fullAddress.value.district)
        buildingState = InputFieldState(text = AddressViewModel.fullAddress.value.buildingNumber)
        apartmentsState =
            InputFieldState(text = AddressViewModel.fullAddress.value.subBuildingNumber)
    }
    var newAddress by remember { mutableStateOf(Address()) }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            ParkingSelection(
                onSelect = { viewModel.onClickParkingType(it) },
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
                            isLoading = saveFamilyAddressLoadingState == LoadingState.LOADING
                                    || switchToAddressLoadingState == LoadingState.LOADING,
                            onClick = {
                                val parking = when (parkingType.label) {
                                    "Free" -> Parking.FREE
                                    else -> Parking.PAID
                                }
                                newAddress = Address(
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
                                    parking,
                                    "",
                                    0L,
                                    currentUser.verifications,
                                    true
                                )
                                viewModel.onSaveNewAddress(newAddress = newAddress)
                            })
                    }
                ) {
                    val isLoading = saveFamilyAddressLoadingState == LoadingState.LOADING
                            || switchToAddressLoadingState == LoadingState.LOADING

                    if (!isLoading) {
                        navController.navigateUp()
                    }
                }
            }
        ) { paddingValues ->
            when (saveFamilyAddressLoadingState) {
                LoadingState.LOADING -> Unit
                LoadingState.LOADED -> {
                    LaunchedEffect(key1 = true) {
                        viewModel.switchToAddress(viewModel.saveAddressResponse.value.id)
                    }
                    when (switchToAddressLoadingState) {
                        LoadingState.LOADING -> Unit
                        LoadingState.LOADED -> {
                            LaunchedEffect(key1 = true) {
                                apiViewModel.getUserProfile()
                            }
                            when (userProfileLoadingState) {
                                LoadingState.LOADING -> Unit
                                LoadingState.LOADED -> {
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
                                        // getaddress.IO dropdown
                                        /*
                                    if(viewModel.addressInputFieldState.value.text.isNotEmpty()){
                                        coroutineScope.launch {
                                            navController.navigate(Screen.AddressSearchResultScreen.route)
                                        }
                                    }
                                    viewModel.onTriggerEvent(AddressEvent.Search)

                                     */
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
                                    coroutineScope.launch {
                                        navController.navigate(P4pShowroomScreens.AddressOnMap.route)
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

                    Spacer(modifier = Modifier.height(16.dp))

                    AddressCard(
                        parkingType = parkingType,
                        postcodeState = postCodeState,
                        streetState = streetState,
                        apartmentsState = apartmentsState,
                        townState = townState,
                        buildingState = buildingState,
                        onChangePostcode = { viewModel.onTriggerEvent(AddressEvent.EnterPostcode(it)) },
                        onChangeStreet = { viewModel.onTriggerEvent(AddressEvent.EnterStreet(it)) },
                        onChangeBuilding = { viewModel.onTriggerEvent(AddressEvent.EnterBuilding(it)) },
                        onChangeTown = { viewModel.onTriggerEvent(AddressEvent.EnterTown(it)) },
                        onChangeApartments = {
                            viewModel.onTriggerEvent(
                                AddressEvent.EnterApartments(
                                    it
                                )
                            )
                        },
                        onFocusPostcode = { viewModel.onTriggerEvent(AddressEvent.FocusPostcode(it)) },
                        onFocusStreet = { viewModel.onTriggerEvent(AddressEvent.FocusStreet(it)) },
                        onFocusBuilding = { viewModel.onTriggerEvent(AddressEvent.FocusBuilding(it)) },
                        onFocusTown = { viewModel.onTriggerEvent(AddressEvent.FocusTown(it)) },
                        onFocusApartments = {
                            viewModel.onTriggerEvent(
                                AddressEvent.FocusApartments(
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
                }
            }
        }
    }
}