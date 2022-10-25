package eac.qloga.android.features.p4p.provider.scenes.addPrvAddress

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.SaveButton
import eac.qloga.android.core.shared.components.LocationMarkerIconButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.components.address.AddressSearchBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.enrollment.CountryCodeList
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.AddPrvAddressCard
import eac.qloga.android.features.p4p.showroom.shared.components.ParkingSelection
import kotlinx.coroutines.launch

private enum class BottomSheetType{
    PARKING_TYPE,
    COUNTY_TYPE
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AddPrvAddressScreen(
    navController: NavController,
    viewModel: AccountSettingsViewModel = hiltViewModel()
) {
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val bottomSheetType = remember { mutableStateOf(BottomSheetType.PARKING_TYPE) }

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            if(bottomSheetType.value == BottomSheetType.PARKING_TYPE){
                ParkingSelection(
                    onSelect = { viewModel.onClickParkingType(it) },
                    selected = viewModel.parkingType
                )
            }else{
                CountryCodeList(
                    selectCountryCode = viewModel.selectedCountryCode,
                    countryCodes = viewModel.countries,
                    onSelectCountryCode = { viewModel.onTriggerEvent(AccountSettingsEvent.SelectCountryCode(it))}
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pProviderScreens.AddPrvAddress.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    actions =  {
                        SaveButton(
                            onClick = {
                                viewModel.clearAddressState()
                                coroutineScope.launch { navController.navigateUp() }
                            }
                        )
                    }
                ) {
                    coroutineScope.launch { navController.navigateUp() }
                }
            }
        ) { paddingValues ->
            val titleBarPadding = paddingValues.calculateTopPadding()

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = containerHorizontalPadding)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(titleBarPadding))
                    Spacer(modifier = Modifier.height(containerTopPadding))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ){
                            AddressSearchBar(
                                value = viewModel.addressInputFieldState.text,
                                hint = viewModel.addressInputFieldState.hint,
                                isFocused = viewModel.addressInputFieldState.isFocused,
                                onValueChange = { viewModel.onTriggerEvent(AccountSettingsEvent.EnterAddress(it))},
                                onSubmit = {
                                    if(viewModel.addressInputFieldState.text.isNotEmpty()){
                                        coroutineScope.launch {
                                            navController.navigate(P4pScreens.SearchedAddrResult.route)
                                        }
                                    }
                                    viewModel.onTriggerEvent(AccountSettingsEvent.SearchAddress)
                                },
                                onClear = { viewModel.onTriggerEvent(AccountSettingsEvent.ClearAddressInput) },
                                onFocusedChanged = { viewModel.onTriggerEvent(AccountSettingsEvent.FocusAddressInput(it))}
                            )
                        }
                        LocationMarkerIconButton(
                            onClick = {
                                coroutineScope.launch {
                                    //navController.navigate(Screen.MapView.route)
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    AddPrvAddressCard(
                        parkingType = viewModel.parkingType,
                        checkBusinessOnly = viewModel.isBusinessOnly,
                        postcodeState = viewModel.providerPostCodeState,
                        streetState = viewModel.providerStreetState,
                        apartmentsState = viewModel.providerApartmentsState,
                        townState = viewModel.providerTownState,
                        buildingState = viewModel.providerBuildingState,
                        onChangePostcode = { viewModel.onTriggerEvent( AccountSettingsEvent.EnterProviderPostcode(it)) },
                        onChangeStreet = { viewModel.onTriggerEvent( AccountSettingsEvent.EnterProviderStreet(it)) },
                        onChangeBuilding = { viewModel.onTriggerEvent( AccountSettingsEvent.EnterProviderBuilding(it)) },
                        onChangeTown = { viewModel.onTriggerEvent( AccountSettingsEvent.EnterProviderTown(it))},
                        onChangeApartments = { viewModel.onTriggerEvent(AccountSettingsEvent.EnterProviderApartments(it))},
                        onFocusPostcode = { viewModel.onTriggerEvent( AccountSettingsEvent.FocusProviderPostcode(it)) },
                        onFocusStreet = { viewModel.onTriggerEvent( AccountSettingsEvent.FocusProviderStreet(it)) },
                        onFocusBuilding = { viewModel.onTriggerEvent( AccountSettingsEvent.FocusProviderBuilding(it)) },
                        onFocusTown = { viewModel.onTriggerEvent( AccountSettingsEvent.FocusProviderTown(it))},
                        onFocusApartments = { viewModel.onTriggerEvent( AccountSettingsEvent.FocusProviderApartments(it))},
                        countryCode = viewModel.selectedCountryCode.descr + "(${viewModel.selectedCountryCode.dialcode})",
                        onSelectCountryCode ={
                            coroutineScope.launch {
                                bottomSheetType.value = BottomSheetType.COUNTY_TYPE
                                modalBottomSheetState.show()
                            }
                        },
                        onClickParkingType = {
                            coroutineScope.launch {
                                bottomSheetType.value = BottomSheetType.PARKING_TYPE
                                modalBottomSheetState.show()
                            }
                        },
                        onChangeBusinessOnly = {
                            viewModel.onTriggerEvent(AccountSettingsEvent.ToggleBusinessOnly)
                        }
                    )
                }
            }
        }
    }
}