package eac.qloga.android.features.p4p.showroom.scenes.notEnrolled

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import eac.qloga.android.core.services.BrowserState
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.core.shared.viewmodels.AuthenticationViewModel
import eac.qloga.android.data.shared.models.address_suggestions.Suggestion
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.LeftNavBar
import eac.qloga.android.features.p4p.showroom.shared.components.MainContent
import eac.qloga.android.features.p4p.showroom.shared.components.SearchBar
import eac.qloga.android.features.p4p.showroom.shared.viewModels.AddressViewModel
import eac.qloga.android.features.platform.landing.scenes.LandingScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotEnrolledScreen(
    navController: NavController,
    viewModel: NotEnrolledViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel(),
    authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val searchBarValue = viewModel.inputFieldState.value.text
    val searchBarFocusRequester = remember { FocusRequester() }
    val isAlreadyEnrolled = remember { mutableStateOf(true) }
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    val oktaState by authenticationViewModel.oktaState.collectAsState(BrowserState.LoggedIn)

    val categoriesList = ApiViewModel.categories.value.sortedBy {
        it.sortOrder
    }.sortedBy {
        it.catGroupOrder
    }

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
                                AddressViewModel.selectedAddressSuggestion.value =
                                    Suggestion(searchBarValue, "", "")
                                navController.navigate(P4pShowroomScreens.AddAddress.route)
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
                            navList = categoriesList,
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
                        when (oktaState) {
                            is BrowserState.Loading -> {
                                CircularProgressIndicator(color = green1)
                            }
                            is BrowserState.LoggedOut -> {
                                LaunchedEffect(key1 = true) {

                                    navController.navigate(LandingScreens.SignIn.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }
                            }
                            else -> {
                                Button(onClick = {
                                    authenticationViewModel.oktaLogout(context)
                                }) {
                                    androidx.compose.material3.Text(text = "Log out")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}