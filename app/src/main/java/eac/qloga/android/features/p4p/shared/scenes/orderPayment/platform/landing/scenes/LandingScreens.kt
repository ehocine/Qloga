package eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes

sealed class LandingScreens(
    val route: String,
    val titleName: String = ""
) {
    object SignIn: LandingScreens("sign_in", "Sign In")
    object Signup: LandingScreens("sign_up", "Create an account")
    object PostSignup: LandingScreens("post_sign_up", "")
    object SignupTermsConds: LandingScreens("signup_terms_conditions", "Terms & Conditions")
    object DataPrivacy: LandingScreens("data_privacy", "Data Privacy")
    object NoAddress: LandingScreens("no_address", "")

    companion object {
        val listOfScreen by lazy {
            listOf(
                SignIn,
                Signup,
                PostSignup,
                SignupTermsConds,
                DataPrivacy
            )
        }
    }
}