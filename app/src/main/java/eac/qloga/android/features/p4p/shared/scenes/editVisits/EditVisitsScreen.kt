package eac.qloga.android.features.p4p.shared.scenes.editVisits

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.DoneButton
import eac.qloga.android.core.shared.components.TimeChip
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.components.TwoSwitchTabRow
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.ServiceEvent
import eac.qloga.android.features.p4p.shared.viewmodels.ServiceViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.CircleDot
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditVisitsScreen(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel()
) {
    val spanCount = 3
    val gridHeight = 470.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val selectedDate = viewModel.selectedVisitedDate.value
    val listOfTimeSlots = viewModel.listOfTimeSlots.value

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val lazyGridScrollState = rememberLazyGridState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.EditVisits.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions = {
                    DoneButton(
                        onClick = {
                            scope.launch {
                                navController.navigateUp()
                            }
                        }
                    )
                }
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = containerHorizontalPadding)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //space for title bar
            Spacer(modifier = Modifier.height(topPadding))
            Spacer(modifier = Modifier.height(containerTopPadding))

            ContainerBorderedCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (selectedDate != null) {
                            Text(
                                text = selectedDate.date + " " + selectedDate.weekOfDay.uppercase(),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W500
                                ),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TwoSwitchTabRow(
                modifier = Modifier.fillMaxWidth(.5f),
                height = 28.dp,
                selectedTapIndex = viewModel.selectedTimeTabRowIndex.value,
                selectedColor = MaterialTheme.colorScheme.primary,
                tabsContent = viewModel.timeTabContains.value,
                onSelect = { viewModel.onTriggerEvent(ServiceEvent.ChangeTimeTabRow(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            ContainerBorderedCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(gridHeight)
                            ,
                            userScrollEnabled = false,
                            state = lazyGridScrollState,
                            columns = GridCells.Fixed(spanCount),
                            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
                        ){
                            items(listOfTimeSlots, key = {it}){  timeSlot ->
                                val selected = viewModel.selectedTimeSlotList.value.contains(timeSlot)
                                TimeChip(
                                    label = timeSlot,
                                    isSelected = selected,
                                    isAvailable = timeSlot in viewModel.timeSlotAvailableList.value,
                                    onClick = { viewModel.toggleSelectedTimeSlot(timeSlot) }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f),
                    text = "You can schedule 3 visits per day, within two weeks",
                    style = MaterialTheme.typography.titleSmall,
                    color = info_sky,
                    textAlign = TextAlign.Start
                )

                Spacer(Modifier.width(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    CircleDot(size = 8.dp, background = gray1)
                    Text(
                        modifier = Modifier.padding(end = 8.dp, start = 4.dp),
                        text = "-off time",
                        style = MaterialTheme.typography.titleSmall,
                        color = gray30
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}