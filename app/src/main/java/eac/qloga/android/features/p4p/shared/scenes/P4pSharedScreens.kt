package eac.qloga.android.features.p4p.shared.scenes

sealed class P4pSharedScreens(
    val route: String,
    val titleName: String = ""
) {
    object ServiceInfo: P4pSharedScreens("service_info", "Selected service")
    object ServiceInfoEdit: P4pSharedScreens("service_info_edit", "Selected service")
    object ContactDetails: P4pSharedScreens("contact_details", "Contacts")
    object RatingDetails: P4pSharedScreens("rating_details", "Rating")
    object Reviews: P4pSharedScreens("reviews", "Reviews")
    object Verifications: P4pSharedScreens("verifications", "Verifications")

    companion object {
        val listOfScreen: List<P4pSharedScreens> by lazy{
            listOf(
                ServiceInfo,
                ServiceInfoEdit,
                ContactDetails,
                RatingDetails,
                Reviews,
                Verifications
            )
        }
    }
}