package eac.qloga.android.features.p4p.showroom.scenes.serviceInfo

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.components.TitleBarDelete
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.grayTextColor
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.ExpandableConditionsListItem
import eac.qloga.android.features.p4p.showroom.shared.components.SelectedListItem
import eac.qloga.android.features.p4p.showroom.shared.components.StatusButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceInfoScreen(
    navController: NavController,
    parentRoute: String? = null,
    id: Int? = null,
    viewModel: ServiceInfoViewModel = hiltViewModel(),
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val scrollState = rememberScrollState()
    val headerText  = "Deep upholstery and carpet cleaning"
    val imageWidth = 120.dp
    val showCountingBtn = remember { mutableStateOf(false) }
    val showDeleteBtn = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        when(parentRoute){
            P4pShowroomScreens.Categories.route
            //Screen.ServicesList.route,
            //Screen.OpenRequest.route,
            //Screen.ProviderDetails.route,
            //Screen.CustomerOrder.route,
            //Screen.CustomerDetails.route,
            //Screen.Services.route,
            //Screen.ProviderOrder.route
                -> {
                showCountingBtn.value = false
                showDeleteBtn.value = false
            }
        }
        id?.let { viewModel.setSelectedServiceId(it) }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pShowroomScreens.ServiceInfo.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions = {
                    if(showDeleteBtn.value){
                        TitleBarDelete {
                            scope.launch {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                                viewModel.selectedServiceId.value?.let {
                                    viewModel.removeCleaningCategory(it)
                                }
                                navController.navigateUp()
                            }
                        }
                    }
                }
            ) { navController.navigateUp() }
        }
    ) { paddingValues ->

        val topPadding = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topPadding))
            Spacer(modifier = Modifier.height(containerTopPadding))

            //title text bar
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = headerText,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Unit:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            text = "window(sq.feet)",
                            style = MaterialTheme.typography.titleMedium,
                            color = gray30,
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    if(showCountingBtn.value){
                        viewModel.selectedServiceId.value?.let {
                            /*
                            CountingButton(
                                onSub = { viewModel.onTriggerEvent(ServiceEvent.SubSelectedServiceCount) },
                                onAdd = { viewModel.onTriggerEvent(ServiceEvent.AddSelectedServiceCount)},
                                count = viewModel.selectedCleaningCategories.value[it].value.count
                            )
                             */
                        }
                    }else{
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Price:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            modifier = Modifier
                                .alpha(.75f)
                                .padding(start = 8.dp),
                            text = "100.00 $",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp
                            ),
                            color = grayTextColor,
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Time norm:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            modifier = Modifier
                                .alpha(.75f)
                                .padding(start = 8.dp),
                            text = "60 min",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp
                            ),
                            color = grayTextColor,
                        )
                    }
                }

                //image
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(imageWidth)
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    ,
                    painter = painterResource(id = R.drawable.washing_lady),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            SelectedListItem(
                title = "Description:",
                label = "Internal and external drains and sewers " +
                        "repairs including blockage removals, pipe " +
                        "replacements, etc. \n\nInternal and external " +
                        "drains and sewers repairs including blockage " +
                        "removals, pipe replacements, etc."
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Conditions: ",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))
            ExpandableConditionsListItem(
                label = "No carpet cleaning",
                description = "Service person will not use anything like a ladder " +
                        "to reach surfaces that cannot be reached when standing on the floor. \n" +
                        "Service person will not use anything like a ladder to reach " +
                        "surfaces that cannot be reached when standing on the floor." +
                        " Service person will not use anything like not use anything"
            )
            Spacer(Modifier.height(8.dp))
            ExpandableConditionsListItem(
                label = "No climbing",
                description = "Service person will not use anything like a ladder " +
                        "to reach surfaces that cannot be reached when standing on the floor. \n" +
                        "Service person will not use anything like a ladder to reach " +
                        "surfaces that cannot be reached when standing on the floor." +
                        " Service person will not use anything like not use anything"
            )

            Spacer(Modifier.height(16.dp))
            StatusButton(
                label = "Full service contract",
                count = "",
                trailingIcon = Icons.Rounded.ArrowForwardIos,
                clickable = true,
                onClick = {
                   navController.navigate(P4pShowroomScreens.ServiceContract.route){
                        launchSingleTop = true
                   }
                }
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedService() {
    //SelectedServiceScreen()
}

