package eac.qloga.android.features.p4p.shared.scenes.displayVisits

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.components.DoneButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.ServiceEvent
import eac.qloga.android.features.p4p.shared.viewmodels.ServiceViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.VisitDayListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayVisitsScreen(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel()
) {
    val lastVisitDate = "13/10/2022 12:00-13:00"
    val firstVisitDate = "07/10/2022 12:00-13:00"
    val checked = viewModel.checked.value
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val visitedData = remember(checked) {
        if (checked) viewModel.visitedTwoWeekData else viewModel.visitedOneWeekData
    }
    val interactionSource = remember{ MutableInteractionSource() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.DisplayVisits.titleName,
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
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .animateContentSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(
                                modifier = Modifier
                                    .rotate(-90f)
                                    .clip(CircleShape)
                                    .clickable { /** TODO **/ }
                                    .padding(8.dp)
                            ){
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Rounded.ArrowForwardIos,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .rotate(90f)
                                    .clip(CircleShape)
                                    .clickable { viewModel.getNextWeekVisitedData() }
                                    .padding(8.dp)
                            ){
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Rounded.ArrowForwardIos,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                modifier = Modifier
                                    .padding(end = 8.dp),
                                text = "Show Two weeks",
                                style = MaterialTheme.typography.bodySmall,
                                color = gray30
                            )
                            Switch(
                                checked = checked,
                                onCheckedChange = { viewModel.onTriggerEvent(ServiceEvent.ToggleSwitch) },
                                colors = SwitchDefaults.colors(
                                    uncheckedBorderColor = gray1,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }

                    //Day List
                    visitedData.value.forEach {  visitInfo ->
                        VisitDayListItem(
                            title = visitInfo.date,
                            visitInfo = visitInfo,
                            // while split first element is space in array
                            daySign = visitInfo.weekOfDay.split("")[1],
                        ) {
                            viewModel.setSelectedDate(visitInfo)
                            navController.navigate(P4pScreens.EditVisits.route)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ContainerBorderedCard {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 14.dp, top = 14.dp, bottom = 14.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = "First visit: ",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            modifier = Modifier.padding(end = 8.dp),
                            text = firstVisitDate,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W500
                            ),
                            color = gray30
                        )
                    }
                    DividerLine(Modifier.padding(start = 28.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 14.dp, top = 14.dp, bottom = 14.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = "Last visit: ",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            modifier = Modifier.padding(end = 8.dp),
                            text = lastVisitDate,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W500
                            ),
                            color = gray30
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "You can schedule 3 visits per day, within two weeks",
                    style = MaterialTheme.typography.titleSmall,
                    color = info_sky
                )
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        viewModel.clearAllVisits()
                    },
                    text = "Clear All",
                    style = MaterialTheme.typography.titleMedium,
                    color = orange1
                )
            }
        }
    }
}
