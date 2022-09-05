package eac.qloga.android.features.p4p.showroom.scenes.notEnrolled

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.LeftNavBar
import eac.qloga.android.features.p4p.showroom.shared.components.MainContent
import eac.qloga.android.features.p4p.showroom.shared.components.SearchBar
import eac.qloga.android.features.p4p.showroom.shared.viewModels.AddressViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotEnrolledScreen(
    navController: NavController,
    viewModel: NotEnrolledViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel(),
) {
    val searchBarValue = viewModel.inputFieldState.value.text
    val searchBarFocusRequester = remember { FocusRequester() }
    val isAlreadyEnrolled = remember { mutableStateOf(true) }
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    val addressSuggestionsLoadingState by addressViewModel.getAddressSuggestionsLoadingState.collectAsState()
    val addressSuggestions by addressViewModel.addressSuggestionsList

    var expanded by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(Unit) {
        viewModel.onNavClick(null)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        parentSize = it.size
                    }) {
                    SearchBar(
                        value = searchBarValue,
                        isFocused = viewModel.inputFieldState.value.isFocused,
                        hint = viewModel.inputFieldState.value.hint,
                        focusRequester = searchBarFocusRequester,
                        onValueChange = {
                            viewModel.onTriggerEvent(NotEnrolledEvent.EnterText(it))
                            addressViewModel.getAddressSuggestions(
                                term = it
                            )
                        },
                        onSubmit = {
                            if (viewModel.inputFieldState.value.text.isNotEmpty()) {
//                                viewModel.onTriggerEvent(NotEnrolledEvent.Search)
//                                AddressViewModel.selectedAddressSuggestion.value =
//                                    addressSuggestion
//                                navController.navigate(P4pShowroomScreens.AddAddress.route)
//                                scope.launch {
//                                    navController.navigate(P4pShowroomScreens.AddressOnMap.route)
//                                }
                            }

                        },
                        onClear = { viewModel.onTriggerEvent(NotEnrolledEvent.ClearInput) },
                        onFocusedChanged = { viewModel.onTriggerEvent(NotEnrolledEvent.FocusInput(it)) }
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
                                        viewModel.onTriggerEvent(
                                            NotEnrolledEvent.AddressChosen(
                                                addressSuggestion.address
                                            )
                                        )
                                        AddressViewModel.selectedAddressSuggestion.value =
                                            addressSuggestion
//                                        viewModel.getFullAddress(id = addressSuggestion.id)

                                        navController.navigate(P4pShowroomScreens.AddAddress.route)
                                    }
                                ) {
                                    Text(text = addressSuggestion.address)
                                }
                            }
                        }
                    }

                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        LeftNavBar(
                            selectedNav = viewModel.selectedNav.value,
                            navList = ApiViewModel.categories.value,
                            enableClick = searchBarValue.isNotEmpty(),
                            onClickItem = {
                                if (searchBarValue.isEmpty()) {
                                    scope.launch {
                                        searchBarFocusRequester.requestFocus()
                                    }
                                } else {
                                    scope.launch {
                                        navController.navigate(P4pShowroomScreens.Categories.route)
                                        viewModel.onTriggerEvent(NotEnrolledEvent.NavItemClick(it))
                                    }
                                }
                            }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp, start = 40.dp),
                    ) {
                        MainContent(
                            onClickBecomeProvider = {
                                scope.launch {
                                    if (isAlreadyEnrolled.value) {
                                        // navController.navigate(Screen.OrderListPrv.route)
                                    } else {
                                        // navController.navigate(Screen.ProviderEnrollment.route)
                                    }
                                }
                            },
                            onClickRequest = {
                                scope.launch {
                                    if (isAlreadyEnrolled.value) {
                                        // navController.navigate(Screen.OrderListPrv.route)
                                    } else {
                                        // navController.navigate(Screen.CustomerEnrollment.route)
                                    }
                                }
                            },
                            onClickProviderSearch = {
                                scope.launch {
                                    // navController.navigate(Screen.ServiceCategories.route)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}