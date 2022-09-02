package eac.qloga.android.core.shared.utils

import android.util.Log
import java.util.*

private const val TAG = "TimeConverter"
object TimeConverter {

    fun formatToString(value: String): String
    {
        var hour = ""
        var minute = ""
        var amOrPm = ""
        try {
            val time = value.split(":")

            /**
             *  @return format like:- 09:50 AM
             * If the size of array is less than 2 than
             *  the incoming data format is not right .
             * */
            if(time.size < 2) return ""

            val hourIn24 = time[0].toInt()
            val min = time[1].toInt()

            if(hourIn24 > 12)
            {
                hour = if(hourIn24 - 12 < 10) "0${hourIn24 - 12}" else (hourIn24 - 12).toString()
                minute = if(min < 10 ) "0$min" else min.toString()
                amOrPm = "PM"
            }else if(hourIn24 == 0) {
                hour = "12"
                minute = if(min < 10 ) "0$min" else min.toString()
                amOrPm = "AM"
            }else {
                hour = if(hourIn24 < 10) "0${hourIn24}" else (hourIn24).toString()
                minute = if(min < 10 ) "0$min" else min.toString()
                amOrPm = "AM"
            }
        }catch (e: Exception){
            Log.d(TAG, "formatToString: ${e.printStackTrace()}")
        }
        return "$hour:$minute $amOrPm"
    }

    fun getCurrentHour(): Int
    {
        val c = Calendar.getInstance()
        return c.get(Calendar.HOUR_OF_DAY)
    }

    fun getCurrentMinute(): Int
    {
        val c = Calendar.getInstance()
        return c.get(Calendar.MINUTE)
    }
}