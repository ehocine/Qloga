package eac.qloga.android.features.platform.landing.scenes

sealed class LandingScreens(
    val route: String,
    val titleName: String = ""
) {
    object SignIn : LandingScreens("sign_in", "Sign In")

    companion object {
        val listOfScreen = listOf(
            SignIn
        )
    }
}