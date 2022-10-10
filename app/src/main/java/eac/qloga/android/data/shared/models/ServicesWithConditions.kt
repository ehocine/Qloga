package eac.qloga.android.data.shared.models

import eac.qloga.p4p.lookups.dto.QService
import eac.qloga.p4p.lookups.dto.ServiceCondition

data class ServicesWithConditions (
    val service: QService,
    val unitPrice: Long?,
    val conditions: List<ServiceCondition>?
)