package eac.qloga.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import eac.qloga.android.core.viewmodels.ApiViewModel
import eac.qloga.android.core.viewmodels.AuthenticationViewModel
import eac.qloga.android.features.intro.presentation.IntroViewModel
import eac.qloga.android.features.negotiation.presentation.OrderListPrvViewModel
import eac.qloga.android.features.shared.util.*
import eac.qloga.android.ui.theme.QLOGATheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BuildScreen()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BuildScreen() {
    val introViewModel = hiltViewModel<IntroViewModel>()
    val authenticationViewModel = hiltViewModel<AuthenticationViewModel>()
    val apiViewModel = hiltViewModel<ApiViewModel>()
    val orderListPrvViewModel = hiltViewModel<OrderListPrvViewModel>()

    val isDarkTheme = remember { mutableStateOf(false) }
    val isDynamicColor = remember { mutableStateOf(false) }

    QLOGATheme(
        darkTheme = isDarkTheme.value,
        dynamicColor = isDynamicColor.value
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberAnimatedNavController()
            val actions = remember(navController) { NavigationActions(navController) }

            AnimatedNavHost(
                navController = navController,
                startDestination = Screen.SignIn.route,
                builder = {
                    intro(
                        navController,
                        introViewModel,
                        authenticationViewModel,
                        apiViewModel,
                        actions
                    )
                    signIn(
                        authViewModel = authenticationViewModel,
                        apiViewModel = apiViewModel,
                        actions = actions
                    )
                    orderListPrv(
                        navController = navController,
                        authViewModel = authenticationViewModel,
                        apiViewModel = apiViewModel,
                        viewModel = orderListPrvViewModel,
                        actions = actions
                    )
                }
            )
        }
    }
}