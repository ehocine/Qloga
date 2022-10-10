package eac.qloga.android.features.p4p.shared.utils

import eac.qloga.android.core.shared.utils.CUSTOMER_TERMS_CONDITIONS_LINK
import eac.qloga.android.core.shared.utils.QLOGA_TERMS_CONDITIONS_LINK

sealed class CustomerTabsType(val title: String, val url :String ) {
    object QLOGA: ProvidersTabsType("QLOGA \nTs & Cs", QLOGA_TERMS_CONDITIONS_LINK)
    object CUSTOMER: ProvidersTabsType("Customer's \nTs & Cs", CUSTOMER_TERMS_CONDITIONS_LINK)

    companion object{
        val listOfItems by lazy { listOf(QLOGA, CUSTOMER) }
    }
}