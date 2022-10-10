package eac.qloga.android.features.p4p.shared.utils

import eac.qloga.android.core.shared.utils.PROVIDER_TERMS_CONDITIONS_LINK
import eac.qloga.android.core.shared.utils.QLOGA_TERMS_CONDITIONS_LINK

sealed class ProvidersTabsType(val title: String, val url :String ) {
    object QLOGA: ProvidersTabsType("QLOGA \nTs & Cs", QLOGA_TERMS_CONDITIONS_LINK)
    object PROVIDER: ProvidersTabsType("Provider's \nTs & Cs", PROVIDER_TERMS_CONDITIONS_LINK)

    companion object{
        val listOfItems by lazy { listOf(QLOGA,PROVIDER) }
    }
}