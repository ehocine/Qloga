package eac.qloga.android.features.p4p.provider.shared.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.PickerDialog
import eac.qloga.android.core.shared.utils.TimeConverter
import eac.qloga.android.core.shared.utils.WeekDays
import eac.qloga.bare.dto.WorkHours
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkingHoursCard(
    modifier: Modifier = Modifier,
    workingHoursState: Map<String,List<WorkHours>>,
    onRemove: (day: WeekDays, index: Int) -> Unit,
    onAdd: (weekDay: WeekDays, workHours: WorkHours) -> Unit,
    onAddSameWeek: (weekDay: WeekDays) -> Unit,
    onUpdateWorkHours: ( WeekDays, WorkHours,index: Int) -> Unit,
) {
    val context = LocalContext.current
    val chipWidth = 68.dp // width of time picker chip
    val scope = rememberCoroutineScope()

    Cards.ContainerBorderedCard {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.fillMaxWidth(.4f))
                Text(
                    modifier = Modifier.width(chipWidth),
                    text = "from",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "to",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            workingHoursState.forEach { (weekDay, workingHours) ->
                if(workingHours.isEmpty()){
                    TimePickerItem(
                        showBottomBorder = weekDay != WeekDays.SUNDAY.name,
                        dayOfWeek = weekDay,
                        isActive = false,
                        onAdd = {
                            val lt = LocalTime.now()
                            val workHours = WorkHours.builder()
                                .weekDay(DayOfWeek.valueOf(weekDay))
                                .from(lt)
                                .to(lt)
                                .build()

                            onAdd(WeekDays.valueOf(weekDay), workHours)
                        },
                        onRemove = {},
                        onSelectFrom = {},
                        onSelectTo = {}
                    )
                }else{
                    workingHours.forEachIndexed { index, workHours ->

                        val formatter = DateTimeFormatter.ofPattern("HH:mm")

                        TimePickerItem(
                            dayOfWeek = if(index == 0) weekDay else "",
                            fromTime = workHours.from.format(formatter),
                            toTime = workHours.to.format(formatter),
                            showBottomBorder = weekDay != WeekDays.SUNDAY.name,
                            onRemove = { onRemove(WeekDays.valueOf(weekDay),index) },
                            onAdd = { onAddSameWeek(WeekDays.valueOf(weekDay)) },
                            onSelectFrom = {
                                scope.launch {
                                    PickerDialog.showTimePickerDialog(
                                        onSetTime = {
                                            val workHour = WorkHours.builder()
                                                .from(TimeConverter.stringToLocalTime(it))
                                                .to(workHours.to)
                                                .weekDay(workHours.weekDay)
                                                .build()
                                            onUpdateWorkHours(WeekDays.valueOf(weekDay), workHour, index)
                                        },
                                        context = context,
                                        is24HourView = true
                                    )
                                }
                            },
                            onSelectTo = {
                                scope.launch {
                                    PickerDialog.showTimePickerDialog(
                                        onSetTime = {
                                            val workHour = WorkHours.builder()
                                                .to(TimeConverter.stringToLocalTime(it))
                                                .from(workHours.from)
                                                .weekDay(workHours.weekDay)
                                                .build()
                                            onUpdateWorkHours(WeekDays.valueOf(weekDay), workHour, index)
                                        },
                                        context = context,
                                        is24HourView = true
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewWorkingHoursCard() {
    //WorkingHoursCard()
}