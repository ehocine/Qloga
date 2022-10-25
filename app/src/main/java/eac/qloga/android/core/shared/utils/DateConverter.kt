package eac.qloga.android.core.shared.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
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

private const val TAG = "${QTAG}-DateConverter"

object DateConverter {
    private val simpleDateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
    private val calendar: Calendar = Calendar.getInstance()

    fun stringToLocalDate(stringDate: String?): LocalDate? {
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("d, MMM yyyy")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        var result: LocalDate? = null
        try {
            result = LocalDate.parse(stringDate, formatter)
        }catch (e: Exception){
            Log.e(TAG, "stringToLocalDate: ${e.printStackTrace()}")
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun localDateToString(localDate: LocalDate?): String? {
        var result = ""
        if(localDate == null) return null
        try {
            val formatter1 = DateTimeFormatter.ofPattern("yyyy-m-d")
            val localDate2 = LocalDate.parse(localDate.toString(), formatter1)
            val formatter2 = DateTimeFormatter.ofPattern("d/m/yyyy")
            result = localDate2.format(formatter2)
        }catch (e: Exception){
            Log.e(TAG, "localDateToString: ${e.printStackTrace()}")
        }
        return result
    }

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
        return simpleDateFormat.parse(stringVal)?: throw NullPointerException(
            "Given String value can't be converted to date "
        )
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
            return "$day, ${textMonth.substring(0,3)} $year"
        }catch (e: Exception){
            Log.e(TAG, "toTextMonth: ${e.printStackTrace()}")
        }
        return ""
    }

    fun dayMonthYear(date: String): String
    {
        // date format d/m/yyyy
        //return d, m yyyy eg 23, Oct 2022
        try {
            val splited = date.split("/")
            val month = splited[1]
            val day = splited[0]
            val y = splited[2]

            val textMonth = getTextMonth(month.toInt())
            return "$day ${textMonth.substring(0,3)}, $y"
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

    // accepts format 2022-02-13
    // return format 13, Feb 2022
    fun toTextDMY(date: String): String{
        val sdf1 = SimpleDateFormat("yyyy-m-d", Locale.ENGLISH)
        val sdf2 = SimpleDateFormat("dd, MMM yyyy", Locale.ENGLISH)

        val d1 = sdf1.parse(date)
        val d2 = d1?.let { sdf2.format(it) }

        return d2 ?: ""
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun zonedDateTimeToStringDate(zonedDateTime: ZonedDateTime): String {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(zonedDateTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringDateTimeToZonedDateTime(date: String, time: String): ZonedDateTime {
        val result: ZonedDateTime

        try {
            val f2 = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")
            val cDate = LocalDateTime.parse("$date $time", f2)

            val zonedDateTime = cDate.atZone(ZoneOffset.UTC)
//            val localDateTime = LocalDateTime.parse("$date $time", f2)
//                .atZone(ZoneId.systemDefault())
//                .toInstant()

//            val zonedDateTime: ZonedDateTime = LocalDateTime.parse("$date $time",f2)
//                .atZone(ZoneId.systemDefault())


//            val zoned: ZonedDateTime = ZonedDateTime.of("$data", ZoneId.systemDefault())
            result  = zonedDateTime
        }catch (e: Exception){
            e.printStackTrace()
            throw Exception("Error converting date time ")
        }
        return result
    }

    fun getDateFormat1(data: String): String{
        // parameter format 'yyyy-MM-dd'T'hh:0mm:ssZ'
        // return format yyyy/MM/dd
        var result: String = ""
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        try {
            val inputDate = inputFormat.parse(data)?.toString() ?: ""
            result = outputFormat.format(inputDate)
        }catch (e: Exception) {
            Log.e(TAG, "getDateFormat1: ${e.cause}")
            throw Exception("Error converting date ")
        }
        return result
    }
}