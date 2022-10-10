import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens


sealed class P4pCustomerScreens (
    val route: String,
    val titleName: String = ""
) {
    object CustomerDashboard: P4pCustomerScreens("customer_nav_container", "")
    object CustomerOrders: P4pCustomerScreens("orders", "")
    object FavouriteProviders: P4pCustomerScreens("favourite_providers", "Favourite Providers")
    object OpenRequests: P4pCustomerScreens("open_requests", "Open Requests")
    object CustomerProfile: P4pCustomerScreens("customer_profile", "Customer")
    object CustomerAccountSettings: P4pCustomerScreens("customer_account_settings", "Settings")
    object OpenRequestList: P4pCustomerScreens("open_request_list", "Open Requests")
    object Request: P4pCustomerScreens("request", "Request")
    object CustomerOrder: P4pCustomerScreens("customer_order", "Order")

    companion object {
        val listOfScreen:List<P4pCustomerScreens> by lazy{ listOf(
            CustomerOrder,
            OpenRequestList,
            Request,
            CustomerDashboard,
            CustomerOrders,
            FavouriteProviders,
            OpenRequests,
            CustomerProfile,
            CustomerAccountSettings
        )}
    }
}