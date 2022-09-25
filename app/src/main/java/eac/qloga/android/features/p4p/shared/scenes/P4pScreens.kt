package eac.qloga.android.features.p4p.shared.scenes

import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens

sealed class P4pScreens (
    val route: String,
    val titleName: String = ""
) {
    object Enrollment: P4pScreens("enrollment", "Enrollment")
    object VerifyPhone: P4pScreens("verify_phone", "Verify phone")
    object ConfirmAddress: P4pScreens("confirm_address", "Confirm address")
    object SelectLocationMap: P4pScreens("select_location_map","Your location")
    object IdVerification: P4pScreens("id_verification", "Identity verification")
    object Passport: P4pScreens("passport","Passport")
    object EnrollmentTermsConditions: P4pScreens("enrollment_terms_conditions", "Terms & Conditions")
    object ProviderSearch: P4pScreens("provider_search", "Providers")


    companion object {
        val listOfScreen:List<P4pScreens> by lazy{ listOf(
            Enrollment,
            VerifyPhone,
            ConfirmAddress,
            SelectLocationMap,
            IdVerification,
            Passport,
            EnrollmentTermsConditions
        )}

    }
}