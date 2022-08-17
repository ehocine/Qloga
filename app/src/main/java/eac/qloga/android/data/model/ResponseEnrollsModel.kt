package eac.qloga.android.data.model

import eac.qloga.p4p.cst.dto.Customer
import eac.qloga.p4p.prv.dto.Provider

data class ResponseEnrollsModel(
    val CUSTOMER: Customer? = null,
    val PROVIDER: List<Provider>? = null
)
