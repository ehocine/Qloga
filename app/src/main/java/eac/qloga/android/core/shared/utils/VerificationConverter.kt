package eac.qloga.android.core.shared.utils

import eac.qloga.bare.dto.Verification

object VerificationConverter {

    fun verificationToString(
        vrfs: List<Verification?>?
    ): String{
        if(vrfs == null) return ""
        val result = vrfs.map { it?.type?.name }
        if(result.isEmpty()) return ""
        return result.reduce { acc, s -> "$acc, $s" } ?: ""
    }
}