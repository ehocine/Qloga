package eac.qloga.android.features.p4p.shared.utils

data class VisitInfo(
    val date: String ,
    val weekOfDay: String,
    val time: List<VisitedTime>? = null
)

data class VisitedTime(
    val start: String,
    val end: String
)