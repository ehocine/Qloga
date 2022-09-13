package eac.qloga.android.features.platform.landing.scenes

sealed class LandingScreens(
    val route: String,
    val titleName: String = ""
) {
    object SignIn: LandingScreens("sign_in", "Sign In")
    object Signup: LandingScreens("sign_up", "")
    object PostSignup: LandingScreens("post_sign_up", "")
    object NoAddress: LandingScreens("no_address", "")

    companion object {
        val listOfScreen = listOf(
            SignIn,
            Signup,
            PostSignup
        )
    }
}