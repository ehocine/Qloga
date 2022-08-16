package eac.qloga.android.features.screen2

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
fun Screen2(
    authViewModel: AuthenticationViewModel,
    apiViewModel: ApiViewModel,
    actions: NavigationActions
) {
    val context = LocalContext.current
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)
    val responseEnrollsModel by apiViewModel.responseEnrollsModel

    LaunchedEffect(key1 = true) {
        apiViewModel.getEnrollsLoadingState.emit(LoadingState.IDLE)
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(

            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Screen #2", fontSize = MaterialTheme.typography.h3.fontSize)
            when (oktaState) {
                is BrowserState.Loading -> {
                    CircularProgressIndicator()
                }
                is BrowserState.LoggedOut -> {
                    actions.goToSignIn.invoke()
                }
                else -> {

                    Text(
                        text = if (responseEnrollsModel.PROVIDER != null) "Provider: ${responseEnrollsModel.PROVIDER.toString()}"
                        else "Customer: ${responseEnrollsModel.CUSTOMER.toString()}"
                    )
                    Button(onClick = {
                        authViewModel.oktaLogout(context)
                    }) {
                        Text(text = "Log out")
                    }
                }
            }
        }
    }

}