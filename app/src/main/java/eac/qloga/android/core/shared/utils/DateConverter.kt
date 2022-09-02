package eac.qloga.android.core.shared.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

enum class Months(val month: String){
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December")
}

private const val TAG = "DateConverter"

object DateConverter {
    private val simpleDateFormat = SimpleDateFormat("MMMM d, yyyy")
    private val calendar: Calendar = Calendar.getInstance()

    fun longToDate(longVal: Long): Date
    {
        return Date(longVal)
    }

    fun longToStringDate(longVal: Long): String
    {
        val sdf = SimpleDateFormat("dd MMM yyyy ")
        val date = Date(longVal)
        return sdf.format(date)
    }

    fun dateToLong(dateVal: Date): Long{
        return (dateVal.time)/1000
    }

    fun dateToString(dateVal: Date): String{
        return simpleDateFormat.format(dateVal)
    }

    fun stringToDate(stringVal: String): Date {
        return simpleDateFormat.parse(stringVal)?: throw NullPointerException("Given String value can't be converted to date ")
    }

    fun getTimeInMillis(): Long{
        return System.currentTimeMillis()
    }

    // 22 Feb 2022 format
    // parameters year, month, day format
    fun dayMonthYear(year: String, month: String, day: String ): String
    {
        try {
            val textMonth = getTextMonth(month.toInt())
            return "$day ${textMonth.substring(0,3)}, $year"
        }catch (e: Exception){
            Log.e(TAG, "toTextMonth: ${e.printStackTrace()}")
        }
        return ""
    }

    /**
     * @param date should be in format 2022-01 or 3003-03-34
     *  @returns year with text month like
     *  eg: January 2022, January, 23 2022
     * */
    fun toTextMonth(date: String ): String
    {
        try {
            val dateArr = date.split("-")
            if(dateArr.size < 2) return ""
            val year = dateArr[0]
            val textMonth = getTextMonth(dateArr[1].toInt())
            return if(dateArr.size == 3) "$textMonth, ${dateArr[2]} $year" else "$textMonth, $year"
        }catch (e: Exception){
            Log.e(TAG, "toTextMonth: ${e.printStackTrace()}")
        }
        return ""
    }

    /**
     * @param date 2022-01-03
     *  @returns Jan 03
     * */
    fun toTextMonthAndDay(date: String ): String
    {
        val dateArr = date.split("-")
        if(dateArr.size < 3) return ""
        val textMonth = getTextMonth(dateArr[1].toInt())
        val day = dateArr[2]
        return  "$textMonth $day"
    }

    private fun getTextMonth(month: Int): String
    {
        val textMonth: String = when(month)
        {
            1 -> {
                Months.JANUARY.month}
            2 -> {
                Months.FEBRUARY.month}
            3 -> {
                Months.MARCH.month}
            4 -> {
                Months.APRIL.month}
            5 -> {
                Months.MAY.month}
            6 -> {
                Months.JUNE.month}
            7 -> {
                Months.JULY.month}
            8 -> {
                Months.AUGUST.month}
            9 -> {
                Months.SEPTEMBER.month}
            10 -> {
                Months.OCTOBER.month}
            11 -> {
                Months.NOVEMBER.month}
            12 -> {
                Months.DECEMBER.month}
            else -> { "" }
        }
        return textMonth
    }

    /**
     * Returns the current year in string
     * eg: 2022
     * */
    fun getThisYear() : String
    {
        return "${ calendar.get(Calendar.YEAR) }"

    }

    /**
     * Returns the current year and month in string
     * eg: 2022-03
     * */
    fun getThisYearMonth() : String
    {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1

        var monthString = "$month"

        if(month < 10) {
            monthString = "0$month"
        }
        return "$year-$monthString"
    }


    /**
     * Returns the Year Month and Day of current date
     *  For eg format id 2022-01-23
     * if day is 1 , gives yesterday
     * **/
    fun getYearMonthDay(day: Int = 0): String
    {
        //calendar.get(Calendar.)
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val _day = calendar.get(Calendar.DAY_OF_MONTH) - day

        var monthString = "$month"
        var dayString = "$_day"

        if(month < 10) {
            monthString = "0$month"
        }

        if(_day < 10)
        {
            dayString = "0$_day"
        }

        return "$year-$monthString-$dayString"
    }

    /***
     *  return list of date with the format [2022-02-34] which
     *  are last week dates
     * */
    fun getLastWeek(
        week: Int = -1
    ) : List<String>
    {
        val c = Calendar.getInstance()

        val weeks = mutableListOf<String>()

        // Set the calendar to this week of the current week
        //c[Calendar.DAY_OF_WEEK] = Calendar.DAY_OF_WEEK

        // Format the date in 2022-02-34 format
        val df = SimpleDateFormat("yyyy-MM-dd")

        // looping the 7 weeks day and adding in weeks list
        for (i in 0..6) {
            weeks.add(df.format(c.time))
            // -1 represents go to down weeks
            // if needs further week add 1
            c.add(Calendar.DATE, week)
        }
        return weeks
    }

    fun getYesterdayDate(): String
    {
        val c = Calendar.getInstance()
        c.add(Calendar.DATE, -1)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        return dateFormat.format(c.time)
    }

    fun getTomorrowDate(): String
    {
        val c = Calendar.getInstance()
        c.add(Calendar.DATE, 1)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        return dateFormat.format(c.time)
    }

    fun differenceDateDay(date1: String?, date2: String?): Int {
        if(date1 == null || date2 == null) return 0
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val d1 = dateFormat.parse(date1) ?: throw NullPointerException(" String format do not match")
        val d2 = dateFormat.parse(date2) ?: throw  NullPointerException(" String format do not match")
        val diff = (d1.time) - (d2.time )
        val numOfDays = (diff / (1000 * 60 * 60 * 24)).toInt()
        return numOfDays
    }
}