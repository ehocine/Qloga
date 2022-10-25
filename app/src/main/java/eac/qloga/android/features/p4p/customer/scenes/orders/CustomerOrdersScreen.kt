package eac.qloga.android.features.p4p.customer.scenes.orders

import P4pCustomerScreens
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FilterButton
import eac.qloga.android.core.shared.components.Buttons.UserButton
import eac.qloga.android.core.shared.components.ImagePlaceholder
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.customer.shared.components.CustomerOrdersSearchFilter
import eac.qloga.android.features.p4p.customer.shared.viewModels.CustomerDashboardViewModel
import eac.qloga.android.features.p4p.shared.components.OrdersTabRow
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.account.AccountViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.OrdersTabTypes
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
    ExperimentalPagerApi::class)
@Composable
fun CustomerOrdersScreen(
    navController: NavController,
    hideNavBar: (Boolean) -> Unit = {},
    viewModel: CustomerDashboardViewModel = hiltViewModel()
) {
    val orderNumberState = remember{ mutableStateOf(InputFieldState(text = "10" ))}
    val isOrdersEmpty = remember{ mutableStateOf(false) }
    val isQuotesEmpty = remember{ mutableStateOf(false) }
    val isInquiresEmpty = remember{ mutableStateOf(false) }
    val isTodayEmpty = remember{ mutableStateOf(true) }
    val fromDate = remember{ mutableStateOf("")}
    val toDate = remember{ mutableStateOf("")}

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(modalBottomSheetState.isVisible){
        hideNavBar(modalBottomSheetState.isVisible)
    }

    BackHandler {
        navController.popBackStack(route = P4pShowroomScreens.Enrolled.route, inclusive = false)
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topEnd = 16.dp , topStart = 16.dp),
        sheetContent = {
            CustomerOrdersSearchFilter(
                orderNumberState = orderNumberState.value,
                selectedStatus = viewModel.selectedStatus,
                fromDate = fromDate.value,
                toDate = toDate.value,
                onChangeOrderValue = { orderNumberState.value = orderNumberState.value.copy(text =it)},
                onFocusOrderValue = { orderNumberState.value = orderNumberState.value.copy(isFocused = it.isFocused) },
                onPickFromDate = { fromDate.value = it },
                onPickToDate = { toDate.value = it },
                onStatusSelect = { viewModel.onSelectStatusOption(it) }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pCustomerScreens.CustomerOrders.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    onBackPress = { navController.navigateUp() },
                    showBackPressButton = false,
                    actions = {
                        FilterButton(
                            onClick = {
                                scope.launch {
                                    hideNavBar(modalBottomSheetState.isVisible)
                                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            },
                            color = MaterialTheme.colorScheme.onBackground
                        )
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
                //space for title bar
                Spacer(modifier = Modifier.height(titleBarHeight))

                OrdersTabRow(
                    selectedTabItem = OrdersTabTypes.listOfItems[pagerState.currentPage],
                    onSelectTabItem = {
                        scope.launch {
                            pagerState.animateScrollToPage(OrdersTabTypes.listOfItems.indexOf(it))
                        }
                    },
                    ordersTabsItems = OrdersTabTypes.listOfItems
                )

                HorizontalPager(
                    count = OrdersTabTypes.listOfItems.size,
                    state = pagerState
                ) { page ->
                    when(page){
                        0 -> {
                            CustomerOrdersTab(
                                isOrdersEmpty = isOrdersEmpty.value,
                                navController = navController
                            )
                        }

                        1 -> {
                            CustomerQuotesTab(
                                isQuotesEmpty = isQuotesEmpty.value,
                                navController = navController
                            )
                        }

                        2 -> {
                            CustomerInquiresTab(
                                isInquiresEmpty = isInquiresEmpty.value,
                                navController = navController
                            )
                        }

                        3 -> {
                            if(isTodayEmpty.value){
                                OrdersEmptyStateCard( imageId = R.drawable.empty_state_holder2 )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrdersEmptyStateCard(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    imageId: Int
) {
    ImagePlaceholder(
        modifier = modifier.fillMaxSize(),
        imageModifier = imageModifier.fillMaxSize(),
        imageId = imageId
    )
}