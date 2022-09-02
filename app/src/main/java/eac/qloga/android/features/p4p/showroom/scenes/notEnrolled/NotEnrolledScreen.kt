package eac.qloga.android.features.p4p.showroom.scenes.notEnrolled

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.services.BrowserState
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.LeftNavBar
import eac.qloga.android.features.p4p.showroom.shared.components.MainContent
import eac.qloga.android.features.p4p.showroom.shared.components.SearchBar
import eac.qloga.android.NavigationActions
import eac.qloga.android.core.shared.viewmodels.AuthenticationViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotEnrolledScreen(
    navController: NavController,
    actions: NavigationActions,
    viewModel: NotEnrolledViewModel = hiltViewModel(),
) {
    val searchBarValue = viewModel.inputFieldState.value.text
    val searchBarFocusRequester = remember{ FocusRequester() }
    val isAlreadyEnrolled = remember{ mutableStateOf(true) }
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
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
                SearchBar(
                    value = searchBarValue,
                    isFocused = viewModel.inputFieldState.value.isFocused,
                    hint = viewModel.inputFieldState.value.hint,
                    focusRequester = searchBarFocusRequester,
                    onValueChange = { viewModel.onTriggerEvent(NotEnrolledEvent.EnterText(it)) },
                    onSubmit = {
                        if(viewModel.inputFieldState.value.text.isNotEmpty()){
                            scope.launch {
                                navController.navigate(P4pShowroomScreens.AddressOnMap.route)
                            }
                        }
                        viewModel.onTriggerEvent(NotEnrolledEvent.Search)
                    },
                    onClear = { viewModel.onTriggerEvent(NotEnrolledEvent.ClearInput) },
                    onFocusedChanged = { viewModel.onTriggerEvent(NotEnrolledEvent.FocusInput(it)) }
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
                                if(searchBarValue.isEmpty()){
                                    scope.launch {
                                        searchBarFocusRequester.requestFocus()
                                    }
                                }else{
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
                    ){
                        MainContent(
                            onClickBecomeProvider = {
                                scope.launch {
                                    if(isAlreadyEnrolled.value){
                                        // navController.navigate(Screen.OrderListPrv.route)
                                    }else{
                                        // navController.navigate(Screen.ProviderEnrollment.route)
                                    }
                                }
                            },
                            onClickRequest = {
                                scope.launch {
                                    if(isAlreadyEnrolled.value){
                                       // navController.navigate(Screen.OrderListPrv.route)
                                    }else{
                                       // navController.navigate(Screen.CustomerEnrollment.route)
                                    }
                                }
                            },
                            onClickProviderSearch = {
                                scope.launch {
                                   navController.navigate(P4pShowroomScreens.Categories.route)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}