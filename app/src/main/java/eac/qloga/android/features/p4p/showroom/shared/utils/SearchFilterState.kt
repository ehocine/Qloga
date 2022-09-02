package eac.qloga.android.features.p4p.showroom.shared.utils


data class SearchFilterState(
    val distance: Distance = Distance(),
    val returnRate: ReturnRate = ReturnRate(),
    val minStartRating: MinStartRating = MinStartRating(),
    val ordersDelivered: OrdersDelivered = OrdersDelivered(),
    val providersType: ProvidersType = ProvidersType(),
    val providersAdminVerifications: ProvidersAdminVerifications = ProvidersAdminVerifications(),
    val providersVerifications: ProvidersVerifications = ProvidersVerifications(),
    val clearanceCertifications: ClearanceCertifications = ClearanceCertifications()
)

data class Distance(val value: Int = 0, val max:Int = 100)
data class ReturnRate(val value: Int = 0, val max: Int = 100)
data class MinStartRating( val value: Int = 0, val max:Int = 10)
data class OrdersDelivered( val value: Int = 0, val max:Int = 250)

data class ProvidersType( val value: ProvidersTypeOptions = ProvidersTypeOptions.All)

data class ProvidersAdminVerifications(
    val value: ProvidersAdminVerificationsOptions = ProvidersAdminVerificationsOptions.Id
)

data class ProvidersVerifications(
    val value: ProvidersVerificationOptions = ProvidersVerificationOptions.First
)

data class ClearanceCertifications(
    val value: ClearanceCertificationsOptions = ClearanceCertificationsOptions.First
)


sealed class ProvidersTypeOptions(val label: String){
    object All: ProvidersTypeOptions("All")
    object Individual: ProvidersTypeOptions("Individual")
    object Agency: ProvidersTypeOptions("Agency")

    companion object{
        val listValue by lazy  { listOf(All, Individual, Agency) }
    }
}

sealed class ProvidersAdminVerificationsOptions(val label: String){
    object Id: ProvidersAdminVerificationsOptions("ID")
    object Address: ProvidersAdminVerificationsOptions("Address")
    object Avatar: ProvidersAdminVerificationsOptions("Avatar")

    companion object{
        val listValue by lazy { listOf(Id, Address, Avatar) }
    }
}

sealed class ProvidersVerificationOptions(val label: String){
    object First: ProvidersVerificationOptions("Registration certificate")
    object Second: ProvidersVerificationOptions("Professional insurance")
    object Third: ProvidersVerificationOptions("Email")

    companion object{
        val listValue by lazy { listOf(First, Second, Third) }
    }
}

sealed class ClearanceCertificationsOptions(val label: String){
    object First: ClearanceCertificationsOptions("Disclosure Scotland: Basic Disclosure")
    object Second: ClearanceCertificationsOptions("Disclosure Scotland: Protecting Vulnerable Groups")
    object Third: ClearanceCertificationsOptions("DBS: Disclosure and Barring Service")
    object Fourth: ClearanceCertificationsOptions("AccessNI: Crimital Records Check")
    object Fifth: ClearanceCertificationsOptions("None")

    companion object{
        val listValue by lazy { listOf(First, Second, Third, Fourth, Fifth) }
    }
}