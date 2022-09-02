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
    object ServiceInfo: P4pShowroomScreens("service_info", "Selected service")
    object ServiceContract: P4pShowroomScreens("service_contract", "Service contract")


    companion object {
        val listOfScreen = listOf(
            NotEnrolled,
            Enrolled,
            AddAddress,
            AddressOnMap,
            Categories,
            ServiceInfo,
            ServiceContract
        )
    }
}