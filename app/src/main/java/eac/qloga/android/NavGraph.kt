package eac.qloga.android

import P4pCustomerScreens
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
import eac.qloga.android.core.shared.utils.PROVIDER_ID
import eac.qloga.android.features.p4p.customer.scenes.accountSettings.CustomerAccountSettingsScreen
import eac.qloga.android.features.p4p.customer.scenes.customerOrder.CustomerOrderScreen
import eac.qloga.android.features.p4p.customer.scenes.customerProfile.CustomerProfileScreen
import eac.qloga.android.features.p4p.customer.scenes.dashboard.CustomerDashboardScreen
import eac.qloga.android.features.p4p.customer.scenes.favouriteProviders.FavouriteProvidersScreen
import eac.qloga.android.features.p4p.customer.scenes.openRequestList.OpenRequestListScreen
import eac.qloga.android.features.p4p.customer.scenes.openRequests.OpenRequestsScreen
import eac.qloga.android.features.p4p.customer.scenes.orders.CustomerOrdersScreen
import eac.qloga.android.features.p4p.customer.scenes.request.RequestScreen
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.scenes.accountSettings.ProviderAccountSettingsScreen
import eac.qloga.android.features.p4p.provider.scenes.addPrvAddress.AddPrvAddressScreen
import eac.qloga.android.features.p4p.provider.scenes.customers.CustomersScreen
import eac.qloga.android.features.p4p.provider.scenes.dashboard.ProviderDashboardScreen
import eac.qloga.android.features.p4p.provider.scenes.favouriteCustomers.FavouriteCustomersScreen
import eac.qloga.android.features.p4p.provider.scenes.orders.ProviderOrdersScreen
import eac.qloga.android.features.p4p.provider.scenes.providedService.ProvidedServiceScreen
import eac.qloga.android.features.p4p.provider.scenes.providedServiceConditions.ProvidedServiceConditionsScreen
import eac.qloga.android.features.p4p.provider.scenes.providerOrder.ProviderOrderScreen
import eac.qloga.android.features.p4p.provider.scenes.providerOrderFilter.ProviderOrderFilterScreen
import eac.qloga.android.features.p4p.provider.scenes.providerProfile.ProviderProfileScreen
import eac.qloga.android.features.p4p.provider.scenes.servicesConditions.ServicesConditionsScreen
import eac.qloga.android.features.p4p.provider.scenes.workingSchedule.WorkingScheduleEditScreen
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.account.AccountScreen
import eac.qloga.android.features.p4p.shared.scenes.businessDetails.BusinessDetailsScreen
import eac.qloga.android.features.p4p.shared.scenes.choosingNewAddress.ChoosingNewAddressScreen
import eac.qloga.android.features.p4p.shared.scenes.closedOrder.ClosedOrderScreen
import eac.qloga.android.features.p4p.shared.scenes.confirmAddress.ConfirmAddressScreen
import eac.qloga.android.features.p4p.shared.scenes.contactDetails.ContactDetailsScreen
import eac.qloga.android.features.p4p.shared.scenes.displayVisits.DisplayVisitsScreen
import eac.qloga.android.features.p4p.shared.scenes.editVisits.EditVisitsScreen
import eac.qloga.android.features.p4p.shared.scenes.enrollment.EnrollmentScreen
import eac.qloga.android.features.p4p.shared.scenes.faQuestions.FaQuestionsScreen
import eac.qloga.android.features.p4p.shared.scenes.faqDashboard.FAQDashboardScreen
import eac.qloga.android.features.p4p.shared.scenes.idVerification.IdVerificationScreen
import eac.qloga.android.features.p4p.shared.scenes.inquiredServices.InquiredServicesScreen
import eac.qloga.android.features.p4p.shared.scenes.inquiry.InquiryScreen
import eac.qloga.android.features.p4p.shared.scenes.mediaFullView.MediaFullViewScreen
import eac.qloga.android.features.p4p.shared.scenes.mediaView.MediaViewScreen
import eac.qloga.android.features.p4p.shared.scenes.notesEdit.NotesEditScreen
import eac.qloga.android.features.p4p.shared.scenes.orderAddrMapView.OrderAddrMapViewScreen
import eac.qloga.android.features.p4p.shared.scenes.orderMapGps.OrderMapGpsScreen
import eac.qloga.android.features.p4p.shared.scenes.orderNotes.OrderNotesScreen
import eac.qloga.android.features.p4p.shared.scenes.orderPayment.OrderPaymentScreen
import eac.qloga.android.features.p4p.shared.scenes.orderVisits.OrderVisitsScreen
import eac.qloga.android.features.p4p.shared.scenes.paidOrder.PaidOrderScreen
import eac.qloga.android.features.p4p.shared.scenes.passport.PassportScreen
import eac.qloga.android.features.p4p.shared.scenes.paymentTracking.PaymentTrackingScreen
import eac.qloga.android.features.p4p.shared.scenes.portfolio.PortfolioScreen
import eac.qloga.android.features.p4p.shared.scenes.portfolioFullView.PortfolioFullViewScreen
import eac.qloga.android.features.p4p.shared.scenes.providerSearch.ProviderSearchScreen
import eac.qloga.android.features.p4p.shared.scenes.prvCstTC.PrvCstTCScreen
import eac.qloga.android.features.p4p.shared.scenes.quote.QuoteScreen
import eac.qloga.android.features.p4p.shared.scenes.ratingDetails.RatingDetailsScreen
import eac.qloga.android.features.p4p.shared.scenes.reviews.ReviewsScreen
import eac.qloga.android.features.p4p.shared.scenes.saveNewAddress.SaveNewAddressScreen
import eac.qloga.android.features.p4p.shared.scenes.searchedAddrResult.SearchedAddrResultScreen
import eac.qloga.android.features.p4p.shared.scenes.selectAddress.SelectAddressScreen
import eac.qloga.android.features.p4p.shared.scenes.selectAlbum.SelectAlbumsScreen
import eac.qloga.android.features.p4p.shared.scenes.selectLocationMap.SelectLocationScreen
import eac.qloga.android.features.p4p.shared.scenes.serviceInfo.ServiceInfoScreen
import eac.qloga.android.features.p4p.shared.scenes.serviceinfoedit.ServiceInfoEditScreen
import eac.qloga.android.features.p4p.shared.scenes.settingsEmail.SettingsEmailScreen
import eac.qloga.android.features.p4p.shared.scenes.settingsLanguage.SettingsLanguageScreen
import eac.qloga.android.features.p4p.shared.scenes.settingsPhone.SettingsPhoneScreen
import eac.qloga.android.features.p4p.shared.scenes.showLocatioMapView.ShowLocationMapViewScreen
import eac.qloga.android.features.p4p.shared.scenes.tc.EnrollmentTcScreen
import eac.qloga.android.features.p4p.shared.scenes.tracking.TrackingScreen
import eac.qloga.android.features.p4p.shared.scenes.verifications.VerificationsScreen
import eac.qloga.android.features.p4p.shared.scenes.verifyPhone.VerifyPhoneScreen
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.scenes.addAddress.AddAddressScreen
import eac.qloga.android.features.p4p.showroom.scenes.addressOnMap.MapViewScreen
import eac.qloga.android.features.p4p.showroom.scenes.categories.CategoriesScreen
import eac.qloga.android.features.p4p.showroom.scenes.enrolled.EnrolledScreen
import eac.qloga.android.features.p4p.showroom.scenes.notEnrolled.NotEnrolledScreen
import eac.qloga.android.features.p4p.showroom.scenes.portfolioAlbums.PortfolioAlbumsScreen
import eac.qloga.android.features.p4p.showroom.scenes.providerServices.ProviderServicesScreen
import eac.qloga.android.features.p4p.showroom.scenes.providerWorkingSchedule.ProviderWorkingScheduleScreen
import eac.qloga.android.features.p4p.showroom.scenes.serviceContract.ServiceContractScreen
import eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.LandingScreens
import eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.dataPrivacy.DataPrivacyScreen
import eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.noAddress.NoAddressScreen
import eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.postSignup.PostSignupScreen
import eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.signIn.SignIn
import eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.signUp.SignupScreen
import eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.termsConds.SignupTermsCondsScreen

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
    navActions: NavigationActions
) {
    composable(
        P4pScreens.ServiceInfo.route + "?$ID_KEY={$ID_KEY}",
        arguments = listOf(
            navArgument(
                name = ID_KEY
            ) {
                type = NavType.LongType
                defaultValue = 0L
            }
        ),
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ServiceInfoScreen(
            navActions = navActions
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.serviceInfoEdit(
    navActions: NavigationActions
) {
    composable(
        P4pScreens.ServiceInfoEdit.route + "?$ID_KEY={$ID_KEY}",
        arguments = listOf(
            navArgument(
                name = ID_KEY
            ) {
                type = NavType.LongType
                defaultValue = 0L
            }
        ),
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ServiceInfoEditScreen(
            navActions = navActions
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
) {
    composable(
        route = LandingScreens.SignupTermsConds.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SignupTermsCondsScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providers(
    navController: NavController,
) {
    composable(
        P4pScreens.ProviderSearch.route + "?$PARENT_ROUTE_KEY={$PARENT_ROUTE_KEY}",
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
        ProviderSearchScreen(
            navController = navController,
            parentRoute = parentRoute
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providersDetails(
    navController: NavController,
    actions: NavigationActions
) {
    composable(
        P4pProviderScreens.ProviderProfile.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProviderProfileScreen(
            navController = navController,
            navigationActions = actions
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.albums(
    navController: NavController
) {
    composable(
        route = P4pShowroomScreens.PortfolioAlbums.route + "?$PARENT_ROUTE_KEY={$PARENT_ROUTE_KEY}",
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
    ) { navBackStackEntry ->
        val parentRoute = navBackStackEntry.arguments?.getString(PARENT_ROUTE_KEY)

        PortfolioAlbumsScreen(
            navController = navController,
            parentRouteParam = parentRoute
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providerDashboard(
    navController: NavController
) {
    composable(
        route = P4pProviderScreens.ProviderDashboard.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProviderDashboardScreen(navController = navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providerOrders(
    navController: NavController
) {
    composable(
        route = P4pProviderScreens.ProviderOrders.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProviderOrdersScreen(
            navController = navController
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favouriteCustomers(
    navController: NavController
) {
    composable(
        route = P4pProviderScreens.FavouriteCustomers.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        FavouriteCustomersScreen(
            navController = navController,
            actions = NavigationActions(navController)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.customers(
    navController: NavController,
) {
    composable(
        route = P4pProviderScreens.Customers.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        CustomersScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favouriteCustomer(
    navController: NavController
) {
    composable(
        route = P4pCustomerScreens.CustomerProfile.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        CustomerProfileScreen(
            navController = navController,
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.enrollment(
    navController: NavController
) {
    composable(
        P4pScreens.Enrollment.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        EnrollmentScreen(
            navController = navController,
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.verifyPhone(
    navController: NavController,
) {
    composable(
        P4pScreens.VerifyPhone.route,
        enterTransition = { enterTransition() },

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        VerifyPhoneScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.chooseNewAddress(
    navController: NavController
) {
    composable(
        P4pScreens.ChoosingNewAddress.route,
        enterTransition = { enterTransition() },

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ChoosingNewAddressScreen(
            navController = navController
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.saveNewAddress(
    navController: NavController
) {
    composable(
        P4pScreens.SaveNewAddress.route,
        enterTransition = { enterTransition() },

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SaveNewAddressScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.userLocationMapView(
    navController: NavController
) {
    composable(
        P4pScreens.SelectLocationMap.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SelectLocationScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.identityVerification(
    navController: NavController
) {
    composable(
        P4pScreens.IdVerification.route,
        enterTransition = { enterTransition() },

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        IdVerificationScreen(
            navController = navController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.passport(
    navController: NavController,
) {
    composable(
        P4pScreens.Passport.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        PassportScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.enrollmentTermsConditions(
    navController: NavController,
) {
    composable(
        P4pScreens.EnrollmentTermsConditions.route,
        enterTransition = { enterTransition() },

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        EnrollmentTcScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providerServices(
    navController: NavController
) {
    composable(
        route = P4pShowroomScreens.ProviderServices.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProviderServicesScreen(
            navController = navController
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providerWorkingSchedule(
    navController: NavController,
) {
    composable(
        route = P4pShowroomScreens.ProviderWorkingSchedule.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProviderWorkingScheduleScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.confirmAddress(
    navController: NavController
) {
    composable(
        P4pScreens.ConfirmAddress.route,
        enterTransition = { enterTransition() },

        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ConfirmAddressScreen(
            navController = navController
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.ratingDetails(
    navActions: NavigationActions,
) {
    composable(
        route = P4pScreens.RatingDetails.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        RatingDetailsScreen(navigationActions = navActions)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.reviews(
    navActions: NavigationActions,
) {
    composable(
        route = P4pScreens.Reviews.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ReviewsScreen(navigationActions = navActions)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.contactDetails(
    navActions: NavigationActions,
) {
    composable(
        route = P4pScreens.ContactDetails.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ContactDetailsScreen(navigationActions = navActions)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.verifications(
    navActions: NavigationActions,
) {
    composable(
        route = P4pScreens.Verifications.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        VerificationsScreen(navigationActions = navActions)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.customerDashboard(
    navController: NavController
) {
    composable(
        route = P4pCustomerScreens.CustomerDashboard.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        CustomerDashboardScreen(
            navController = navController,
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.customerOrders(
    navController: NavController,
) {
    composable(
        route = P4pCustomerScreens.CustomerOrders.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        CustomerOrdersScreen(
            navController = navController,
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favouriteProviders(
    navController: NavController,
) {
    composable(
        route = P4pCustomerScreens.FavouriteProviders.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        FavouriteProvidersScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.dataPrivacy(
    navActions: NavigationActions,
) {
    composable(
        route = LandingScreens.DataPrivacy.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        DataPrivacyScreen(navigationActions = navActions)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.openRequests(
    navController: NavController,
) {
    composable(
        route = P4pCustomerScreens.OpenRequests.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        OpenRequestsScreen(
            navController = navController,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.account(
    navController: NavController,
) {
    composable(
        route = P4pScreens.Account.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        AccountScreen(
            navController = navController,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providerAccountSettings(
    navController: NavController,
) {
    composable(
        route = P4pProviderScreens.ProviderAccountSettings.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProviderAccountSettingsScreen(
            navController = navController,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.customerAccountSettings(
    navController: NavController,
) {
    composable(
        route = P4pCustomerScreens.CustomerAccountSettings.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        CustomerAccountSettingsScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.inquiredServices(
    navController: NavController,
) {
    composable(
        route = P4pScreens.InquiredServices.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        InquiredServicesScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.selectAddress(
    navController: NavController,
) {
    composable(
        route = P4pScreens.SelectAddress.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SelectAddressScreen(
            navController = navController,
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.quote(
    navController: NavController,
) {
    composable(
        route = P4pScreens.Quote.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        QuoteScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.tracking(
    navController: NavController,
) {
    composable(
        route = P4pScreens.Tracking.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        TrackingScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.paymentTracking(
    navController: NavController,
) {
    composable(
        route = P4pScreens.PaymentTracking.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        PaymentTrackingScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.displayVisits(
    navController: NavController,
) {
    composable(
        route = P4pScreens.DisplayVisits.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        DisplayVisitsScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.editVisits(
    navController: NavController,
) {
    composable(
        route = P4pScreens.EditVisits.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        EditVisitsScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.inquiry(
    navController: NavController,
) {
    composable(
        route = P4pScreens.Inquiry.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        InquiryScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchedAddrResult(
    navController: NavController,
) {
    composable(
        route = P4pScreens.SearchedAddrResult.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SearchedAddrResultScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addPrvAddress(
    navController: NavController,
) {
    composable(
        route = P4pProviderScreens.AddPrvAddress.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        AddPrvAddressScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsPhone(
    navController: NavController,
) {
    composable(
        route = P4pScreens.SettingsPhone.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SettingsPhoneScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsEmail(
    navController: NavController,
) {
    composable(
        route = P4pScreens.SettingsEmail.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SettingsEmailScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsLanguage(
    navController: NavController,
) {
    composable(
        route = P4pScreens.SettingsLanguage.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SettingsLanguageScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.businessDetails(
    navController: NavController,
) {
    composable(
        route = P4pScreens.BusinessDetails.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        BusinessDetailsScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.prvCstTC(
    navController: NavController,
) {
    composable(
        route = P4pScreens.PrvCstTC.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        PrvCstTCScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.faQuestions(
    navController: NavController,
) {
    composable(
        route = P4pScreens.FaQuestions.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        FaQuestionsScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.faqDashboard(
    navController: NavController,
) {
    composable(
        route = P4pScreens.FAQDashboard.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        FAQDashboardScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.servicesConditions(
    navController: NavController,
) {
    composable(
        route = P4pProviderScreens.ServicesConditions.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ServicesConditionsScreen(
            navController = navController,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.workingScheduleEdit(
    navController: NavController,
) {
    composable(
        route = P4pProviderScreens.WorkingScheduleEdit.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        WorkingScheduleEditScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providedService(
    navController: NavController,
) {
    composable(
        route = P4pProviderScreens.ProvidedService.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProvidedServiceScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providedServiceConditions(
    navController: NavController,
) {
    composable(
        route = P4pProviderScreens.ProvidedServiceConditions.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProvidedServiceConditionsScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.openRequestList(
    navController: NavController,
) {
    composable(
        route = P4pCustomerScreens.OpenRequestList.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        OpenRequestListScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.request(
    navController: NavController,
) {
    composable(
        route = P4pCustomerScreens.Request.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        RequestScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.customerOrder(
    navController: NavController,
) {
    composable(
        route = P4pCustomerScreens.CustomerOrder.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        CustomerOrderScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providerOrder(
    navController: NavController,
) {
    composable(
        route = P4pProviderScreens.ProviderOrder.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProviderOrderScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.notesEdit(
    navController: NavController,
) {
    composable(
        route = P4pScreens.NotesEdit.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        NotesEditScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.orderNotes(
    navController: NavController,
) {
    composable(
        route = P4pScreens.OrderNotes.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        OrderNotesScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.providerOrderFilter(
    navController: NavController,
) {
    composable(
        route = P4pProviderScreens.ProviderOrderFilter.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ProviderOrderFilterScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.closedOrder(
    navController: NavController,
) {
    composable(
        route = P4pScreens.ClosedOrder.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ClosedOrderScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.orderPayment(
    navController: NavController,
) {
    composable(
        route = P4pScreens.OrderPayment.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        OrderPaymentScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.orderVisits(
    navController: NavController,
) {
    composable(
        route = P4pScreens.OrderVisits.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        OrderVisitsScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.paidOrder(
    navController: NavController,
) {
    composable(
        route = P4pScreens.PaidOrder.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        PaidOrderScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.showLocationMapView(
    navController: NavController,
) {
    composable(
        route = P4pScreens.ShowLocationMapView.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        ShowLocationMapViewScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.orderAddrMapview(
    navController: NavController,
) {
    composable(
        route = P4pScreens.OrderAddrMapView.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        OrderAddrMapViewScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.portfolio(
    navController: NavController,
) {
    composable(
        route = P4pScreens.Portfolio.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        PortfolioScreen(
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.selectAlbum(
    navController: NavController,
) {
    composable(
        route = P4pScreens.SelectAlbum.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        SelectAlbumsScreen(navController = navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mediaView(
    navController: NavController,
) {
    composable(
        route = P4pScreens.MediaView.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        MediaViewScreen(navController = navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mediaFullView(
    navController: NavController,
) {
    composable(
        route = P4pScreens.MediaFullView.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        MediaFullViewScreen(navController = navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.portfolioFullView(
    navController: NavController,
) {
    composable(
        route = P4pScreens.PortfolioFullView.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        PortfolioFullViewScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.orderMapGps(
    navController: NavController,
) {
    composable(
        route = P4pScreens.OrderMapGps.route,
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
        exitTransition = { exitTransition() }
    ) {
        OrderMapGpsScreen(
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

    val goToProviderServices: () -> Unit = {
        navController.navigate(P4pShowroomScreens.ProviderServices.route)
    }

    val goToProviderDetails: (providerId: Long?) -> Unit = { providerId ->
        navController.navigate(
            P4pProviderScreens.ProviderProfile.route + "?$PROVIDER_ID=$providerId"
        )
    }

    val goToProviderWorkingSchedule: () -> Unit = {
        navController.navigate(
            P4pShowroomScreens.ProviderWorkingSchedule.route
        )
    }

    val goToServiceInfo: (serviceId: Long) -> Unit = { serviceID ->
        navController.navigate(
            P4pScreens.ServiceInfo.route + "?$ID_KEY=$serviceID"
        )
    }

    val goToServiceInfoEdit: (serviceId: Long) -> Unit = { serviceID ->
        navController.navigate(
            P4pScreens.ServiceInfoEdit.route + "?$ID_KEY=$serviceID"
        )
    }

    val goToServiceContract: () -> Unit = {
        navController.navigate(
            P4pShowroomScreens.ServiceContract.route
        ) {
            launchSingleTop = true
        }
    }

    val goToContactDetails: () -> Unit = {
        navController.navigate(
            P4pScreens.ContactDetails.route
        ) {
            launchSingleTop = true
        }
    }

    val goToRatingDetails: () -> Unit = {
        navController.navigate(
            P4pScreens.RatingDetails.route
        ) {
            launchSingleTop = true
        }
    }

    val goToReviews: () -> Unit = {
        navController.navigate(
            P4pScreens.Reviews.route
        ) {
            launchSingleTop = true
        }
    }

    val goToVerifications: () -> Unit = {
        navController.navigate(
            P4pScreens.Verifications.route
        ) {
            launchSingleTop = true
        }
    }

    val goToDataPrivacy: () -> Unit = {
        navController.navigate(
            LandingScreens.DataPrivacy.route
        ) {
            launchSingleTop = true
        }
    }

    val goToAccount: () -> Unit = {
        navController.navigate(
            P4pScreens.Account.route
        ) {
            launchSingleTop = true
        }
    }

    val goToProviderAccountSettings: () -> Unit = {
        navController.navigate(
            P4pProviderScreens.ProviderAccountSettings.route
        ) {
            launchSingleTop = true
        }
    }

    val goToCustomerAccountSettings: () -> Unit = {
        navController.navigate(
            P4pCustomerScreens.CustomerAccountSettings.route
        ) {
            launchSingleTop = true
        }
    }
}
