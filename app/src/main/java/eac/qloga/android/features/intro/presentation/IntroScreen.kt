package eac.qloga.android.features.intro.presentation

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.features.intro.presentation.components.LeftNavBar
import eac.qloga.android.features.intro.presentation.components.MainContent
import eac.qloga.android.features.intro.presentation.components.SearchBar
import eac.qloga.android.features.shared.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroScreen(
    navController: NavController,
    viewModel: IntroViewModel
) {
    val searchBarValue = viewModel.inputFieldState.value.text
    val searchBarFocusRequester = remember{ FocusRequester() }
    val isAlreadyEnrolled = remember{ mutableStateOf(true) }
    val activity = LocalContext.current as Activity

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier
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
                        if(viewModel.inputFieldState.value.text.isNotEmpty()){
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
                                if(searchBarValue.isEmpty()){
                                    scope.launch {
                                        searchBarFocusRequester.requestFocus()
                                    }
                                }else{
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
                    ){
                        MainContent(
                            onClickBecomeProvider = {
                                scope.launch {
                                    if(isAlreadyEnrolled.value){
                                     //   navController.navigate(Screen.OrderListPrv.route){
                                     //       launchSingleTop = true
                                     //   }
                                    }else{
                                     //   navController.navigate(Screen.ProviderEnrollment.route){
                                     //       launchSingleTop = true
                                     //   }
                                    }
                                }
                            },
                            onClickRequest = {
                                scope.launch {
                                    if(isAlreadyEnrolled.value){
                                    //    navController.navigate(Screen.OrderListPrv.route){
                                    //        launchSingleTop = true
                                    //    }
                                    }else{
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
                                //    navController.navigate(Screen.OrderListPrv.route){
                                //        launchSingleTop = true
                                //    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}