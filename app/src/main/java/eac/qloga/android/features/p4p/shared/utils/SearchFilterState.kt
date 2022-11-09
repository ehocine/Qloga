package eac.qloga.android.features.p4p.shared.utils

import eac.qloga.bare.enums.VerificationType


data class SearchFilterState(
    val distance: Distance = Distance(),
    val returnRate: ReturnRate = ReturnRate(),
    val minStartRating: MinStartRating = MinStartRating(),
    val ordersDelivered: OrdersDelivered = OrdersDelivered(),
    val providersType: List<Int> = emptyList(),
    val providersAdminVerifications: List<Int> = emptyList(),
    val providersVerifications: List<Int> = emptyList(),
    val clearanceCertifications: List<Int> = emptyList()
)

data class Distance(val value: Int = 0, val max: Int = 100)
data class ReturnRate(val value: Int = 0, val max: Int = 100)
data class MinStartRating(val value: Int = 1, val max: Int = 5)
data class OrdersDelivered(val value: Int = 0, val max: Int = 250)

sealed class ProvidersTypeOptions(val label: String) {
    object All : ProvidersTypeOptions("All")
    object Individual : ProvidersTypeOptions("Individual")
    object Agency : ProvidersTypeOptions("Agency")

    companion object {
        val listValue by lazy { listOf(All, Individual, Agency) }
    }
}

sealed class ProvidersAdminVerificationsOptions(
    val label: String,
    val verificationType: VerificationType
) {
    object Id : ProvidersAdminVerificationsOptions("ID", VerificationType.ID)
    object Address : ProvidersAdminVerificationsOptions("Address", VerificationType.ADDRESS)
    object Avatar : ProvidersAdminVerificationsOptions("Avatar", VerificationType.MEDIA)

    companion object {
        val listValue by lazy { listOf(Id, Address, Avatar) }
    }
}

sealed class ProvidersVerificationOptions(
    val label: String,
    val verificationType: VerificationType
) {
    object First :
        ProvidersVerificationOptions("Registration certificate", VerificationType.ORG_REGISTRATION)

    object Second :
        ProvidersVerificationOptions("Professional insurance", VerificationType.ORG_INSURANCE)

    object Third : ProvidersVerificationOptions("Email", VerificationType.ORG_EMAIL)

    companion object {
        val listValue by lazy { listOf(First, Second, Third) }
    }
}

sealed class ClearanceCertificationsOptions(
    val label: String,
    val verificationType: VerificationType,
    val clearanceTypeId: Int
) {
    object First : ClearanceCertificationsOptions(
        "Disclosure Scotland: Basic Disclosure",
        VerificationType.CLEARANCE,
        1
    )

    object Second :
        ClearanceCertificationsOptions(
            "Disclosure Scotland: Protecting Vulnerable Groups",
            VerificationType.CLEARANCE,
            2
        )

    object Third : ClearanceCertificationsOptions(
        "DBS: Disclosure and Barring Service",
        VerificationType.CLEARANCE,
        3
    )

    object Fourth : ClearanceCertificationsOptions(
        "AccessNI: Crimital Records Check",
        VerificationType.CLEARANCE,
        4
    )

    object Fifth : ClearanceCertificationsOptions("None", VerificationType.CLEARANCE, 0)

    companion object {
        val listValue by lazy { listOf(First, Second, Third, Fourth, Fifth) }
    }
}