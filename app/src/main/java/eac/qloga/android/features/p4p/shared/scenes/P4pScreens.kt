package eac.qloga.android.features.p4p.shared.scenes

import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens

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
    object ServiceInfo: P4pScreens("service_info", "Selected service")
    object ServiceInfoEdit: P4pScreens("service_info_edit", "Selected service")
    object ContactDetails: P4pScreens("contact_details", "Contacts")
    object RatingDetails: P4pScreens("rating_details", "Rating")
    object Reviews: P4pScreens("reviews", "Reviews")
    object Verifications: P4pScreens("verifications", "Verifications")
    object Account: P4pScreens("account", "Your account")
    object InquiredServices: P4pScreens("inquired_services", "Services")
    object SelectAddress: P4pScreens("select_address", "Address")
    object Quote: P4pScreens("quote", "Quote")
    object Inquiry: P4pScreens("inquiry", "Inquiry")
    object SettingsPhone: P4pScreens("settings_phone", "Phone")
    object SettingsEmail: P4pScreens("settings_email", "Email")
    object SettingsLanguage: P4pScreens("settings_language", "Spoken language")
    object BusinessDetails: P4pScreens("business_details", "Business details")
    object PrvCstTC: P4pScreens("provider_customer_terms_and_conditions", "Terms & conditions")
    object FAQDashboard: P4pScreens("faq_dashboard", "F.A.Q")
    object FaQuestions: P4pScreens("frequently_asked_questions", "Questions")
    object Services: P4pScreens("services", "Services")
    object NotesEdit: P4pScreens("notes_edit", "Notes")
    object OrderNotes: P4pScreens("order_notes", "Notes")
    object ClosedOrder: P4pScreens("closed_order", "Order")
    object OrderPayment: P4pScreens("order_payment", "Payments")
    object PaidOrder: P4pScreens("paid_order", "Order")
    object OrderVisits: P4pScreens("order_visits", "Visits")
    object DisplayVisits: P4pScreens("display_visits", "Visits")
    object EditVisits: P4pScreens("edit_visits", "Visits")
    object MediaView: P4pScreens("media_view", "")
    object MediaFullView: P4pScreens("media_full_view", "")
    object PaymentTracking: P4pScreens("payment_tracking", "Tracking")
    object PaymentsList: P4pScreens("payments_list", "Payments")
    object OrderMapGps: P4pScreens("order_map_gps", "Order")
    object OrderAddrMapView: P4pScreens("order_address_map_view", "Address")
    object Portfolio: P4pScreens("portfolio", "Portfolio")
    object PortfolioFullView: P4pScreens("portfolio", "Portfolio")
    object Tracking: P4pScreens("tracking", "Tracking")
    object SearchedAddrResult: P4pScreens("searched_address_result", "Address")
    object SelectAlbum: P4pScreens("select_album", "Select to move")
    object ShowLocationMapView: P4pScreens("show_location_map_view", "Address")

    companion object {
        val listOfScreen:List<P4pScreens> by lazy{ listOf(
            Tracking,
            SearchedAddrResult,
            SelectAlbum,
            ShowLocationMapView,
            Portfolio,
            PortfolioFullView,
            PaymentsList,
            PaymentTracking,
            OrderMapGps,
            OrderAddrMapView,
            MediaView,
            MediaFullView,
            DisplayVisits,
            EditVisits,
            OrderPayment,
            PaidOrder,
            OrderVisits,
            NotesEdit,
            OrderNotes,
            Services,
            SettingsPhone,
            SettingsEmail,
            SettingsLanguage,
            BusinessDetails,
            Enrollment,
            VerifyPhone,
            ConfirmAddress,
            ChoosingNewAddress,
            SaveNewAddress,
            SelectLocationMap,
            IdVerification,
            Passport,
            EnrollmentTermsConditions,
            ServiceInfo,
            ServiceInfoEdit,
            ContactDetails,
            RatingDetails,
            Reviews,
            Verifications,
            InquiredServices,
            SelectAddress,
            Quote,
            Inquiry,
            PrvCstTC,
            FAQDashboard,
            FaQuestions,
            ProviderSearch
        )}
    }
}