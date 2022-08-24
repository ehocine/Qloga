package eac.qloga.android.features.shared.util

sealed class Screen(
    val route: String,
    val titleName: String = ""
) {
    object SplashScreen : Screen("splash_screen", "Splash Screen")
    object Intro : Screen("intro", "Intro")
    object SignIn : Screen("sign_in", "Sign In")
    object Enrolled : Screen("enrolled", "")
    // object Screen2 : Screen("screen_2", "Screen 2")
    // object ProviderSearch: Screen("provider_search", "Provider Search")
    //object Previews: Screen("previews", "Reviews")
    // object AddressAdd: Screen("address_add", "Address")
    // object CustomerEnrollment: Screen("customer_enrollment", "Enrollment")
    // object ProviderEnrollment: Screen("provider_enrollment", "Enrollment")
    //object CustomerNavContainer: Screen("customer_nav_container", "")
    //object ProviderNavContainer: Screen("provider_nav_container", "")
    //object ProviderSearchEmpty: Screen("provider_search_empty", "Providers search")
    //object ServicesList: Screen("services_list", "Services")
    //object Request: Screen("request", "Request")
    //object PaymentsList: Screen("Payments", "Payments")
    //object OpenRequestList: Screen("open_request_list", "Open Requests")
    //object TestScreen: Screen("test_screen") // only for testing purpose
    //object TestText: Screen("text_test") // only for testing purpose

    companion object {
        val listOfScreen = listOf(
//            ProviderNavContainer,
            //          OpenRequestList,
            //        PaymentsList,
            //           Request,
            //         ServicesList,
            //       ProviderSearchEmpty,
            //     CustomerNavContainer,
            SplashScreen,
            Intro,
            SignIn,
            Enrolled
        )
    }
}