package eac.qloga.android.features.shared.util

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import eac.qloga.android.features.intro.presentation.IntroScreen
import eac.qloga.android.features.intro.presentation.IntroViewModel

fun enterTransition(): EnterTransition{
    return slideInHorizontally(
        initialOffsetX = { 300 },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(animationSpec = tween(600))
}

fun exitTransition(): ExitTransition{
    return slideOutHorizontally(
        targetOffsetX = { -300 },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(600))
}

fun popEnterTransition(): EnterTransition{
    return slideInHorizontally(
        initialOffsetX = { -300 },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(animationSpec = tween(600))
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.intro(
    navController: NavController,
    viewModel: IntroViewModel,
){
    composable(
        Screen.Intro.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() }
    ){
        IntroScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}
