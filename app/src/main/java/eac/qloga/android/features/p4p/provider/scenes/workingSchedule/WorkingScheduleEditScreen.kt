package eac.qloga.android.features.p4p.provider.scenes.workingSchedule

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.QTAG
import eac.qloga.android.core.shared.utils.UiEvent
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.shared.components.TimeOffCard
import eac.qloga.android.features.p4p.provider.shared.components.WorkingHoursCard
import eac.qloga.android.features.p4p.showroom.scenes.providerWorkingSchedule.PrvWorkingScheduleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val TAG = "$QTAG-WorkingSchedule"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkingScheduleEditScreen(
    navController: NavController,
    viewModel: PrvWorkingScheduleViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel(),
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val savingState by viewModel.savingState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.preLoadCalls()
        apiViewModel.getOrgs()
    }

    LaunchedEffect(true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.msg, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.WorkingScheduleEdit.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    Buttons.SaveButton(
                        textColor = MaterialTheme.colorScheme.primary,
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                viewModel.saveOffTime()
                                viewModel.saveWorkingHours()
                            }
                        },
                        isLoading = savingState == LoadingState.LOADING
                    )
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
                    WorkingHoursCard(
                        workingHoursState = viewModel.groupedWorkingHours,
                        onRemove = { weekDay, index ->
                            viewModel.removeWorkingHour(weekDay,index)
                        },
                        onAddSameWeek = { weekDay ->
                            val weekWorkHour = viewModel.groupedWorkingHours.getValue(weekDay.name)
                            if(weekWorkHour.isNotEmpty()){
                                val newWorkHour = weekWorkHour[weekWorkHour.size - 1]
                                newWorkHour.from = newWorkHour.to
                                viewModel.addWorkingHour(weekDay,newWorkHour)
                            }
                        },
                        onAdd = {weekDay, workHours ->
                            viewModel.addWorkingHour(weekDay, workHours)
                        },
                        onUpdateWorkHours = { weekDay, workHours, index ->
                            viewModel.updateWorkingHour(weekDay,workHours, index)
                        },
                    )
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
                                    viewModel.addOffTime()
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
                    TimeOffCard(
                        timeOffList = PrvWorkingScheduleViewModel.offTimes,
                        onPickDateTime = { offTime, index ->
                            viewModel.updateOffTime(offTime, index)
                        },
                        onRemoveOffTime = { viewModel.removeOffTime(it) }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}