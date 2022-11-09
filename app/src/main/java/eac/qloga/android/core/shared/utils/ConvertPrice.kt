package eac.qloga.android.core.shared.utils

fun convertPrice(price: Long): String{
    return String.format("%.2f", price.toFloat().div(100))
}