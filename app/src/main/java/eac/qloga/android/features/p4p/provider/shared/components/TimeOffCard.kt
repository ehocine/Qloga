package eac.qloga.android.features.p4p.provider.shared.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DoNotDisturbOn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.orange2
import eac.qloga.android.core.shared.utils.DateConverter
import eac.qloga.android.core.shared.utils.Extensions.toDoubleDigitStr
import eac.qloga.android.core.shared.utils.PickerDialog
import eac.qloga.bare.dto.OffTime
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeOffCard(
    modifier: Modifier = Modifier,
    timeOffList: List<OffTime> = emptyList(),
    onRemoveOffTime: (Int) -> Unit = {},
    onPickDateTime: (OffTime, Int) -> Unit ,
) {
    Cards.ContainerBorderedCard {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(vertical = 12.dp)
        ) {
            if(timeOffList.isEmpty()){
                // if the time list is empty we can't remove timeOff anymore, so inactive
                TimeOffItem(
                    isActive = false,
                    offTime = OffTime(ZonedDateTime.now(), ZonedDateTime.now()),
                    showDivider = false,
                    onPickDateTime = {},
                )
            }else{
                // if timeOffList is not empty we can remove, so active
                timeOffList.forEachIndexed { index, offTime ->
                    val isLast = timeOffList.size-1 == index && timeOffList.isNotEmpty()

                    TimeOffItem(
                        isActive = true,
                        offTime = offTime,
                        showDivider = !isLast,
                        onPickDateTime = { onPickDateTime(it, index)},
                        onRemoveTimeOff = { onRemoveOffTime(index) }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TimeOffItem(
    offTime: OffTime,
    showDivider: Boolean = true,
    isActive: Boolean = true,
    onRemoveTimeOff: () -> Unit = {},
    onPickDateTime: (OffTime) -> Unit,
){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val fromDate = DateConverter.zonedDateTimeToStringDate(offTime.from)
    val toDate = DateConverter.zonedDateTimeToStringDate(offTime.to)
    val fromTime = DateTimeFormatter.ofPattern("HH:mm").format(offTime.from)
    val toTime = DateTimeFormatter.ofPattern("HH:mm").format(offTime.to)

//    val fromTime = offTime.from.hour.toDoubleDigitStr + ":" + offTime.from.minute.toDoubleDigitStr
//    val toTime = offTime.to.hour.toDoubleDigitStr + ":" + offTime.to.minute.toDoubleDigitStr

    Column {
        DateTimePickerItem(
            modifier = Modifier.padding(start = 32.dp, end = 16.dp),
            label = "from",
            date = fromDate,
            time = fromTime ,
            isActive = isActive,
            onSelectDate = {
                scope.launch {
                    PickerDialog.showDatePickerDialog(context, numberFormat = true, onSetDate = {
                        val zonedDateTimeFrom = DateConverter.stringDateTimeToZonedDateTime(it,fromTime)
                        val zonedDateTimeTo = DateConverter.stringDateTimeToZonedDateTime(toDate,toTime)
                        offTime.from = zonedDateTimeFrom
                        offTime.to = zonedDateTimeTo
                        onPickDateTime(offTime)
                    })
                }
            },
            onSelectTime = {
                scope.launch {
                    PickerDialog.showTimePickerDialog(
                        context = context,
                        onSetTime = {
                            val zonedDateTimeFrom = DateConverter.stringDateTimeToZonedDateTime(fromDate,it)
                            val zonedDateTimeTo = DateConverter.stringDateTimeToZonedDateTime(toDate,toTime)

                            offTime.from = zonedDateTimeFrom
                            offTime.to = zonedDateTimeTo
                            onPickDateTime(offTime)
                        },
                        is24HourView = true
                    )
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(isActive){
                Box(modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(CircleShape)
                    .clickable { onRemoveTimeOff() }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Outlined.DoNotDisturbOn,
                        contentDescription = null,
                        tint = orange2
                    )
                }
            }
            Divider(
                Modifier
                    .alpha(.2f)
                    .weight(1f)
                    .height(1.dp)
                    .padding(horizontal = if (isActive) 0.dp else 16.dp)
                    .background(gray1)
            )
        }

        DateTimePickerItem(
            modifier = Modifier.padding(start = 32.dp, end = 16.dp),
            label = "to",
            date = toDate,
            time = toTime,
            isActive = isActive,
            onSelectDate = {
                scope.launch {
                    PickerDialog.showDatePickerDialog(context,numberFormat = true, onSetDate = {
                        val zonedDateTimeFrom = DateConverter.stringDateTimeToZonedDateTime(fromDate,fromTime)
                        val zonedDateTimeTo = DateConverter.stringDateTimeToZonedDateTime(it,toTime)

                        offTime.from = zonedDateTimeFrom
                        offTime.to = zonedDateTimeTo
                        onPickDateTime(offTime)
                    })
                }
            },
            onSelectTime = {
                scope.launch {
                    PickerDialog.showTimePickerDialog(
                        context = context,
                        onSetTime = {
                            val zonedDateTimeFrom = DateConverter.stringDateTimeToZonedDateTime(fromDate,fromTime)
                            val zonedDateTimeTo = DateConverter.stringDateTimeToZonedDateTime(toDate,it)

                            offTime.from = zonedDateTimeFrom
                            offTime.to = zonedDateTimeTo
                            onPickDateTime(offTime)
                        },
                        is24HourView = true
                    )
                }
            },
        )
        if(showDivider){
            Spacer(Modifier.height(8.dp))
            DividerLine()
            Spacer(Modifier.height(8.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewTimeOffCard() {
    TimeOffCard(
        onPickDateTime = {_,_ -> },
    )
}