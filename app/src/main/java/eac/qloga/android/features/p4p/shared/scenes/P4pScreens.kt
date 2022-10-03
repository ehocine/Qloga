package eac.qloga.android.features.p4p.shared.scenes


sealed class P4pScreens (
    val route: String,
    val titleName: String = ""
) {
    object Enrollment: P4pScreens("enrollment", "Enrollment")
    object VerifyPhone: P4pScreens("verify_phone", "Verify phone")
    object ConfirmAddress: P4pScreens("confirm_address", "Confirm address")
    object ChoosingNewAddress : P4pScreens("choosing_new_address", "Choosing new address")
    object SaveNewAddress : P4pScreens("save_new_address", "Save new address")
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
            ChoosingNewAddress,
            SaveNewAddress,
            SelectLocationMap,
            IdVerification,
            Passport,
            EnrollmentTermsConditions,
            ProviderSearch
        )}
    }
}