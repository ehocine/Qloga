package eac.qloga.android.features.p4p.shared.scenes.choosingNewAddress

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.*
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.address.AddressSearchBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.EnrollmentEvent
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel
import eac.qloga.android.features.p4p.shared.viewmodels.AddressViewModel
import eac.qloga.bare.dto.person.Address
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ChoosingNewAddressScreen(
    navController: NavController,
    viewModel: EnrollmentViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel()
) {
    val infoMsg = "Your address is required to proceed with the order."
    val containerHorizontalPadding = 24.dp
    val enrollmentType = EnrollmentViewModel.enrollmentType.value
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val addressSuggestionsLoadingState by addressViewModel.getAddressSuggestionsLoadingState.collectAsState()
    val addressSuggestions by addressViewModel.addressSuggestionsList
    var expanded by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val currentAddress = viewModel.addressFieldState.value.text

    val addressesList by EnrollmentViewModel.addressesList

    val newAddressList = addressesList.distinctBy { it.shortAddress }
    newAddressList.forEach {
        Log.d("add", it.shortAddress)
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            ExistAddressOptions(
                addressList = viewModel.listOfAddress.value,
                selectedAddress = viewModel.selectedAddressIndex.value,
                onClick = {
                    coroutineScope.launch {
                        modalBottomSheetState.hide()
                        viewModel.onSelectAddressOption(it)
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pScreens.ChoosingNewAddress.titleName,
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
                            onTap = {
                                expanded = false
                                focusManager.clearFocus()
                            }
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
                        Box {
                            ConstraintLayout {
                                val (searchBar, suggestionCard, addresses) = createRefs()
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(3.dp)
                                        .constrainAs(searchBar) {
                                            top.linkTo(parent.bottom)
                                            bottom.linkTo(parent.top)
                                        },
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .onGloballyPositioned {
                                                    parentSize = it.size
                                                }
                                        ) {
                                            AddressSearchBar(
                                                value = currentAddress,
                                                hint = addressViewModel.addressInputFieldState.value.hint,
                                                isFocused = addressViewModel.addressInputFieldState.value.isFocused,
                                                onValueChange = {
                                                    viewModel.onTriggerEvent(
                                                        EnrollmentEvent.EnterAddress(
                                                            it
                                                        )
                                                    )
                                                    addressViewModel.getAddressSuggestions(
                                                        term = it
                                                    )
                                                },
                                                onSubmit = {
                                                },
                                                onClear = { viewModel.onTriggerEvent(EnrollmentEvent.ClearAddress) },
                                                onFocusedChanged = {
                                                    viewModel.onTriggerEvent(
                                                        EnrollmentEvent.FocusAddressInput(
                                                            it
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .clickable {
                                                coroutineScope.launch {
                                                    navController.navigate(P4pScreens.SelectLocationMap.route)
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

                                when (addressSuggestionsLoadingState) {
                                    LoadingState.LOADED -> {
                                        expanded = true
                                        LaunchedEffect(key1 = true) {
                                            addressViewModel.getAddressSuggestionsLoadingState.value =
                                                LoadingState.IDLE
                                        }
                                    }
                                }
                                Column(modifier = Modifier.constrainAs(addresses) {
                                    top.linkTo(
                                        searchBar.bottom
                                    )
                                }) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    // if there are family's addresses
                                    if (addressesList.isNotEmpty()) {
                                        Text(
                                            text = "Or pick your existing address",
                                            style = MaterialTheme.typography.titleMedium,
                                            modifier = Modifier.padding(3.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        ContainerBorderedCard(modifier = Modifier.height(350.dp)) {
                                            Column(
                                                Modifier
                                                    .padding(8.dp)
                                                    .verticalScroll(scrollState)
                                            ) {
                                                newAddressList.forEach { address ->
                                                    AddressItem(
                                                        address = address,
                                                        isSelected = EnrollmentViewModel.selectedAddress.value.shortAddress == address.shortAddress,
                                                        selectAddress = {
                                                            viewModel.onTriggerEvent(
                                                                EnrollmentEvent.EnterAddress(
                                                                    it.shortAddress
                                                                )
                                                            )
                                                            EnrollmentViewModel.selectedAddress.value =
                                                                it
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                if (addressSuggestions.isNotEmpty()) {
                                    SuggestionCard(
                                        modifier = Modifier
                                            .constrainAs(suggestionCard) {
                                                top.linkTo(searchBar.bottom)
                                            }
                                            .verticalScroll(scrollState),
                                        width = with(LocalDensity.current) { parentSize.width.toDp() },
                                        roundedCornerShape = RoundedCornerShape(8.dp),
                                        expanded = expanded,
                                    ) {
                                        addressSuggestions.forEach { addressSuggestion ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        expanded = false
                                                        viewModel.onTriggerEvent(
                                                            EnrollmentEvent.EnterAddress(
                                                                addressSuggestion.address
                                                            )
                                                        )
                                                        AddressViewModel.selectedAddressSuggestion.value =
                                                            addressSuggestion
                                                        EnrollmentViewModel.addressSaved.value =
                                                            false
                                                        AddressViewModel.searchAddress.value =
                                                            true
                                                        coroutineScope.launch {
                                                            navController.navigate(
                                                                P4pScreens.SaveNewAddress.route
                                                            )
                                                        }
                                                    }
                                                    .padding(
                                                        start = 12.dp,
                                                        end = 12.dp,
                                                        top = 8.dp,
                                                        bottom = 8.dp
                                                    )
                                            ) {
                                                androidx.compose.material.Text(
                                                    modifier = Modifier,
                                                    text = addressSuggestion.address,
                                                    style = MaterialTheme.typography.titleSmall,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //next button
                FullRoundedButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = containerHorizontalPadding, vertical = 16.dp),
                    buttonText = "Next",
                    textColor = MaterialTheme.colorScheme.background,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                ) {
                    coroutineScope.launch {
                        EnrollmentViewModel.addressSaved.value = true
                        navController.navigate(P4pScreens.SaveNewAddress.route)
                    }
                }
            }
        }
    }
}

@Composable
fun AddressItem(
    address: Address,
    isSelected: Boolean,
    selectAddress: (selectedAddress: Address) -> Unit
) {
    Text(
        text = address.shortAddress,
        style = MaterialTheme.typography.titleMedium,
        color = if (isSelected) green1 else Color.Black,
        modifier = Modifier
            .clickable { selectAddress(address) }
            .padding(8.dp)
    )
}