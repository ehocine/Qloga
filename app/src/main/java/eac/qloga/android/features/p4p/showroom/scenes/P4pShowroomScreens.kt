package eac.qloga.android.features.p4p.showroom.scenes

sealed class P4pShowroomScreens(
    val route: String,
    val titleName: String = ""
) {
    object NotEnrolled : P4pShowroomScreens("not_enrolled", "Not Enrolled")
    object Enrolled : P4pShowroomScreens("enrolled", "Enrolled")
    object AddAddress: P4pShowroomScreens("add_address", "Address")
    object AddressOnMap: P4pShowroomScreens("address_on_map", "Address")
    object Categories: P4pShowroomScreens("service_categories", "Service categories")
    object ServiceContract: P4pShowroomScreens("service_contract", "Service contract")
    object ProviderDetails: P4pShowroomScreens("providers_details", "Provider")
    object PortfolioAlbums: P4pShowroomScreens("portfolio_albums", "Albums")
    object ProviderServices: P4pShowroomScreens("provider_services", "Provider Services")
    object ProviderWorkingSchedule: P4pShowroomScreens("provider_working_schedule", "Working schedule")

    companion object {
        val listOfScreen: List<P4pShowroomScreens> by lazy{
            listOf(
                NotEnrolled,
                Enrolled,
                AddAddress,
                AddressOnMap,
                Categories,
                ProviderWorkingSchedule,
                ServiceContract,
                ProviderDetails,
                PortfolioAlbums
            )
        }
    }
}