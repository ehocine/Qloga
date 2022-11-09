package eac.qloga.android.features.p4p.provider.shared.utils

import eac.qloga.bare.enums.VerificationType


sealed class CustomerVerificationOptions(
    val label: String,
    val verificationType: VerificationType
) {
    object Id : CustomerVerificationOptions("ID", VerificationType.ID)
    object Address : CustomerVerificationOptions("Address", VerificationType.ADDRESS)
    object Avatar : CustomerVerificationOptions("Avatar", VerificationType.MEDIA)

    companion object {
        val itemList by lazy { listOf(Id, Address, Avatar) }
    }
}