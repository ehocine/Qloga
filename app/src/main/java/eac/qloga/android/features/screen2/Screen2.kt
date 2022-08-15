package eac.qloga.android.features.screen2

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
import eac.qloga.android.features.BrowserState
import eac.qloga.android.features.shared.util.NavigationActions
import eac.qloga.android.features.viewmodels.ApiViewModel
import eac.qloga.android.features.viewmodels.AuthenticationViewModel

@Composable
fun Screen2(authViewModel: AuthenticationViewModel, actions: NavigationActions) {
    val context = LocalContext.current

    val apiViewModel = hiltViewModel<ApiViewModel>()
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)
    val responseEnrollsModel by ApiViewModel.responseEnrollsModel

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