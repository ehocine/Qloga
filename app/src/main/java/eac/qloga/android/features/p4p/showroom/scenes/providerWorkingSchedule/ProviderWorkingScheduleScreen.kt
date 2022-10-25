package eac.qloga.android.features.p4p.showroom.scenes.providerWorkingSchedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.DateConverter
import eac.qloga.android.core.shared.utils.TimeConverter
import eac.qloga.android.core.shared.utils.WeekDays
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.DayWorkingHourItem
import eac.qloga.android.features.p4p.showroom.shared.components.TimeOffItem
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderWorkingScheduleScreen(
    navController: NavController,
    viewModel: PrvWorkingScheduleViewModel = hiltViewModel(),
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val containerHorizontalPadding = 24.dp
    val scrollState = rememberScrollState()
    val offTimes = PrvWorkingScheduleViewModel.offTimes

    val scope = rememberCoroutineScope()

    val filteredGroupedWorkHours by derivedStateOf {
        viewModel.groupedWorkingHours.filter { it.value.isNotEmpty() }
    }

    LaunchedEffect(key1 = Unit, key2 = PrvWorkingScheduleViewModel.workingHours.value){
        viewModel.groupWorkHours()
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pShowroomScreens.ProviderWorkingSchedule.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(horizontal = containerHorizontalPadding),
            ) {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                if(filteredGroupedWorkHours.isNotEmpty()){
                    Text(
                        modifier = Modifier.padding(start = 16.dp, bottom = 2.dp),
                        text = "WORKING HOURS",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )
                    Cards.ContainerBorderedCard {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                        ) {
                            filteredGroupedWorkHours.forEach{ (weekDay, workHours) ->
                                if(workHours.isNotEmpty()){
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(end = 16.dp)) {
                                            workHours.forEachIndexed { index, workHour ->
                                                val day = if(index == 0) weekDay.substring(0,3) else ""
                                                DayWorkingHourItem(
                                                    day = day,
                                                    from = TimeConverter.localTimeToTimeString(workHour.from),
                                                    to = TimeConverter.localTimeToTimeString(workHour.to)
                                                )
                                            }
                                        }

                                        if(weekDay != filteredGroupedWorkHours.entries.last().key){
                                            DividerLines.LightDividerLine()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                if(offTimes.isNotEmpty()){
                    Text(
                        modifier = Modifier.padding(start = 16.dp, bottom = 2.dp),
                        text = "TIME OFF",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )

                    offTimes.forEach { offTime ->
                        Cards.ContainerBorderedCard {
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                TimeOffItem(
                                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 16.dp),
                                    type = "from",
                                    date = DateConverter.zonedDateTimeToStringDate(offTime.from),
                                    time = TimeConverter.zonedDateTimeToStringTime(offTime.from)
                                )
                                DividerLines.LightDividerLine()
                                TimeOffItem(
                                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 16.dp),
                                    type = "to",
                                    date = DateConverter.zonedDateTimeToStringDate(offTime.to),
                                    time = TimeConverter.zonedDateTimeToStringTime(offTime.to)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}