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
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import eac.qloga.android.core.scenes.CoreScreens
import eac.qloga.android.core.shared.theme.QLOGATheme

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
                startDestination = CoreScreens.SplashScreen.route,
                builder = {
                    splash(actions)
                    notEnrolled(navController, actions)
                    signIn(actions)
                    enrolled(navController, actions)
                    addressAdd(navController)
                    addressOnMap(navController)
                    categories(navController)
                    serviceInfo(navController)
                    serviceContract(navController)
                }
            )
        }
    }
}