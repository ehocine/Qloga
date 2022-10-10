package eac.qloga.android.features.p4p.provider.scenes

import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens

sealed class P4pProviderScreens (
    val route: String,
    val titleName: String = ""
) {
    object ProviderDashboard: P4pProviderScreens("provider_dashboard", "")
    object ProviderOrders: P4pProviderScreens("provider_orders", "")
    object FavouriteCustomers: P4pProviderScreens("favourite_customers", "Favourite Customers")
    object Customers: P4pProviderScreens("customers", "Customers")
    object ProviderProfile: P4pProviderScreens("provider_profile", "Provider")
    object ProviderAccountSettings: P4pProviderScreens("provider_account_settings", "Settings")
    object ServicesConditions: P4pProviderScreens("services_conditions", "Services & Conditions")
    object WorkingScheduleEdit: P4pProviderScreens("working_hours_schedule_edit", "Working schedule")
    object ProvidedService: P4pProviderScreens("provided_service", "Provided service")
    object ProvidedServiceConditions: P4pProviderScreens("provided_service_conditions", "Conditions")
    object ProviderOrder: P4pProviderScreens("provider_order", "Order")
    object ProviderOrderFilter: P4pProviderScreens("provider_order_filter", "Order")
    object AddPrvAddress: P4pProviderScreens("add_provider_address", "Address")

    companion object {
        val listOfScreen by lazy {
            listOf(
                AddPrvAddress,
                ProviderOrder,
                ProviderOrderFilter,
                ProviderDashboard,
                ProviderOrders,
                FavouriteCustomers,
                Customers,
                ProviderProfile,
                ProviderAccountSettings,
                ServicesConditions,
                WorkingScheduleEdit,
                ProvidedService,
                ProvidedServiceConditions
            )
        }
    }
}