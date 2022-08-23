package eac.qloga.android.features.intro.presentation

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.core.services.BrowserState
import eac.qloga.android.core.util.LoadingState
import eac.qloga.android.core.viewmodels.ApiViewModel
import eac.qloga.android.features.intro.presentation.components.LeftNavBar
import eac.qloga.android.features.intro.presentation.components.MainContent
import eac.qloga.android.features.intro.presentation.components.SearchBar
import eac.qloga.android.features.shared.util.NavigationActions
import eac.qloga.android.core.viewmodels.AuthenticationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroScreen(
    navController: NavController,
    viewModel: IntroViewModel,
    authViewModel: AuthenticationViewModel,
    apiViewModel: ApiViewModel,
    actions: NavigationActions
) {
    val context = LocalContext.current
    val searchBarValue = viewModel.inputFieldState.value.text
    val searchBarFocusRequester = remember { FocusRequester() }
    val isAlreadyEnrolled = remember { mutableStateOf(true) }
    val activity = LocalContext.current as Activity

    val scope = rememberCoroutineScope()

    val signedInUser by authViewModel.signedInUser
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)

    LaunchedEffect(key1 = true) {
        apiViewModel.getEnrollsLoadingState.emit(LoadingState.IDLE)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()
        when (oktaState) {
            is BrowserState.LoggedIn -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                ) {
                    Column {

                        SearchBar(
                            value = searchBarValue,
                            isFocused = viewModel.inputFieldState.value.isFocused,
                            hint = viewModel.inputFieldState.value.hint,
                            focusRequester = searchBarFocusRequester,
                            onValueChange = { viewModel.onTriggerEvent(IntroEvent.EnterText(it)) },
                            onSubmit = {
                                if (viewModel.inputFieldState.value.text.isNotEmpty()) {
                                    scope.launch {
                                        //navController.navigate(Screen.AddressAdd.route)
                                    }
                                }
                                viewModel.onTriggerEvent(IntroEvent.Search)
                            },
                            onClear = { viewModel.onTriggerEvent(IntroEvent.ClearInput) },
                            onFocusedChanged = { viewModel.onTriggerEvent(IntroEvent.FocusInput(it)) }
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box {
                                LeftNavBar(
                                    selectedNav = viewModel.selectedNav.value,
                                    enableClick = searchBarValue.isNotEmpty(),
                                    onClickItem = {
                                        if (searchBarValue.isEmpty()) {
                                            scope.launch {
                                                searchBarFocusRequester.requestFocus()
                                            }
                                        } else {
                                            scope.launch {
                                                //navController.navigate(Screen.ProviderSearch.route)
                                                viewModel.onTriggerEvent(IntroEvent.NavItemClick(it))
                                            }
                                        }
                                    }
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 24.dp, start = 40.dp),
                            ) {
                                MainContent(
                                    onClickBecomeProvider = {
                                        scope.launch {
                                            if (isAlreadyEnrolled.value) {
                                                //   navController.navigate(Screen.EnrolledPrv.route){
                                                //       launchSingleTop = true
                                                //   }
                                            } else {
                                                //   navController.navigate(Screen.ProviderEnrollment.route){
                                                //       launchSingleTop = true
                                                //   }
                                            }
                                        }
                                    },
                                    onClickRequest = {
                                        scope.launch {
                                            if (isAlreadyEnrolled.value) {
                                                //    navController.navigate(Screen.Enrolled.route){
                                                //        launchSingleTop = true
                                                //    }
                                            } else {
                                                //    navController.navigate(Screen.CustomerEnrollment.route){
                                                //        launchSingleTop = true
                                                //    }
                                            }
                                        }
                                    },
                                    onClickProviderSearch = {
                                        scope.launch {
                                            //    navController.navigate(Screen.ProviderSearch.route){
                                            //        launchSingleTop = true
                                            //    }
                                        }
                                    },
                                    onClickEnrolled = {
                                        scope.launch {
                                            //    navController.navigate(Screen.Enrolled.route){
                                            //        launchSingleTop = true
                                            //    }
                                        }
                                    },
                                    user = signedInUser,
                                    onLogOutClicked = {
                                        authViewModel.oktaLogout(context)
                                    }
                                )

                            }
                        }
                    }
                }
            }
            is BrowserState.LoggedOut -> {actions.goToSignIn.invoke()}
            else -> {}
        }
    }
}