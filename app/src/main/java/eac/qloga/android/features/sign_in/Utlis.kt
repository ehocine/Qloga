package eac.qloga.android.features.sign_in

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertTimeStampToDate(epoch: Long): String {
    val date = Date(epoch * 1000L)
    val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm aaa")
    return sdf.format(date)
}