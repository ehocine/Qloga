package eac.qloga.android.features.p4p.shared.utils

sealed class OrdersTabTypes(
    val label: String,
) {
    object Orders: OrdersTabTypes("Orders")
    object Quotes: OrdersTabTypes("Quotes")
    object Inquires: OrdersTabTypes("Inquires")
    object Today: OrdersTabTypes("Today")

    companion object{
        val listOfItems by lazy { listOf(Orders,Quotes,Inquires,Today) }
    }
}