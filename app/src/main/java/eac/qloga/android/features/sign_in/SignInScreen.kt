package eac.qloga.android.features.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import eac.qloga.android.core.services.BrowserState
import eac.qloga.android.core.util.LoadingState
import eac.qloga.android.features.shared.util.NavigationActions
import eac.qloga.android.core.viewmodels.ApiViewModel
import eac.qloga.android.core.viewmodels.AuthenticationViewModel

@Composable
fun SignIn(
    authViewModel: AuthenticationViewModel,
    apiViewModel: ApiViewModel,
    actions: NavigationActions
) {

    val context = LocalContext.current
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)

    val getEnrollsState by apiViewModel.getEnrollsLoadingState.collectAsState()
    val responseEnrollsModel by apiViewModel.responseEnrollsModel

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
                    LaunchedEffect(key1 = true) {
                        apiViewModel.getEnrolls()
                    }
                    when (getEnrollsState) {
                        LoadingState.LOADING -> {
                            CircularProgressIndicator()
                        }
                        LoadingState.LOADED -> {
                            if (responseEnrollsModel.CUSTOMER != null || responseEnrollsModel.PROVIDER != null) {
                                actions.goToScreen2.invoke()

                            } else {
                                actions.goToIntro.invoke()
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