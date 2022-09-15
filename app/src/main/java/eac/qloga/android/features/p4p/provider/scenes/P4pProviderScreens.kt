package eac.qloga.android.features.p4p.provider.scenes

sealed class P4pProviderScreens (
    val route: String,
    val titleName: String = ""
) {
    object ProviderDashboard: P4pProviderScreens("provider_dashboard", "")
    object ProviderOrders: P4pProviderScreens("provider_orders", "")
    object FavouriteCustomers: P4pProviderScreens("favourite_customers", "Favourite Customers")
    object Customers: P4pProviderScreens("customers", "Customers")
    object FavouriteCustomer: P4pProviderScreens("favourite_customer_view", "Customer")

    companion object {
        val listOfScreen = listOf(
            ProviderDashboard,
            ProviderOrders,
            FavouriteCustomers,
            Customers,
            FavouriteCustomer
        )
    }
}