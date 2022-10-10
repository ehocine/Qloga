package eac.qloga.android.features.p4p.shared.components

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

@Composable
fun WorkingHoursCard(
    modifier: Modifier = Modifier,
//    borderColor : Color = gray1,
//    borderWidth: Dp = 1.4.dp,
//    borderShape: Shape = RoundedCornerShape(16.dp),
//    workingHoursState: WorkingHoursState,
//    onRemove: (workingHoursType: WorkingHoursType, index: Int) -> Unit,
//    onAdd: (workingHoursType: WorkingHoursType) -> Unit,
//    onSelectTime: (workingHoursType: WorkingHoursType,workingHourTime: WorkingHourTime, index: Int ) -> Unit
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
                .padding(start = 40.dp)) {
                Spacer(modifier = Modifier.width(chipWidth))
                Text(
                    modifier = Modifier.width(chipWidth),
                    text = "from",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "to",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
//            if(workingHoursState.SUN.isEmpty()){
//                // if the list of working hour is empty show inactive data
//                TimePickerItem(dayOfWeek = "SUN", isActive = false, onAdd = { onAdd(WorkingHoursType.SUN) })
//            }else{
//                workingHoursState.SUN.forEachIndexed{ index, workingHourTime ->
//                    TimePickerItem(
//                        dayOfWeek = if(index == 0) "SUN" else "",
//                        fromTime = workingHourTime.from,
//                        toTime = workingHourTime.to,
//                        showBottomBorder = index == workingHoursState.SUN.size - 1,
//                        onRemove = { onRemove(WorkingHoursType.SUN,index) },
//                        onAdd = { onAdd(WorkingHoursType.SUN) },
//                        onSelectFrom = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.SUN, WorkingHourTime(from = it, to =""), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        },
//                        onSelectTo = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.SUN, WorkingHourTime(from = "", to = it), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        }
//                    )
//                }
//            }
//
//            if(workingHoursState.MON.isEmpty()){
//                TimePickerItem(dayOfWeek = "MON", isActive = false, onAdd = { onAdd(WorkingHoursType.MON) })
//            }else{
//                workingHoursState.MON.forEachIndexed{ index, workingHourTime ->
//                    TimePickerItem(
//                        dayOfWeek = if(index == 0) "MON" else "",
//                        fromTime = workingHourTime.from,
//                        toTime = workingHourTime.to,
//                        showBottomBorder = index == workingHoursState.MON.size - 1,
//                        onRemove = { onRemove(WorkingHoursType.MON,index) },
//                        onAdd = { onAdd(WorkingHoursType.MON) },
//                        onSelectFrom = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.MON, WorkingHourTime(from = it, to =""), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        },
//                        onSelectTo = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.MON, WorkingHourTime(from = "", to = it), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        }
//                    )
//                }
//            }
//
//            if(workingHoursState.TUE.isEmpty()){
//                TimePickerItem(dayOfWeek = "TUE", isActive = false, onAdd = { onAdd(WorkingHoursType.TUE) })
//            }else{
//                workingHoursState.TUE.forEachIndexed{ index, workingHourTime ->
//                    TimePickerItem(
//                        dayOfWeek = if(index == 0) "TUE" else "",
//                        fromTime = workingHourTime.from,
//                        toTime = workingHourTime.to,
//                        showBottomBorder = index == workingHoursState.TUE.size - 1,
//                        onRemove = { onRemove(WorkingHoursType.TUE,index) },
//                        onAdd = { onAdd(WorkingHoursType.TUE) },
//                        onSelectFrom = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.TUE, WorkingHourTime(from = it, to =""), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        },
//                        onSelectTo = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.TUE, WorkingHourTime(from = "", to = it), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        }
//                    )
//                }
//            }
//
//            if(workingHoursState.WED.isEmpty()){
//                TimePickerItem(dayOfWeek = "WED", isActive = false, onAdd = { onAdd(WorkingHoursType.WED) })
//            }else{
//                workingHoursState.WED.forEachIndexed{ index, workingHourTime ->
//                    TimePickerItem(
//                        dayOfWeek = if(index == 0) "WED" else "",
//                        fromTime = workingHourTime.from,
//                        toTime = workingHourTime.to,
//                        showBottomBorder = index == workingHoursState.WED.size - 1,
//                        onRemove = { onRemove(WorkingHoursType.WED,index) },
//                        onAdd = { onAdd(WorkingHoursType.WED) },
//                        onSelectFrom = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.WED, WorkingHourTime(from = it, to =""), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        },
//                        onSelectTo = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.WED, WorkingHourTime(from = "", to = it), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        }
//                    )
//                }
//            }
//
//            if(workingHoursState.THU.isEmpty()){
//                TimePickerItem(dayOfWeek = "THU", isActive = false, onAdd = { onAdd(WorkingHoursType.THU) })
//            }else{
//                workingHoursState.THU.forEachIndexed{ index, workingHourTime ->
//                    TimePickerItem(
//                        dayOfWeek = if(index == 0) "THU" else "",
//                        fromTime = workingHourTime.from,
//                        toTime = workingHourTime.to,
//                        showBottomBorder = index == workingHoursState.THU.size - 1,
//                        onRemove = { onRemove(WorkingHoursType.THU,index) },
//                        onAdd = { onAdd(WorkingHoursType.THU) },
//                        onSelectFrom = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.THU, WorkingHourTime(from = it, to =""), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        },
//                        onSelectTo = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.THU, WorkingHourTime(from = "", to = it), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        }
//                    )
//                }
//            }
//
//            if(workingHoursState.FRI.isEmpty()){
//                TimePickerItem(dayOfWeek = "FRI", isActive = false, onAdd = { onAdd(WorkingHoursType.FRI) })
//            }else{
//                workingHoursState.FRI.forEachIndexed{ index, workingHourTime ->
//                    TimePickerItem(
//                        dayOfWeek = if(index == 0) "FRI" else "",
//                        fromTime = workingHourTime.from,
//                        toTime = workingHourTime.to,
//                        showBottomBorder = index == workingHoursState.FRI.size - 1,
//                        onRemove = { onRemove(WorkingHoursType.FRI,index) },
//                        onAdd = { onAdd(WorkingHoursType.FRI) },
//                        onSelectFrom = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.FRI, WorkingHourTime(from = it, to =""), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        },
//                        onSelectTo = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.FRI, WorkingHourTime(from = "", to = it), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        }
//                    )
//                }
//            }
//
//            if(workingHoursState.SAT.isEmpty()){
//                TimePickerItem(dayOfWeek = "SAT", isActive = false,onAdd = { onAdd(WorkingHoursType.SAT) }, showBottomBorder = false)
//            }else{
//                workingHoursState.SAT.forEachIndexed{ index, workingHourTime ->
//                    TimePickerItem(
//                        dayOfWeek = if(index == 0) "SAT" else "",
//                        fromTime = workingHourTime.from,
//                        toTime = workingHourTime.to,
//                        onRemove = { onRemove(WorkingHoursType.SAT,index) },
//                        onAdd = { onAdd(WorkingHoursType.SAT) },
//                        showBottomBorder = false,
//                        onSelectFrom = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.SAT, WorkingHourTime(from = it, to =""), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        },
//                        onSelectTo = {
//                            scope.launch {
//                                PickerDialog.showTimePickerDialog(
//                                    onSetTime = { onSelectTime(WorkingHoursType.SAT, WorkingHourTime(from = "", to = it), index)},
//                                    context = context,
//                                    is24HourView = true
//                                )
//                            }
//                        }
//                    )
//                }
//            }
        }
    }
}

@Preview
@Composable
fun PreviewWorkingHoursCard() {
    //WorkingHoursCard()
}