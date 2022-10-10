package eac.qloga.android

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import eac.qloga.android.core.shared.utils.QTAG

private const val TAG = "${QTAG}-MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BuildScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                Log.d(TAG, "Nav controller new destination. Arguments: $arguments")
            }

            val actions = remember(navController) { NavigationActions(navController) }

            AnimatedNavHost(
                navController = navController,
                startDestination = CoreScreens.SplashScreen.route,
                builder = {
                    splash(navController, actions)
                    notEnrolled(navController)
                    signIn(navController)
                    enrolled(navController, actions)
                    addressAdd(navController)
                    addressOnMap(navController)
                    categories(navController)
                    serviceInfo(actions)
                    serviceInfoEdit(actions)
                    serviceContract(navController)
                    signup(navController)
                    postSignup(navController)
                    signupTermsConds(navController)
                    providers(navController)
                    providersDetails(navController,actions)
                    albums(navController)
                    providerDashboard(navController)
                    providerOrders(navController)
                    favouriteCustomers(navController)
                    customers(navController)
                    favouriteCustomer(navController)
                    noAddress(navController)
                    chooseNewAddress(navController)
                    saveNewAddress(navController)
                    providerServices(navController)
                    providerWorkingSchedule(navController)
                    ratingDetails(actions)
                    reviews(actions)
                    contactDetails(actions)
                    verifications(actions)
                    dataPrivacy(actions)
                    enrollment(navController)
                    verifyPhone(navController)
                    confirmAddress(navController)
                    userLocationMapView(navController)
                    identityVerification(navController)
                    passport(navController)
                    enrollmentTermsConditions(navController)
                    customerDashboard(navController)
                    customerOrders(navController)
                    favouriteProviders(navController)
                    openRequests(navController)
                    account(navController)
                    providerAccountSettings(navController)
                    customerAccountSettings(navController)
                    inquiredServices(navController)
                    selectAddress(navController)
                    quote(navController)
                    tracking(navController)
                    paymentTracking(navController)
                    displayVisits(navController)
                    editVisits(navController)
                    inquiry(navController)
                    searchedAddrResult(navController)
                    addressAdd(navController)
                    addPrvAddress(navController)
                    settingsPhone(navController)
                    settingsEmail(navController)
                    settingsLanguage(navController)
                    businessDetails(navController)
                    prvCstTC(navController)
                    faqDashboard(navController)
                    faQuestions(navController)
                    servicesConditions(navController)
                    workingScheduleEdit(navController)
                    providedService(navController)
                    providedServiceConditions(navController)
                    openRequestList(navController)
                    request(navController)
                    customerOrder(navController)
                    providerOrder(navController)
                    notesEdit(navController)
                    orderNotes(navController)
                    providerOrderFilter(navController)
                    closedOrder(navController)
                    orderPayment(navController)
                    orderVisits(navController)
                    paidOrder(navController)
                    showLocationMapView(navController)
                    orderAddrMapview(navController)
                    orderMapGps(navController)
                }
            )
        }
    }
}
