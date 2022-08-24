package eac.qloga.android.features.splash_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import eac.qloga.android.R
import eac.qloga.android.core.services.BrowserState
import eac.qloga.android.core.util.LoadingState
import eac.qloga.android.core.viewmodels.ApiViewModel
import eac.qloga.android.core.viewmodels.AuthenticationViewModel
import eac.qloga.android.features.shared.util.NavigationActions
import eac.qloga.android.ui.theme.green1
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    authViewModel: AuthenticationViewModel,
    apiViewModel: ApiViewModel,
    actions: NavigationActions
) {
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)
    val getEnrollsState by apiViewModel.getEnrollsLoadingState.collectAsState()
    val responseEnrollsModel by apiViewModel.responseEnrollsModel

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000)
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000L)
        when (oktaState) {
            is BrowserState.Loading -> Unit
            is BrowserState.LoggedIn -> {
                apiViewModel.getEnrolls()
            }
            else -> {
                actions.goToSignIn.invoke()
            }
        }
    }
    SplashContent(alpha = alphaAnim.value)
    when (getEnrollsState) {
        LoadingState.LOADING -> Unit
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

@Composable
fun SplashContent(alpha: Float) {

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.qloga_logo),
                modifier = Modifier.alpha(alpha),
                contentDescription = ""
            )
            CircularProgressIndicator(color = green1, modifier = Modifier.alpha(alpha))
        }
    }
}