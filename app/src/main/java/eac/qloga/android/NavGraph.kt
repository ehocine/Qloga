package eac.qloga.android

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import eac.qloga.android.core.scenes.CoreScreens
import eac.qloga.android.features.p4p.showroom.scenes.notEnrolled.NotEnrolledScreen
import eac.qloga.android.features.p4p.showroom.scenes.enrolled.EnrolledScreen
import eac.qloga.android.features.platform.landing.scenes.signIn.SignIn
import eac.qloga.android.core.scenes.splash.SplashScreen
import eac.qloga.android.core.shared.utils.ID_KEY
import eac.qloga.android.core.shared.utils.PARENT_ROUTE_KEY
import eac.qloga.android.features.intro.presentation.CategoriesScreen
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.scenes.addAddress.AddAddressScreen
import eac.qloga.android.features.p4p.showroom.scenes.serviceContract.ServiceContractScreen
import eac.qloga.android.features.p4p.showroom.scenes.serviceInfo.ServiceInfoScreen
import eac.qloga.android.features.platform.landing.scenes.LandingScreens
import eac.qloga.android.features.platform.landing.scenes.postSignup.PostSignupScreen
import eac.qloga.android.features.platform.landing.scenes.signUp.SignupScreen

private fun enterTransition(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { 300 },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(animationSpec = tween(600))
}

private fun exitTransition(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -300 },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(600))
}

private fun popEnterTransition(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { -300 },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(animationSpec = tween(600))
}


private fun popExitTransition(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { 300 },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(600))
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splash(
    actions: NavigationActions
) {
    composable(
        CoreScreens.SplashScreen.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() }
    ) {
        SplashScreen(
            actions = actions
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.notEnrolled(
    navController: NavController,
    actions: NavigationActions
) {
    composable(
        P4pShowroomScreens.NotEnrolled.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() }
    ) {
        NotEnrolledScreen(
            navController = navController,
            actions = actions
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.signIn(
    navController: NavController,
) {
    composable(
        LandingScreens.SignIn.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() }
    ) {
        SignIn(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.enrolled(
    navController: NavController,
    actions: NavigationActions
) {
    composable(
        route = P4pShowroomScreens.Enrolled.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        EnrolledScreen(
            navController = navController,
            actions = actions
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addressAdd(
    navController: NavController,
){
    composable(
        P4pShowroomScreens.AddAddress.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        AddAddressScreen(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addressOnMap(
    navController: NavController
){
    composable(
        P4pShowroomScreens.AddressOnMap.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        AddAddressScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.categories(
    navController: NavController,
){
    composable(
        P4pShowroomScreens.Categories.route+"?$PARENT_ROUTE_KEY={$PARENT_ROUTE_KEY}",
        arguments = listOf(
            navArgument(
                name = PARENT_ROUTE_KEY
            ){
                type = NavType.StringType
                defaultValue = ""
            }
        ),
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){ backStackEntry ->
        val parentRoute = backStackEntry.arguments?.getString(PARENT_ROUTE_KEY)
        CategoriesScreen(
            navController = navController,
            parentRoute = parentRoute
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.serviceInfo(
    navController: NavController,
){
    composable(
        P4pShowroomScreens.ServiceInfo.route+"?$PARENT_ROUTE_KEY={$PARENT_ROUTE_KEY}" +
                "&$ID_KEY={$ID_KEY}",
        arguments = listOf(
            navArgument(
                name = PARENT_ROUTE_KEY
            ){
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(
                name = ID_KEY
            ){
                type = NavType.IntType
                defaultValue = 0
            }
        ),
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){ stackEntry ->
        val parentRoute = stackEntry.arguments?.getString(PARENT_ROUTE_KEY)
        val id = stackEntry.arguments?.getInt(ID_KEY)

        ServiceInfoScreen(
            navController = navController,
            parentRoute = parentRoute,
            id = id
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.serviceContract(
    navController: NavController
){
    composable(
        route = P4pShowroomScreens.ServiceContract.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        ServiceContractScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.signup(
    navController: NavController,
){
    composable(
        route = LandingScreens.Signup.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        SignupScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.postSignup(
    navController: NavController
){
    composable(
        route = LandingScreens.PostSignup.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        PostSignupScreen(
            navController = navController
        )
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
        navController.navigate(LandingScreens.SignIn.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    val goToIntro: () -> Unit = {
        navController.navigate(P4pShowroomScreens.NotEnrolled.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }
    val goToOrderLisrPrv: () -> Unit = {
        navController.navigate(P4pShowroomScreens.Enrolled.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }
}
