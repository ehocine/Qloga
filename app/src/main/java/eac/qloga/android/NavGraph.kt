package eac.qloga.android

import android.os.Build
import androidx.annotation.RequiresApi
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
import eac.qloga.android.core.scenes.splash.SplashScreen
import eac.qloga.android.core.shared.utils.ID_KEY
import eac.qloga.android.core.shared.utils.PARENT_ROUTE_KEY
import eac.qloga.android.features.p4p.customer.scenes.dashboard.CustomerDashboardScreen
import eac.qloga.android.features.p4p.customer.scenes.favouriteProviders.FavouriteProvidersScreen
import eac.qloga.android.features.p4p.customer.scenes.openRequests.OpenRequestsScreen
import eac.qloga.android.features.p4p.customer.scenes.orders.CustomerOrdersScreen
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.scenes.customers.CustomersScreen
import eac.qloga.android.features.p4p.provider.scenes.dashboard.ProviderDashboardScreen
import eac.qloga.android.features.p4p.provider.scenes.favouriteCustomer.FavouriteCustomerScreen
import eac.qloga.android.features.p4p.provider.scenes.favouriteCustomers.FavouriteCustomersScreen
import eac.qloga.android.features.p4p.provider.scenes.orders.ProviderOrdersScreen
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.confirmAddress.ConfirmAddressScreen
import eac.qloga.android.features.p4p.shared.scenes.enrollment.EnrollmentScreen
import eac.qloga.android.features.p4p.shared.scenes.idVerification.IdVerificationScreen
import eac.qloga.android.features.p4p.shared.scenes.passport.PassportScreen
import eac.qloga.android.features.p4p.shared.scenes.selectLocationMap.SelectLocationScreen
import eac.qloga.android.features.p4p.shared.scenes.tc.EnrollmentTcScreen
import eac.qloga.android.features.p4p.shared.scenes.verifyPhone.VerifyPhoneScreen
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.scenes.addAddress.AddAddressScreen
import eac.qloga.android.features.p4p.showroom.scenes.addressOnMap.MapViewScreen
import eac.qloga.android.features.p4p.showroom.scenes.categories.CategoriesScreen
import eac.qloga.android.features.p4p.showroom.scenes.enrolled.EnrolledScreen
import eac.qloga.android.features.p4p.showroom.scenes.notEnrolled.NotEnrolledScreen
import eac.qloga.android.features.p4p.showroom.scenes.portfolioAlbums.PortfolioAlbumsScreen
import eac.qloga.android.features.p4p.showroom.scenes.preoviderDetails.ProviderDetailsScreen
import eac.qloga.android.features.p4p.shared.scenes.providerSearch.ProviderSearchScreen
import eac.qloga.android.features.p4p.showroom.scenes.serviceContract.ServiceContractScreen
import eac.qloga.android.features.p4p.showroom.scenes.serviceInfo.ServiceInfoScreen
import eac.qloga.android.features.platform.landing.scenes.LandingScreens
import eac.qloga.android.features.platform.landing.scenes.noAddress.NoAddressScreen
import eac.qloga.android.features.platform.landing.scenes.postSignup.PostSignupScreen
import eac.qloga.android.features.platform.landing.scenes.signIn.SignIn
import eac.qloga.android.features.platform.landing.scenes.signUp.SignupScreen
import eac.qloga.android.features.platform.landing.scenes.termsConds.SignupTermsCondsScreen

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
    navController: NavController,
    actions: NavigationActions
) {
    composable(
        CoreScreens.SplashScreen.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() }
    ) {
        SplashScreen(
            navController = navController,
            actions = actions
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.notEnrolled(
    navController: NavController
) {
    composable(
        P4pShowroomScreens.NotEnrolled.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() }
    ) {
        NotEnrolledScreen(
            navController = navController
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addressAdd(
    navController: NavController
) {
    composable(
        P4pShowroomScreens.AddAddress.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        AddAddressScreen(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addressOnMap(
    navController: NavController
) {
    composable(
        P4pShowroomScreens.AddressOnMap.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        MapViewScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.categories(
    navController: NavController,
) {
    composable(
        P4pShowroomScreens.Categories.route + "?$PARENT_ROUTE_KEY={$PARENT_ROUTE_KEY}",
        arguments = listOf(
            navArgument(
                name = PARENT_ROUTE_KEY
            ) {
                type = NavType.StringType
                defaultValue = ""
            }
        ),
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) { backStackEntry ->
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
) {
    composable(
        P4pShowroomScreens.ServiceInfo.route + "?$PARENT_ROUTE_KEY={$PARENT_ROUTE_KEY}" +
                "&$ID_KEY={$ID_KEY}",
        arguments = listOf(
            navArgument(
                name = PARENT_ROUTE_KEY
            ) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(
                name = ID_KEY
            ) {
                type = NavType.IntType
                defaultValue = 0
            }
        ),
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) { stackEntry ->
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
) {
    composable(
        route = P4pShowroomScreens.ServiceContract.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ServiceContractScreen(
            navController = navController
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.signup(
    navController: NavController,
) {
    composable(
        route = LandingScreens.Signup.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SignupScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.postSignup(
    navController: NavController
) {
    composable(
        route = LandingScreens.PostSignup.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        PostSignupScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.signupTermsConds(
    navController: NavController
){
    composable(
        route = LandingScreens.SignupTermsConds.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        SignupTermsCondsScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providers(
    navController: NavController,
){
    composable(
        P4pScreens.ProviderSearch.route+"?$PARENT_ROUTE_KEY={$PARENT_ROUTE_KEY}",
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
        ProviderSearchScreen(
            navController = navController,
            parentRoute =  parentRoute
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providersDetails(
    navController: NavController
){
    composable(
        P4pShowroomScreens.ProviderDetails.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        ProviderDetailsScreen(
            navController = navController
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.albums(
    navController: NavController
){
    composable(
        route = P4pShowroomScreens.PortfolioAlbums.route+"?$PARENT_ROUTE_KEY={$PARENT_ROUTE_KEY}",
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
    ){ navBackStackEntry ->
        val parentRoute = navBackStackEntry.arguments?.getString(PARENT_ROUTE_KEY)

        PortfolioAlbumsScreen(
            navController = navController,
            parentRouteParam = parentRoute
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providerDashboard(
    navController: NavController
){
    composable(
        route = P4pProviderScreens.ProviderDashboard.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        ProviderDashboardScreen(navController = navController )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providerOrders(
    navController: NavController
){
    composable(
        route = P4pProviderScreens.ProviderOrders.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        ProviderOrdersScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favouriteCustomers(
    navController: NavController
){
    composable(
        route = P4pProviderScreens.FavouriteCustomers.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        FavouriteCustomersScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.customers(
    navController: NavController,
){
    composable(
        route = P4pProviderScreens.Customers.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        CustomersScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favouriteCustomer(
    navController: NavController
){
    composable(
        route = P4pProviderScreens.FavouriteCustomer.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        FavouriteCustomerScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noAddress(
    navController: NavController
) {
    composable(
        route = LandingScreens.NoAddress.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        NoAddressScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.enrollment(
    navController: NavController
){
    composable(
        P4pScreens.Enrollment.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        EnrollmentScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.verifyPhone(
    navController: NavController,
){
    composable(
        P4pScreens.VerifyPhone.route,
        enterTransition = { enterTransition()},

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        VerifyPhoneScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.confirmAddress(
    navController: NavController
){
    composable(
        P4pScreens.ConfirmAddress.route,
        enterTransition = { enterTransition()},

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        ConfirmAddressScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.userLocationMapView(
    navController: NavController
){
    composable(
        P4pScreens.SelectLocationMap.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        SelectLocationScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.identityVerification(
    navController: NavController
){
    composable(
        P4pScreens.IdVerification.route,
        enterTransition = { enterTransition()},

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        IdVerificationScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.passport(
    navController: NavController,
){
    composable(
        P4pScreens.Passport.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        PassportScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.enrollmentTermsConditions(
    navController: NavController,
){
    composable(
        P4pScreens.EnrollmentTermsConditions.route,
        enterTransition = { enterTransition()},

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        EnrollmentTcScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.customerDashboard(
    navController: NavController
){
    composable(
        route = P4pCustomerScreens.CustomerDashboard.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        CustomerDashboardScreen(
            navController = navController,
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.customerOrders(
    navController: NavController,
){
    composable(
        route = P4pCustomerScreens.CustomerOrders.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        CustomerOrdersScreen(
            navController = navController,
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favouriteProviders(
    navController: NavController,
){
    composable(
        route = P4pCustomerScreens.FavouriteProviders.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        FavouriteProvidersScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.openRequests(
    navController: NavController,
){
    composable(
        route = P4pCustomerScreens.OpenRequests.route,
        enterTransition = { enterTransition()},
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ){
        OpenRequestsScreen(
            navController = navController,
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

    val goToNoAddress: () -> Unit = {
        navController.navigate(LandingScreens.NoAddress.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
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
