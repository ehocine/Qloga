package eac.qloga.android.features.p4p.provider.shared.utils

import eac.qloga.android.R

sealed class ProviderBottomNavItems(
    val label: String,
    val icon: Int,
) {
    object ORDERS: ProviderBottomNavItems("Orders", R.drawable.ic_ql_orders_take)
    object CUSTOMERS: ProviderBottomNavItems("Customers", R.drawable.ic_ql_search)
    object FAVOURITES: ProviderBottomNavItems("Favourites", R.drawable.ic_ql_heart)

    companion object{
        val listOfItems by lazy { listOf( ORDERS, CUSTOMERS, FAVOURITES) }
    }
}