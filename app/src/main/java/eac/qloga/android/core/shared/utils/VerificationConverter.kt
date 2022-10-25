package eac.qloga.android.core.shared.utils

import eac.qloga.bare.dto.Verification

object VerificationConverter {

    fun verificationToString(
        vrfs: List<Verification?>?
    ): String{
        if(vrfs == null) return ""
        val result = vrfs.map { vrf -> vrf?.type?.name?.lowercase()?.replaceFirstChar { it.uppercase() } }
        if(result.isEmpty()) return ""
        return result.reduce { acc, s -> "$acc, $s" } ?: ""
    }
}