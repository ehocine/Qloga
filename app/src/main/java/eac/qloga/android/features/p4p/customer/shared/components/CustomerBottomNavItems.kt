package eac.qloga.android.features.p4p.customer.shared.components

import eac.qloga.android.R

sealed class CustomerBottomNavItems(
    val label: String,
    val icon: Int,
) {
    object ORDERS: CustomerBottomNavItems("Orders", R.drawable.ic_ql_orders_take)
    object REQUESTS: CustomerBottomNavItems("Requests", R.drawable.ic_ql_hi_hnd)
    object PROVIDERS: CustomerBottomNavItems("Providers", R.drawable.ic_ql_search)
    object FAVOURITES: CustomerBottomNavItems("Favourites", R.drawable.ic_ql_heart)

    companion object{
        val listOfItems by lazy { listOf( ORDERS, REQUESTS, PROVIDERS, FAVOURITES) }
    }
}