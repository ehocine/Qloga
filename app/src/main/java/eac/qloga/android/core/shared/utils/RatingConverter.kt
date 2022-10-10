package eac.qloga.android.core.shared.utils

import eac.qloga.p4p.core.dto.Rating

object RatingConverter {

    fun ratingToNorm(rating: Float?): Number? {
        if(rating == null) return null
        val result = String.format("%.1f",(rating / 1000)).toFloat()
        if(result <= 0f) return 0
        return result
    }

    fun ratingAvg(ratings: List<Rating>?): Number?{
        if(ratings.isNullOrEmpty()) return null
        var totalRating  = 0f
        ratings.forEach{
            totalRating += it.rating.toFloat()
        }
        return ratingToNorm(totalRating/ratings.size)
    }
}