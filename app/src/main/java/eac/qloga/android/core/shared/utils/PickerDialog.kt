package eac.qloga.android.core.shared.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import eac.qloga.android.R
import java.time.LocalDate
import java.util.*

object PickerDialog {
    fun showTimePickerDialog(
        onSetTime: (time: String) -> Unit,
        context: Context,
        initialHour: Int? = null,
        initialMin: Int? = null,
        is24HourView: Boolean = false
    ) {
        /**
         *  It show the time picker dialog and @returns the string value of
         *  hour and minute . The initialHour and initialMin are time to
         *  show to time in dialog box as a default . If already time is picked up
         *  then show picked time as default otherwise generate current hour and
         *   minute
         * **/

        val calendar = Calendar.getInstance()
        val hourTime = calendar[Calendar.HOUR_OF_DAY]
        val minuteTime = calendar[Calendar.MINUTE]

        TimePickerDialog(
            context,
            R.style.DateTimePickerDialogTheme,
            { _, hour: Int, minute: Int ->
                onSetTime("${if (hour < 10) "0$hour" else hour}:${if (minute < 10) "0$minute" else minute}")
            },
            initialHour ?: hourTime, initialMin ?: minuteTime, is24HourView,
        ).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDatePickerDialog(
        context: Context,
        onSetDate: (date: String) -> Unit,
        numberFormat: Boolean = false,
    ) {
        val newCalendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            R.style.DateTimePickerDialogTheme,
            { _, year: Int, month: Int, day: Int ->
                val m = month + 1
                // date format is 23 Feb 2022
                val date: String = if(numberFormat){
                    "$day/$m/$year"
                }else {
                    DateConverter.dayMonthYear(
                        year = year.toString(),
                        month = m.toString(),
                        day = day.toString()
                    )
                }
                onSetDate(date)
            },
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}