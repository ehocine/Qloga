


sealed class P4pCustomerScreens (
    val route: String,
    val titleName: String = ""
) {
    object CustomerDashboard: P4pCustomerScreens("customer_nav_container", "")
    object CustomerOrders: P4pCustomerScreens("orders", "")
    object FavouriteProviders: P4pCustomerScreens("favourite_providers", "Favourite Providers")
    object OpenRequests: P4pCustomerScreens("open_requests", "Open Requests")

    companion object {
        val listOfScreen:List<P4pCustomerScreens> by lazy{ listOf(
            CustomerDashboard,
            CustomerOrders,
            FavouriteProviders,
            OpenRequests
        )}
    }
}