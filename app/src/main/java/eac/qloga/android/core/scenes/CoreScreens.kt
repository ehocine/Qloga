package eac.qloga.android.core.scenes

sealed class CoreScreens(
    val route: String,
    val titleName: String = ""
) {
    object SplashScreen : CoreScreens("splash_screen", "Splash Screen")
    object TestScreen: CoreScreens("test_screen") // only for testing purpose

    companion object {
        val listOfScreen = listOf(
            SplashScreen,
            TestScreen
        )
    }
}