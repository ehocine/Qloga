package eac.qloga.android.features.p4p.shared.utils

import androidx.compose.ui.graphics.Color
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.theme.orange1

sealed class OrderCategory(
    val label: String,
    val color: Color,
) {
    object FundsReservation: OrderCategory(label ="Funds reservation is needed", color = green1)
    object Paid: OrderCategory(label ="Paid", color = orange1)
    object Closed: OrderCategory(label ="Closed", color = orange1)
    object Accept: OrderCategory(label ="Accept", color = orange1)
    object ProviderIsNear: OrderCategory(label ="Provider is near", color = orange1)
    object Arrived: OrderCategory(label ="Arrived", color = orange1)
    object Completed: OrderCategory(label = "Completed", color = Color.Black)
    object PaymentProcessing: OrderCategory(label ="Payment processing", color = orange1)
}