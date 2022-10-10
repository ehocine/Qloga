package eac.qloga.android.features.p4p.provider.scenes.providerOrder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.Items
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.OrderActions
import eac.qloga.android.features.p4p.shared.components.OrderSummery
import eac.qloga.android.features.p4p.shared.components.OrderTypeStatus
import eac.qloga.android.features.p4p.shared.components.OrderedServicesCard
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.OrderCategory
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderOrderScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel(),
    orderType: String? = null
) {
    val iconSize = 24.dp
    val scope = rememberCoroutineScope()
    val containerTopPadding = 16.dp
    val containerHorizontalPadding = 24.dp
    val orderCategory = viewModel.orderCategory.value
    val confirmedArrival = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        when(orderType){
            OrderCategory.ProviderIsNear.label -> {
                viewModel.orderCategory(OrderCategory.ProviderIsNear)
            }
            OrderCategory.Arrived.label -> {
                viewModel.orderCategory(OrderCategory.Arrived)
            }
            OrderCategory.Completed.label -> {
                viewModel.orderCategory(OrderCategory.Completed)
            }
            OrderCategory.PaymentProcessing.label ->{
                viewModel.orderCategory(OrderCategory.PaymentProcessing)
            }
            else -> {
                viewModel.orderCategory(OrderCategory.Accept)
            }
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.ProviderOrder.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                when(orderCategory){
                    OrderCategory.Arrived -> {
                        viewModel.orderCategory(OrderCategory.ProviderIsNear)
                    }
                    OrderCategory.Completed, OrderCategory.PaymentProcessing -> {
                        navController.navigateUp()
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
                            OrderCategory.Completed -> {
//                                navController.navigate(
//                                    Screen.ProviderOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.PaymentProcessing.label}"
//                                )
                                navController.navigate(
//                                    P4pProviderScreens.ProviderOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.PaymentProcessing.label}"
                                    P4pProviderScreens.ProviderOrder.route
                                )
                            }
                            OrderCategory.Accept -> {
//                                navController.navigate(
//                                    Screen.Tracking.route+"?$PARENT_ROUTE_KEY=${Screen.ProviderOrder.route}"
//                                )
                            }
                            OrderCategory.ProviderIsNear -> {
//                                navController.navigate(
//                                    Screen.ProviderOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.Arrived.label}"
//                                )
                            }
                            OrderCategory.Arrived -> {
//                                navController.navigate(
//                                    Screen.Tracking.route+"?$PARENT_ROUTE_KEY=${Screen.CustomerOrder.route}"
//                                )
                            }
                            OrderCategory.PaymentProcessing -> {
//                                navController.navigate(
//                                    Screen.PaidOrder.route+"?$ACCOUNT_TYPE_KEY=${AccountType.PROVIDER.label}"
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
                        when(orderCategory){
                            OrderCategory.Accept -> {
//                                navController.navigate(Screen.OrderMapGps.route)
                            }
                            else -> {
//                                navController.navigate(Screen.OrderAddressMapView.route)
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                OrderedServicesCard(
                    onClickService = {
//                        navController.navigate(
//                            Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ProviderOrder.route}"
//                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                OrderActions(
                    orderCategory = orderCategory,
                    accountType = AccountType.PROVIDER,
                    confirmedArrival = confirmedArrival.value,
                    onComplete = {
//                        navController.navigate(
//                            Screen.ProviderOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.Completed.label}"
//                        )
                    },
                    onMarkArrived = {
//                        navController.navigate(Screen.OrderMapGps.route)
                    },
                    onProviderNotArrived = {
                        viewModel.orderCategory(OrderCategory.ProviderIsNear)
                    },
                    onClickReserveFunds = {
                        viewModel.orderCategory(OrderCategory.Accept)
                    },
                    onArrivedCancel = {
                        viewModel.orderCategory(OrderCategory.Completed)
                    },
                    onApprove = {
                        viewModel.orderCategory(OrderCategory.PaymentProcessing)
                    },
                    onUnsatisfied = {
                        viewModel.orderCategory(OrderCategory.PaymentProcessing)
                    },
                    onProviderNearCancel = {},
                    onConfirmArrival = {
                        viewModel.orderCategory(OrderCategory.Arrived)
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))
//                OtherServicesCard(
//                    navController = navController,
//                    accountType = AccountType.PROVIDER,
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
                            label = "Customer",
                            value = "Leo Potter",
                            isItemClickable = true,
                            showForwardArrow = true,
                            onClickItem = {
                                scope.launch {
//                                    navController.navigate(Screen.OrderCustomerProfile.route){
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