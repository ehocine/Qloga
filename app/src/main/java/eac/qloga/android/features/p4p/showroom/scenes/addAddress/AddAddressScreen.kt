package eac.qloga.android.features.p4p.showroom.scenes.addAddress

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.SaveButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.components.address.AddressCard
import eac.qloga.android.core.shared.components.address.AddressSearchBar
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.ParkingSelection
import eac.qloga.android.features.p4p.showroom.shared.viewModels.AddressViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AddAddressScreen(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val containerHorizontalPadding = 24.dp

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

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
                    actions =  {
                        SaveButton(onClick = {
                            viewModel.onSaveNewAddress()
                            coroutineScope.launch {
                                navController.navigateUp()
                            }
                        })
                    }
                ) {
                    navController.navigateUp()
                }
            }
        ) { paddingValues ->

            val titleBarHeight = paddingValues.calculateTopPadding()
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = containerHorizontalPadding)
                    ,
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
                        ){
                            AddressSearchBar(
                                value = viewModel.addressInputFieldState.value.text,
                                hint = viewModel.addressInputFieldState.value.hint,
                                isFocused = viewModel.addressInputFieldState.value.isFocused,
                                onValueChange = { viewModel.onTriggerEvent(AddressEvent.EnterText(it))},
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
                                onFocusedChanged = { viewModel.onTriggerEvent(AddressEvent.FocusInput(it))}
                            )
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
                        parkingType = viewModel.parkingType.value,
                        postcodeState = viewModel.postCodeState.value,
                        streetState = viewModel.streetState.value,
                        apartmentsState = viewModel.apartmentsState.value,
                        townState = viewModel.townState.value,
                        buildingState = viewModel.buildingState.value,
                        onChangePostcode = { viewModel.onTriggerEvent( AddressEvent.EnterPostcode(it)) },
                        onChangeStreet = { viewModel.onTriggerEvent( AddressEvent.EnterStreet(it)) },
                        onChangeBuilding = { viewModel.onTriggerEvent( AddressEvent.EnterBuilding(it)) },
                        onChangeTown = { viewModel.onTriggerEvent( AddressEvent.EnterTown(it))},
                        onChangeApartments = { viewModel.onTriggerEvent(AddressEvent.EnterApartments(it))},
                        onFocusPostcode = { viewModel.onTriggerEvent( AddressEvent.FocusPostcode(it)) },
                        onFocusStreet = { viewModel.onTriggerEvent( AddressEvent.FocusStreet(it)) },
                        onFocusBuilding = { viewModel.onTriggerEvent( AddressEvent.FocusBuilding(it)) },
                        onFocusTown = { viewModel.onTriggerEvent( AddressEvent.FocusTown(it))},
                        onFocusApartments = { viewModel.onTriggerEvent( AddressEvent.FocusApartments(it))},
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