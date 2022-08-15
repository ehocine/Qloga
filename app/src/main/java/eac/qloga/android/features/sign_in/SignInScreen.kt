package eac.qloga.android.features.sign_in

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.features.BrowserState
import eac.qloga.android.features.LoadingState
import eac.qloga.android.features.shared.util.NavigationActions
import eac.qloga.android.features.viewmodels.ApiViewModel
import eac.qloga.android.features.viewmodels.AuthenticationViewModel

@Composable
fun SignIn(
    authViewModel: AuthenticationViewModel,
    navController: NavController,
    actions: NavigationActions
) {

    val context = LocalContext.current
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)

    val apiViewModel = hiltViewModel<ApiViewModel>()

    val getEnrollsState by apiViewModel.getEnrollsLoadingState.collectAsState()
    val responseEnrollsModel by ApiViewModel.responseEnrollsModel

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Sign In", fontSize = MaterialTheme.typography.h3.fontSize)
            when (oktaState) {
                is BrowserState.Loading -> {
                    CircularProgressIndicator()
                }
                is BrowserState.LoggedIn -> {
                    apiViewModel.getEnrolls()
                    when (getEnrollsState) {
                        LoadingState.LOADING -> {
                            CircularProgressIndicator()
                        }
                        LoadingState.LOADED -> {
                            if (responseEnrollsModel.CUSTOMER != null || responseEnrollsModel.PROVIDER != null) {
                                Log.d("Tag", " res : ${ responseEnrollsModel.toString()}")
                                // Go to Screen 2
                                actions.goToScreen2.invoke()
//                                navController.navigate(Screen.Screen2.route) {
//                                    popUpTo(navController.graph.findStartDestination().id)
//                                    launchSingleTop = true
//                                }

                            } else {
                                actions.goToIntro.invoke()
//                                navController.navigate(Screen.Intro.route) {
//                                    popUpTo(navController.graph.findStartDestination().id)
//                                    launchSingleTop = true
//                                }
                            }

                        }
                        else -> Unit
                    }
                }
                else -> {
                    Buttons(
                        oktaOnclick = {
                            authViewModel.oktaLogin(context)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Buttons(
    oktaOnclick: () -> Unit
) {
    Button(onClick = { oktaOnclick() }) {
        Text(text = "Login with OKTA")
    }
}