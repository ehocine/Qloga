package eac.qloga.android.features.shared.util

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import eac.qloga.android.features.intro.presentation.IntroScreen
import eac.qloga.android.features.intro.presentation.IntroViewModel
import eac.qloga.android.features.screen2.Screen2
import eac.qloga.android.features.sign_in.SignIn
import eac.qloga.android.features.viewmodels.AuthenticationViewModel

fun enterTransition(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { 300 },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(animationSpec = tween(600))
}

fun exitTransition(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -300 },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(600))
}

fun popEnterTransition(): EnterTransition {
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
    authenticationViewModel: AuthenticationViewModel,
    actions: NavigationActions
) {
    composable(
        Screen.Intro.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() }
    ) {
        IntroScreen(
            navController = navController,
            viewModel = viewModel,
            authViewModel = authenticationViewModel,
            actions = actions
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.signIn(
    navController: NavController,
    viewModel: AuthenticationViewModel,
    actions: NavigationActions
) {
    composable(
        Screen.SignIn.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() }
    ) {
        SignIn(
            navController = navController,
            authViewModel = viewModel,
            actions = actions
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.screen2(
    viewModel: AuthenticationViewModel,
    actions: NavigationActions
) {
    composable(
        Screen.Screen2.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() }
    ) {
        Screen2(viewModel, actions)
    }
}

class NavigationActions(navController: NavController) {
    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    val popBackStack: () -> Unit = {
        navController.popBackStack()
    }

    val goToSignIn: () -> Unit = {
        navController.navigate(Screen.SignIn.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    val goToIntro: () -> Unit = {
        navController.navigate(Screen.Intro.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }
    val goToScreen2: () -> Unit = {
        navController.navigate(Screen.Screen2.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }
}
