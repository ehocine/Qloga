package eac.qloga.android.core.shared.utils

object PriceConverter {
    fun priceToFloat(price: Float?): String? {
        // assumption price was multiply by 100
        if (price == null) return null
        return String.format("%.2f", price.div(100))
    }

    fun floatToPrice(price: Float?): Long? {
        // assumption price was multiply by 100
        if (price == null) return null
        return (price*100).toLong()
    }
}