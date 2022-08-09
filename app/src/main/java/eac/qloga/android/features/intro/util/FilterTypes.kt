package eac.qloga.android.features.intro.util

sealed class FilterTypes(
    val label: String
){
    object Distance: FilterTypes("DISTANCE(miles): ")
    object ReturnRate: FilterTypes("RETURN RATE: ")
    object MinStartRating: FilterTypes("MINIMUM START RATING: ")
    object OrdersDelivered: FilterTypes("ORDERS DELIVERED: ")
    object ProvidersType: FilterTypes("PROVIDERS TYPE ")
    object ProviderAdminVerifications: FilterTypes("PROVIDER ADMIN VERIFICATIONS ")
    object ProviderVerifications: FilterTypes("PROVIDER VERIFICATIONS ")
    object ClearanceCertificates: FilterTypes("CLEARANCE CERTIFICATES ")

    companion object{
        val sliderTypeFilterList by lazy { listOf(Distance, ReturnRate, MinStartRating, OrdersDelivered) }
    }
}
