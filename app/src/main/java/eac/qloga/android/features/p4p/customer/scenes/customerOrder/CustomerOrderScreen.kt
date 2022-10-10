package eac.qloga.android.features.p4p.customer.scenes.customerOrder

import P4pCustomerScreens
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.Items
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.features.p4p.shared.components.OrderActions
import eac.qloga.android.features.p4p.shared.components.OrderSummery
import eac.qloga.android.features.p4p.shared.components.OrderTypeStatus
import eac.qloga.android.features.p4p.shared.components.OrderedServicesCard
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.OrderCategory
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerOrderScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel(),
    orderType: String? = null
) {
    val containerTopPadding = 16.dp
    val containerHorizontalPadding = 24.dp
    val iconSize = 24.dp
    val orderCategory = viewModel.orderCategory.value
    val confirmedArrival = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    with(LocalDensity.current){
        val screenWidthDp = LocalConfiguration.current.screenWidthDp
        val screenWidthPx = screenWidthDp.dp.toPx().toInt()
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        when(orderType){
            OrderCategory.FundsReservation.label -> {viewModel.orderCategory(OrderCategory.FundsReservation)}
            OrderCategory.ProviderIsNear.label -> {viewModel.orderCategory(OrderCategory.ProviderIsNear)}
            OrderCategory.Accept.label -> {viewModel.orderCategory(OrderCategory.Accept)}
            OrderCategory.Arrived.label -> {viewModel.orderCategory(OrderCategory.Arrived)}
            OrderCategory.PaymentProcessing.label -> {viewModel.orderCategory(OrderCategory.PaymentProcessing)}
            OrderCategory.Completed.label -> {viewModel.orderCategory(OrderCategory.Completed)}
            else -> { viewModel.orderCategory(OrderCategory.FundsReservation)}
        }
    }

    BackHandler {
        when(orderCategory){
            OrderCategory.Completed, OrderCategory.PaymentProcessing -> {
                navController.navigate(P4pCustomerScreens.CustomerOrders.route)
            }
            else -> {
                navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pCustomerScreens.CustomerOrder.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                when(orderCategory){
                    OrderCategory.Completed, OrderCategory.PaymentProcessing -> {
                        navController.navigate(P4pCustomerScreens.CustomerOrders.route)
                    }
                    else -> {
                        navController.navigateUp()
                    }
                }
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = containerHorizontalPadding)
            ) {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))
                OrderTypeStatus(
                    orderCategory = orderCategory,
                    onClickItem = {
                        when(orderCategory){
                            OrderCategory.Completed, OrderCategory.FundsReservation -> {
                                navController.navigate(
//                                    P4pScreens.Tracking.route+"?$PARENT_ROUTE_KEY=${Screen.CustomerOrder.route}"
                                    P4pScreens.Tracking.route
                                )
                            }
                            OrderCategory.Accept -> {
                                navController.navigate(
//                                    P4pCustomerScreens.CustomerOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.ProviderIsNear.label}"
                                    P4pCustomerScreens.CustomerOrder.route
                                )
                            }
                            OrderCategory.PaymentProcessing -> {
//                                navController.navigate(
//                                    Screen.PaidOrder.route+"?$ACCOUNT_TYPE_KEY=${AccountType.CUSTOMER.label}"
//                                )
                            }
                            else -> {}
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OrderSummery(
                    date = "12/08/2022",
                    time = "09:00",
                    address = "30 Cloth Market, Merchant House, Newcastle upon Tyne, GB",
                    price = 84.00,
                    onClickLocationMarker = {
//                        navController.navigate(Screen.ShowLocationMapView.route)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                OrderedServicesCard(
                    onClickService = {
//                        navController.navigate(
//                            Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.CustomerOrder.route}"
//                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                OrderActions(
                    orderCategory = orderCategory,
                    accountType = AccountType.CUSTOMER,
                    confirmedArrival = confirmedArrival.value,
                    onProviderNotArrived = {
//                        navController.navigate(
//                            Screen.CustomerOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.ProviderIsNear.label}"
//                        )
                    },
                    onClickReserveFunds = {
//                        navController.navigate(
//                            Screen.CustomerOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.Accept.label}"
//                        )
                    },
                    onArrivedCancel = {
//                        navController.navigate(
//                            Screen.CustomerOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.Completed.label}"
//                        )
                    },
                    onApprove = {
//                        navController.navigate(
//                            Screen.CustomerOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.PaymentProcessing.label}"
//                        )
                    },
                    onUnsatisfied = {
//                        navController.navigate(
//                            Screen.CustomerOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.PaymentProcessing.label}"
//                        )
                    },
                    onConfirmArrival = {
//                        navController.navigate(
//                            Screen.CustomerOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.Arrived.label}"
//                        )
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))
//                OtherServicesCard(
//                    navController = navController,
//                    accountType = AccountType.CUSTOMER,
//                    name = "Leo Potter"
//                )

                Cards.ContainerBorderedCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Items.RequestItem(
                            leadingIcon = {
                                Icon(
                                    modifier = Modifier.size(iconSize),
                                    painter = painterResource(id = R.drawable.ic_info),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )
                            },
                            label = "Provider",
                            value = "Leo Potter",
                            isItemClickable = true,
                            showForwardArrow = true,
                            onClickItem = {
                                scope.launch {
//                                    navController.navigate(Screen.OrderProviderProfile.route){
//                                        launchSingleTop = true
//                                    }
                                }
                            },
                        )
                        Items.RequestItem(
                            leadingIcon = {
                                Icon(
                                    modifier = Modifier.size(iconSize),
                                    painter = painterResource(id = R.drawable.ic_ql_flag),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )
                            },
                            label = "Visits",
                            value = "5",
                            isItemClickable = true,
                            showForwardArrow = true,
                            onClickItem = {
//                                navController.navigate(Screen.OrderVisits.route){
//                                    launchSingleTop = true
//                                }
                            },
                        )
                        Items.RequestItem(
                            leadingIcon = {
                                Icon(
                                    modifier = Modifier.size(iconSize),
                                    painter = painterResource(id = R.drawable.ic_ql_photo),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )
                            },
                            label = "Photos",
                            value = "8",
                            isItemClickable = true,
                            showForwardArrow = true,
                            onClickItem = {
//                                navController.navigate(
//                                    Screen.Albums.route+"?$PARENT_ROUTE_KEY=${Screen.CustomerOrder.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            },
                        )
                        Items.RequestItem(
                            leadingIcon = {
                                Icon(
                                    modifier = Modifier.size(iconSize),
                                    painter = painterResource(id = R.drawable.ic_ql_note),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )
                            },
                            label = "Notes",
                            isItemClickable = true,
                            showForwardArrow = true,
                            onClickItem = {
//                                navController.navigate(
//                                    Screen.OrderNotes.route+"?$ACCOUNT_TYPE_KEY=${accountType.label}"
//                                ) {
//                                    launchSingleTop = true
//                                }
                            }
                        )
                        Items.RequestItem(
                            leadingIcon = {
                                Icon(
                                    modifier = Modifier.size(iconSize),
                                    painter = painterResource(id = R.drawable.ic_ql_pound),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )
                            },
                            label = "Payments",
                            isItemClickable = true,
                            showForwardArrow = true,
                            onClickItem = {
//                                navController.navigate(Screen.OrderPayment.route){
//                                    launchSingleTop = true
//                                }
                            },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}