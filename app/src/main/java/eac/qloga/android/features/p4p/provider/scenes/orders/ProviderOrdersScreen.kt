package eac.qloga.android.features.p4p.provider.scenes.orders

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FilterButton
import eac.qloga.android.core.shared.components.Buttons.MapButton
import eac.qloga.android.core.shared.components.Buttons.ProviderUserButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.scenes.inquiresTab.ProviderInquiresTabScreen
import eac.qloga.android.features.p4p.provider.scenes.ordersTab.ProviderOrdersTabScreen
import eac.qloga.android.features.p4p.provider.scenes.quotesTab.ProviderQuotesTabScreen
import eac.qloga.android.features.p4p.provider.shared.utils.ProviderOrdersSearchFilter
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderDashboardViewModel
import eac.qloga.android.features.p4p.shared.components.OrdersTabRow
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.account.AccountViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.OrdersTabTypes
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class )
@Composable
fun ProviderOrdersScreen(
    navController: NavController,
    hideNavBar: (Boolean) -> Unit = {},
    viewModel: ProviderDashboardViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = 24.dp
    val isOrdersEmpty = remember{ mutableStateOf(false) }
    val isQuotesEmpty = remember{ mutableStateOf(false) }
    val isInquiresEmpty = remember{ mutableStateOf(false) }
    val isTodayEmpty = remember{ mutableStateOf(true) }
    val orderNumberState = remember{ mutableStateOf(InputFieldState(text = "10"))}
    val fromDate = remember{ mutableStateOf("")}
    val toDate = remember{ mutableStateOf("")}

    val pagerState = rememberPagerState()
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = modalBottomSheetState.isVisible){
        hideNavBar(!modalBottomSheetState.isVisible)
    }

    BackHandler {
        navController.popBackStack(route = P4pShowroomScreens.Enrolled.route, inclusive = false)
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            ProviderOrdersSearchFilter(
                orderNumberState = orderNumberState.value,
                selectedStatus = viewModel.selectedStatus.value,
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
                    label = P4pProviderScreens.ProviderOrders.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    showBackPressButton = false,
                    leadingActions = {
                        MapButton(
                            onClick = {
                                // navController.navigate(Screen.ShowLocationMapView.route)
                            },
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    actions = {
                        FilterButton(
                            onClick = {
                                 scope.launch {
                                     modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                 }
                            },
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        ProviderUserButton(
                            onClick = {
                                scope.launch {
                                    AccountViewModel.selectedAccountType = AccountType.PROVIDER
                                    navController.navigate(P4pScreens.Account.route)
                                }
                            }
                        )
                    }
                )
            }
        ) { paddingValues ->

            val titleBarHeight = paddingValues.calculateTopPadding()
            Column(
                modifier = Modifier.fillMaxSize()
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    state = pagerState,
                    count = OrdersTabTypes.listOfItems.size
                ) { page ->
                    when(page) {
                        0 -> {
                            ProviderOrdersTabScreen(
                                isOrdersEmpty = isOrdersEmpty.value,
                                navController = navController
                            )
                        }

                        1 -> {
                            ProviderQuotesTabScreen(
                                isQuotesEmpty = isQuotesEmpty.value,
                                navController = navController
                            )
                        }

                        2 -> {
                            ProviderInquiresTabScreen(
                                isInquiresEmpty = isInquiresEmpty.value,
                                navController = navController
                            )
                        }

                        3 -> {
                            if(isTodayEmpty.value){
                                TodayZeroState()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TodayZeroState(
    modifier: Modifier = Modifier,
) {
    val titleText = "Sorry, but there is nothing for today "
    val inlineId = "inlineContent"
    val iconSize = 28.sp
    val labelText = buildAnnotatedString {
        append("Here you will see the order schedule for today." +
                " You can view both in a list and on a map by selecting the icon  ")
        appendInlineContent(inlineId, "[icon]")
        append(" at the top of the screen")
    }

    val inlineContent = mapOf(
        Pair(inlineId,
            InlineTextContent(
                Placeholder(
                    width = iconSize,
                    height = iconSize,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Bottom
                )
            ) {
                Icon(
                    modifier = Modifier.padding(top = 8.dp),
                    painter = painterResource(id = R.drawable.ic_map_check),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
            ,
            text = titleText,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
            ,
            text = labelText,
            inlineContent = inlineContent,
            style = MaterialTheme.typography.titleMedium,
            color = gray30
        )
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.map_daily_path),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}