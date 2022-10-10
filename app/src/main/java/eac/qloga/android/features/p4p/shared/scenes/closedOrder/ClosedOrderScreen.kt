package eac.qloga.android.features.p4p.shared.scenes.closedOrder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.features.p4p.shared.components.*
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.OrderBottomContentType
import eac.qloga.android.features.p4p.shared.utils.OrderCategory
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ClosedOrderScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel(),
    parentRoute: String? = null,
    accountTypeParam: String? = null
) {
    val containerTopPadding = 16.dp
    val containerHorizontalPadding = 24.dp
    val accountType = remember { mutableStateOf(AccountType.CUSTOMER) }
    val selectedRatingTabIndex = remember{ mutableStateOf(0) }
    val selectedFeedbackTabIndex = remember{ mutableStateOf(0) }
    val tabItemsCustomer = listOf("Customer's", "Your's")
    val tabItemsProvider = listOf("Provider's", "Your's")
    val isNotReview = remember{ mutableStateOf(false) }
    val communicationRating = remember{ mutableStateOf(4) }
    val communicationRating2 = remember{ mutableStateOf(5) }
    val timelyArrivedRating = remember{ mutableStateOf(3) }
    val timelyArrivedRating2 = remember{ mutableStateOf(4) }
    val qualityOfServiceRating = remember{ mutableStateOf(4) }
    val friendlinessRating = remember{ mutableStateOf(5) }
    val performanceRating = remember{ mutableStateOf(4) }

    val scrollState = rememberScrollState()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomContentType = remember{ mutableStateOf(OrderBottomContentType.FEEDBACK) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        if(parentRoute == "NotReview"){ isNotReview.value = true }
        when(accountTypeParam){
            AccountType.PROVIDER.label -> { accountType.value = AccountType.PROVIDER }
            AccountType.CUSTOMER.label -> { accountType.value = AccountType.CUSTOMER }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                BottomSheetDashLine()
                when(bottomContentType.value){
                    OrderBottomContentType.FEEDBACK -> { FeedbackInfo() }
                    OrderBottomContentType.RATING -> { RatingInfo() }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pScreens.ClosedOrder.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                ) {
                    navController.navigateUp()
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
                        orderCategory = OrderCategory.Closed,
                        onClickItem = {}
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OrderSummery(
                        date = "12/08/2022",
                        time = "09:00",
                        address = "30 Cloth Market, Merchant House, Newcastle upon Tyne, GB",
                        price = 84.00,
                        onClickLocationMarker = {
//                            navController.navigate(Screen.ShowLocationMapView.route)
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    if(isNotReview.value){
                        Column {
                            Text(text = "RATING", style = MaterialTheme.typography.titleMedium, color = gray1)
                            OrderStatusButton(label = "You have no rating from customer")
                        }
                    }else{
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                            ,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "RATING",
                                style = MaterialTheme.typography.titleMedium,
                                color = gray1
                            )
                            Box(modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    scope.launch {
                                        bottomContentType.value = OrderBottomContentType.RATING
                                        modalBottomSheetState.show()
                                    }
                                }
                                .padding(4.dp)
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(20.dp),
                                    painter = painterResource(id = R.drawable.ic_info),
                                    contentDescription = null,
                                    tint = orange1
                                )
                            }
                        }

                        TwoSwitchTabRow(
                            height = 32.dp,
                            selectedColor = MaterialTheme.colorScheme.primary,
                            tabsContent = if(accountType.value == AccountType.CUSTOMER) tabItemsCustomer else tabItemsProvider,
                            selectedTapIndex = selectedRatingTabIndex.value,
                            onSelect = { selectedRatingTabIndex.value = it },
                        )
                        when(selectedRatingTabIndex.value){
                            0 -> {
                                ClosedOrderRatingCard(
                                    communicationRating = communicationRating.value,
                                    timelyArrived = timelyArrivedRating.value,
                                    qualityOfService = qualityOfServiceRating.value,
                                    friendliness= friendlinessRating.value,
                                    performance = performanceRating.value,
                                )
                            }
                            1 -> {
                                ClosedOrderRatingCard(
                                    communicationRating = communicationRating2.value,
                                    timelyArrived = timelyArrivedRating2.value,
                                    qualityOfService = qualityOfServiceRating.value,
                                    friendliness= friendlinessRating.value,
                                    performance = performanceRating.value,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    if(isNotReview.value){
                        Column {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "FEEDBACK",
                                style = MaterialTheme.typography.titleMedium,
                                color = gray1
                            )
                            OrderStatusButton(label = "You have no feedback from customer")
                        }
                    }else{
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                            ,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Text(text = "FEEDBACK", style = MaterialTheme.typography.titleMedium, color = gray1)
                            Box(modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    scope.launch {
                                        bottomContentType.value = OrderBottomContentType.FEEDBACK
                                        modalBottomSheetState.show()
                                    }
                                }
                                .padding(4.dp)
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(20.dp),
                                    painter = painterResource(id = R.drawable.ic_info),
                                    contentDescription = null,
                                    tint = orange1
                                )
                            }
                        }
                        TwoSwitchTabRow(
                            height = 32.dp,
                            selectedColor = MaterialTheme.colorScheme.primary,
                            tabsContent = if(accountType.value == AccountType.CUSTOMER) tabItemsCustomer else tabItemsProvider,
                            selectedTapIndex = selectedFeedbackTabIndex.value,
                            onSelect = { selectedFeedbackTabIndex.value = it },
                        )
                        Spacer(Modifier.height(8.dp))
                        when(selectedFeedbackTabIndex.value){
                            0 -> { ClosedOrderFeedbackContent() }
                            1 -> { ClosedOrderFeedbackContent() }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    OrderBottomCard(
                        navController = navController,
                        accountType = accountType.value,
                        name = "Leo Potter"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}