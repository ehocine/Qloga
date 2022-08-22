package eac.qloga.android.features.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.core.services.BrowserState
import eac.qloga.android.core.util.LoadingState
import eac.qloga.android.core.viewmodels.ApiViewModel
import eac.qloga.android.core.viewmodels.AuthenticationViewModel
import eac.qloga.android.features.shared.util.NavigationActions
import eac.qloga.android.ui.theme.green1


@Composable
fun SignIn(
    authViewModel: AuthenticationViewModel,
    apiViewModel: ApiViewModel,
    actions: NavigationActions
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)

    val getEnrollsState by apiViewModel.getEnrollsLoadingState.collectAsState()
    val responseEnrollsModel by apiViewModel.responseEnrollsModel

    LaunchedEffect(key1 = oktaState is BrowserState.LoggedIn) {
        apiViewModel.getEnrolls()
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to QLOGA!",
                color = green1,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 25.sp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                painter = painterResource(id = R.drawable.family_together),
                contentDescription = ""
            )
            when (oktaState) {
                is BrowserState.Loading -> {
                    CircularProgressIndicator(color = green1)
                }
                is BrowserState.LoggedIn -> {
                    when (getEnrollsState) {
                        LoadingState.LOADING -> {
                            CircularProgressIndicator(color = green1)
                        }
                        LoadingState.LOADED -> {
                            if (responseEnrollsModel.CUSTOMER != null || responseEnrollsModel.PROVIDER != null) {
                                actions.goToOrderLisrPrv.invoke()

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
                        },
                        appleSignIn = {
                            authViewModel.appleLogin(context)
                        },
                        googleSignIn = {
                            authViewModel.googleLogin(context)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Buttons(
    oktaOnclick: () -> Unit,
    appleSignIn: () -> Unit,
    googleSignIn: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.padding(10.dp))
        Image(
            painter = painterResource(id = R.drawable.qloga_button),
            contentDescription = "Qloga button",
            modifier = Modifier.clickable {
                oktaOnclick()
            }
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Image(
            painter = painterResource(id = R.drawable.appleid_button),
            contentDescription = "Apple button",
            modifier = Modifier.clickable {
                appleSignIn()
            }
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Button(onClick = { googleSignIn() }) {
            Text(text = "Sign in with Google")
        }
    }

}