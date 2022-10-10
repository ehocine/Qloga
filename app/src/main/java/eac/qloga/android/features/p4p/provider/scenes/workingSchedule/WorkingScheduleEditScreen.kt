package eac.qloga.android.features.p4p.provider.scenes.workingSchedule

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.WorkingHoursCard
import eac.qloga.android.features.p4p.shared.scenes.account.ProfilesEvent
import eac.qloga.android.features.p4p.shared.scenes.account.ProfilesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkingScheduleEditScreen(
    navController: NavController,
    viewModel: ProfilesViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
//    val workingHoursState = viewModel.workingHoursState.value

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.WorkingScheduleEdit.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = containerHorizontalPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "WORKING HOURS",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.W500
                        ),
                        color = gray30
                    )
                    Spacer(modifier = Modifier.height(4.dp))
//                    WorkingHoursCard(
//                        workingHoursState = workingHoursState,
//                        onRemove = { type, index ->  viewModel.onTriggerEvent(ProfilesEvent.RemoveWorkingHour(type, index ))},
//                        onAdd = { viewModel.onTriggerEvent(ProfilesEvent.AddWorkingHour(it))},
//                        onSelectTime = { workingHoursType, workingHourTime,index ->
//                            viewModel.onTriggerEvent(ProfilesEvent.SelectWorkingTime(workingHoursType, workingHourTime, index))
//                        }
//                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                //TimeOffCard()
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "TIME OFF",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W500
                            ),
                            color = gray30
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    viewModel.onTriggerEvent(ProfilesEvent.AddTimeOff)
                                }
                                .padding(2.dp)
                        ){
                            Text(
                                text = "ADD",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.W500
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
//                    TimeOffCard(
//                        timeOffList = viewModel.timeOffList.value,
//                        onPickDateTimeOff = { timeOffState, index ->
//                            viewModel.onTriggerEvent(ProfilesEvent.SelectTimeOff(timeOffState, index))
//                        },
//                        onRemoveTimeOff = { viewModel.onTriggerEvent(ProfilesEvent.RemoveTimeOff(it))}
//                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}