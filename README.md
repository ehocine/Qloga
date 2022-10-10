platform/landing/SignInScreen <- object Welcome: Screen("welcome", "")
platform/landing/SingUpScreen <- object Signup: Screen("sign_up", "")
platform/landing/PostSignUpScreen <- object PostSignup: Screen("post_sign_up", "")
platform/landing/SignupTermsCondScreen <- object SignupTermsConditions: Screen("signup_terms_conditions", "Terms & Conditions")
platform/landing/DataPrivacyScreen <- object DataPrivacy: Screen("data_privacy", "Data Privacy")

p4p/showroom/NotEnrolledScreen <- object Intro: Screen("intro", "Intro") 
p4p/showroom/EnrolledScreen <- object OrderListPrv: Screen("order_list_prv", "")
p4p/showroom/AddAddressScreen <- object AddressAdd: Screen("address_add", "Address")
p4p/showroom/AddressOnMapScreen <- object MapView: Screen("map_view", "Address")
p4p/showroom/ServiceCategoriesScreen <- object ServiceCategories: Screen("service_categories", "Service categories")
p4p/showroom/ServiceInfoScreen <- object SelectedService: Screen("selected_service", "Selected service")
p4p/showroom/ServiceContractScreen <- object ServiceContract: Screen("service_contract", "Service contract")
p4p/showroom/ProviderSearchScreen <- object Providers: Screen("providers", "Providers")
p4p/showroom/ProviderDetailsScreen <- object ProviderDetails: Screen("providers_details", "Provider")

p4p/shared/PortfolioAlbumsScreen <- object Albums: Screen("albums", "Albums")
p4p/shared/Tracking <- object Tracking: Screen("tracking", "Tracking")
p4p/shared/PaymentTracking <- Added new
p4p/shared/DisplayVisits <- object Visits: Screen("visits", "Visits")
p4p/shared/EditVisits <- object VisitedDetails: Screen("visited_details", "Visits")
p4p/shared/PaymentsList <- object PaymentsList: Screen("Payments", "Payments")
p4p/shared/SerchedAddrResult <- object AddressSearchResultScreen: Screen("address_search_result", "Address")
p4p/provider/AddPrvAddress <- object AccountSettingsAddress: Screen("account_settings_address", "Address")

p4p/provider/ProviderDashboardScreen <- object ProviderNavContainer: Screen("provider_nav_container", "")
p4p/provider/ProviderOrdersScreen <- object ProviderOrders: Screen("provider_orders", "")
p4p/provider/FavouriteCustomersScreen <- object FavouriteCustomers: Screen("favourite_customers", "Favourite Customers")
p4p/provider/CustomersScreen <- object Customers: Screen("customers", "Customers")
p4p/provider/FavouriteCustomerScreen <- object FavouriteCustomerView: Screen("favourite_customer_view", "Customer")
p4p/provider/ProviderAccountSettingsScreen <- object ProviderAccountSettings: Screen("provider_account_settings", "Settings")


p4p/customer/CustomerDashboardScreen <- object CustomerNavContainer: Screen("customer_nav_container", "")
p4p/customer/CustomerOrdersScreen <- object CustomerOrders: Screen("orders", "")
p4p/customer/FavouriteProvidersScreen <- object FavouriteProviders: Screen("favourite_providers", "Favourite Providers")
p4p/customer/OpenRequestsScreen <- object OpenRequest: Screen("open_request", "Open Requests")
p4p/customer/CustomerAccountSettingsScreen <- object CustomerAccountSettings: Screen("customer_account_settings", "Settings")
p4p/customer/CustomerOrder <- object CustomerOrder: Screen("customer_order", "Order")
p4p/customer/openRequestList <- object OpenRequestList: Screen("open_request_list", "Open Requests")
p4p/customer/request <- object Request: Screen("request", "Request")

p4p/shared/EnrollmentScreen <- object CustomerEnrollment: Screen("customer_enrollment", "Enrollment")
p4p/shared/VerifyPhoneSceen <- object VerifyPhone: Screen("verify_phone", "Verify phone")
p4p/shared/ConfirmAddressScreen <- object ConfirmAddress: Screen("confirm_address", "Confirm address")
p4p/shared/SelectLocationMap <- object SelectLocationMap: Screen("select_location_map","Your location")
p4p/shared/IdVerificationScreen <- object IDVerification: Screen("id_verification", "Identity verification")
p4p/shared/PassportScreen <- object Passport: Screen("passport","Passport")
p4p/shared/EnrollmentTcScreen <- object EnrollmentTermsConditions: Screen("enrollment_terms_conditions", "Terms & Conditions")

p4p/shared/InquiredServicesScreen <- object Services: Screen("services", "Services")
p4p/shared/Quote <- object Quote: Screen("quote", "Quote")
p4p/shared/InquiryScreen <- object Inquiry: Screen("inquiry_incomplete", "Inquiry(incomplete)")
p4p/shared/SelectAddress <- object Address: Screen("address", "Address") 

p4p/shared/RatingDetailsScreen <- object RatingDetails: Screen("rating_details","Rating")
p4p/shared/ReviewsScreen <- object Previews: Screen("previews", "Reviews")
p4p/shared/Verifications <- object Verifications: Screen("verifications", "Verifications")
p4p/shared/ContactsScreen <- object Contacts: Screen("contacts", "Contacts")
p4p/shared/Account <- object Account: Screen("account", "Your account")


// object AddressSearchResultScreen: Screen("address_search_result", "Address")
// object ProviderEnrollment: Screen("provider_enrollment", "Enrollment")
// object GotoProfile: Screen("goto_profile", "Goto profile")

p4p/provider/WorkingScheduleEdit <- object WorkingSchedule: Screen("working_schedule", "Working schedule")
p4p/provider/ProvidedService  <- object ProvidedService: Screen("provided_service", "Provided service")
p4p/provider/ProvidedServiceConditions <- object Conditions: Screen("conditions", "Conditions")

p4p/shared/SettingsPhone  <- object AccountPhoneVerify: Screen("account_phone_verify", "Phone")
p4p/shared/SettingsEmail <- object AccountEmailVerify: Screen("account_email_verify", "Email(Verified)")
p4p/shared/SettingsLanguage <- object LanguageSelection: Screen("language_selection", "Spoken language")
p4p/shared/BusinessDetails <- object BusinessDetails: Screen("business_details", "Business details")

// object SettingsAddressSearchResult: Screen("settings_address_search_result", "Address")

p4p/shared/PrvCstTC <- object TermsConditions: Screen("terms_conditions", "Terms & Conditions")
p4p/shared/FAQDashboard <- object FAQ: Screen("faq", "F.A.Q.")
p4p/shared/FaQuestions <- object FaqQuestions: Screen("faq_questions", "Questions")
p4p/provider/ServicesConditions <- object ServicesConditions: Screen("services_conditions", "Services & Conditions")
p4p/provider/ProviderOrder <- object ProviderOrder: Screen("provider_order", "Order")
p4p/provider/ProviderOrderFilter <- object ProviderOrderFilter: Screen("provider_order_filter", "Order")

p4p/shared/Portfolio <- object Portfolio: Screen("portfolio","Portfolio")
p4p/shared/PortfolioFullView <- object FullImageScreen: Screen("full_image_screen")
p4p/shared/MediaView <- object MediaView: Screen("media_view", "")
p4p/shared/MediaFullView <- object MediaFullView: Screen("media_full_view", "")
p4p/shared/SelectAlbum <- object SelectAlbum: Screen("select_album", "Select to move")

// p4p/customer/requestEdit <- object RequestDetails: Screen("request_details", "Request")
// object ProviderSearchEmpty: Screen("provider_search_empty", "Providers search")
// object ProviderOrders: Screen("provider_orders", "")
//object FavouriteProviderView: Screen("favourite_provider_view", "Provider")

p4p/shared/Services <- object ServicesList: Screen("services_list", "Services")
p4p/shared/NotesEdit <- object Notes: Screen("notes", "Notes")
p4p/shared/OrderNotes <- object OrderNotes: Screen("order_notes", "Notes")

// object CustomerDetails: Screen("customer_details", "Customer")
// object NegotiationAddressAdd: Screen("negotiation_address_add", "Address")

p4p/shared/PaidOrder <- object PaidOrder: Screen("paid_order", "Order")
p4p/shared/OrderPayment <- object OrderPayment: Screen("order_payment", "Payments")
p4p/shared/OrderVisits <- object OrderVisits: Screen("order_visits", "Visits: ")
p4p/shared/ClosedOrder <- object ClosedOrder: Screen("closed_order", "Order")
// object OrderProviderProfile: Screen("order_provider_profile", "Provider")
// object OrderCustomerProfile: Screen("order_customer_profile", "Customer")

p4p/shared/ShowLocationMapView <- object ShowLocationMapView: Screen("show_map_location_view", "Address")
p4p/shared/OrderMapGps <- object OrderMapGps: Screen("order_map_gps", "Order")
p4p/shared/OrderAddrMapView <- object OrderAddressMapView: Screen("order_address_map_view", "Address")

// object ProviderPublicProfileView: Screen("provider_public_profile_view", "Provider")
// object CustomerPublicProfileView: Screen("customer_public_profile_view", "Customer")



notes :- 
component name changes:
TopCardItem -> SelectedServicesItem
BottomCardItem -> QuoteOptionItem 

route name changes:
.Services.route -> InquiredServices.rote 
.Address.route -> SelectAddress.route 
.Visits.route -> DisplayVisits.route 
.VisitsDetails.route -> EditVisits.route 
.SelectService.route -> ServiceInfo.route | ServiceInfoEdit.route 
.AddressSearchResultScreen.route | SettingsAddressSearchResult.route -> SearchedAddrResult.route