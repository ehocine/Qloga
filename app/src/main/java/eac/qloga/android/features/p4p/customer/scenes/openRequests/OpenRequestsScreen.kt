package eac.qloga.android.features.p4p.customer.scenes.openRequests

import P4pCustomerScreens
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.google.accompanist.flowlayout.FlowRow
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Buttons.UserButton
import eac.qloga.android.core.shared.components.Containers.BottomButtonContainer
import eac.qloga.android.core.shared.components.ImagePlaceholder
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.SCREEN_HORIZONTAL_PADDING
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.customer.shared.viewModels.CustomerDashboardViewModel
import eac.qloga.android.features.p4p.shared.components.ServicesItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.account.AccountViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.ServiceEvent
import eac.qloga.android.features.p4p.shared.viewmodels.ServiceViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.TopNavBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenRequestsScreen(
    navController: NavController,
    viewModel: CustomerDashboardViewModel = hiltViewModel(),
    serviceViewModel: ServiceViewModel = hiltViewModel(),
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val horizontalContentPadding = SCREEN_HORIZONTAL_PADDING.dp
    val showEmptyState = viewModel.showEmptyStateOpenRequest

    val topNavLazyListState = rememberLazyListState()
    //TODO: replace ServiceCategory by the back-end DTO
    /*
    val selectedServices = remember {
        mutableStateOf(
            mapOf(ServiceCategory.Care to 3, ServiceCategory.Cargo to 4, ServiceCategory.Hair to 1)
        )
    }
     */
    val windowCleanCount = serviceViewModel.windowCleanCount.value
    val kitchenCleanCount = serviceViewModel.kitchenCleanCount.value
    val bedRoomCleanCount = serviceViewModel.bedRoomCleanCount.value
    val completeHomeCleanCount = serviceViewModel.completeHomeCleanCount.value
    val completeCleanCount = serviceViewModel.completeCleanCount.value
    val totalCleanServices = remember{ mutableStateOf(0) }

    LaunchedEffect(
        kitchenCleanCount,
        windowCleanCount,
        bedRoomCleanCount,
        completeCleanCount,
        completeHomeCleanCount
    ){
        totalCleanServices.value = windowCleanCount.count + kitchenCleanCount.count + bedRoomCleanCount.count +
                completeCleanCount.count + completeHomeCleanCount.count
       // selectedServices.value = selectedServices.value.plus(ServiceCategory.Cleaning to totalCleanServices.value)
    }

    BackHandler {
        navController.popBackStack(route = P4pShowroomScreens.Enrolled.route, inclusive = false)
    }

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pCustomerScreens.OpenRequests.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                showBackPressButton = false,
                actions = {
                    UserButton(
                        onClick = {
                            scope.launch {
                                AccountViewModel.selectedAccountType = AccountType.CUSTOMER
                                navController.navigate(P4pScreens.Account.route)
                            }
                        },
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) { paddingValues ->
        val titleBarHeight = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(titleBarHeight))
            Spacer(modifier = Modifier.height(containerTopPadding))

            if(showEmptyState){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = 64.dp)
                    ) {
                        //TODO SVG
                        ImagePlaceholder(
                            modifier = Modifier.fillMaxWidth(),
                            imageId = R.drawable.ql_open_rq
                        )
                    }
                    BottomButtonContainer(
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        FullRoundedButton(buttonText = "Create Open Request") {
                            viewModel.onShowEmptyStateOpenRequest(false)
                        }
                    }
                }
            }
            if(!showEmptyState){
                val categoriesList = ApiViewModel.categories.value.sortedBy {
                    it.sortOrder
                }.sortedBy {
                    it.catGroupOrder
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    TopNavBar(
                        onClickItem = {  viewModel.onSelectServiceCategory(it) },
                        selectedNav = viewModel.selectedServiceCategory,
                        scrollable = true,
                        lazyListState = topNavLazyListState,
                        navList = categoriesList
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalContentPadding)
                    ){
                        Text(
                            text = "Selected",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        FlowRow(modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                        ) {
                            /*
                            selectedServices.value.forEach{ (service, count )->
                                if(count > 0){
                                    SelectedServiceChip(
                                        label = service.label,
                                        count = count,
                                        onClear = {
                                            scope.launch {
                                                selectedServices.value = selectedServices.value.minus(service)
                                                serviceViewModel.resetCleaningServices()
                                            }
                                        }
                                    )
                                }
                            }
                             */
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(scrollState)
                                .padding(horizontal = horizontalContentPadding)
                        ) {
                            ServicesItem(
                                additionalInfo = "Time norm: 60 min/room (recomended)",
                                description = "Professional will come to your house and try " +
                                        "theirhardest to fix your boiler as soon as possible.",
                                title = "Windows Cleaning",
                                onSub = { serviceViewModel.onTriggerEvent(ServiceEvent.SubWindowClean) },
                                onAdd = { serviceViewModel.onTriggerEvent(ServiceEvent.AddWindowClean) },
                                onClickInfo = {
                                    scope.launch {
                                        //navController.navigate(
                                        //    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.OpenRequest.route}"
                                        //)
                                    }
                                },
                                count = windowCleanCount.count,
                                showPrice = false
                            )

                            ServicesItem(
                                additionalInfo = "Time norm: 60 min/room (recomended)",
                                description = "Professional will come to your house and try " +
                                        "theirhardest to fix your boiler as soon as possible.",
                                title = "Kitchen Cleaning",
                                onSub = { serviceViewModel.onTriggerEvent(ServiceEvent.SubKitchenClean) },
                                onAdd = { serviceViewModel.onTriggerEvent(ServiceEvent.AddKitchenClean) },
                                count = kitchenCleanCount.count,
                                onClickInfo = {
                                    scope.launch {
                                        //navController.navigate(
                                        //    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.OpenRequest.route}"
                                        //)
                                    }
                                },
                                showPrice = false
                            )

                            ServicesItem(
                                additionalInfo = "Time norm: 60 min/room (recomended)",
                                description = "Professional will come to your house and try " +
                                        "theirhardest to fix your boiler as soon as possible.",
                                title = "Bedroom or living room  Cleaning",
                                onSub = { serviceViewModel.onTriggerEvent(ServiceEvent.SubBedroomClean) },
                                onAdd = { serviceViewModel.onTriggerEvent(ServiceEvent.AddBedroomClean) },
                                onClickInfo = {
                                    scope.launch {
                                        //navController.navigate(
                                        //    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.OpenRequest.route}"
                                        //)
                                    }
                                },
                                count = bedRoomCleanCount.count,
                                showPrice = false
                            )

                            ServicesItem(
                                additionalInfo = "Time norm: 60 min/room (recomended)",
                                description = "Professional will come to your house and try " +
                                        "theirhardest to fix your boiler as soon as possible.",
                                title = "Complete home Cleaning",
                                onSub = { serviceViewModel.onTriggerEvent(ServiceEvent.SubCompleteHomeClean) },
                                onAdd = { serviceViewModel.onTriggerEvent(ServiceEvent.AddCompleteHomeClean) },
                                onClickInfo = {
                                    scope.launch {
                                        //navController.navigate(
                                        //    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.OpenRequest.route}"
                                        //)
                                    }
                                },
                                count = completeHomeCleanCount.count,
                                showPrice = false
                            )

                            ServicesItem(
                                additionalInfo = "Time norm: 60 min/room (recomended)",
                                description = "Professional will come to your house and try " +
                                        "theirhardest to fix your boiler as soon as possible.",
                                title = "Complete Cleaning",
                                showPrice = false,
                                showBottomDivider = false,
                                onSub = { serviceViewModel.onTriggerEvent(ServiceEvent.SubCompleteClean) },
                                onAdd = {  serviceViewModel.onTriggerEvent(ServiceEvent.AddCompleteClean) },
                                onClickInfo = {
                                    scope.launch {
                                        //navController.navigate(
                                        //    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.OpenRequest.route}"
                                        //)
                                    }
                                },
                                count = completeCleanCount.count,
                            )
                            Spacer(Modifier.height(52.dp)) //needed space for bottom button
                        }

                        BottomButtonContainer(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                        ) {
                            FullRoundedButton(
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                buttonText = "Add service",
                                onClick = {
                                    scope.launch {
                                        //navController.navigate(Screen.Request.route){
                                        //    launchSingleTop = true
                                        //}
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}