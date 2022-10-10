package eac.qloga.android.features.p4p.shared.scenes.paidOrder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.shared.components.*
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.OrderBottomContentType
import eac.qloga.android.features.p4p.shared.utils.OrderCategory
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel
import kotlinx.coroutines.launch

typealias ComposeFun = @Composable () -> Unit

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PaidOrderScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel(),
    accountTypeParam: String? = null
) {
    val containerTopPadding = 16.dp
    val containerHorizontalPadding = 24.dp
    val accountType = remember { mutableStateOf(AccountType.CUSTOMER) }
    val publicFeedback = remember{ mutableStateOf(InputFieldState(hint = "Public"))}
    val privateFeedback = remember{ mutableStateOf(InputFieldState(hint = "Private"))}

    val scrollState = rememberScrollState()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomContentType = remember{ mutableStateOf(OrderBottomContentType.FEEDBACK) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        when(accountTypeParam){
            AccountType.CUSTOMER.label -> { accountType.value = AccountType.CUSTOMER}
            AccountType.PROVIDER.label -> { accountType.value = AccountType.PROVIDER}
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topEnd = 12.dp,
            topStart = 12.dp
        ),
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
                    label = P4pScreens.PaidOrder.titleName,
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
                        orderCategory = OrderCategory.Paid,
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

                    Spacer(modifier = Modifier.height(16.dp))
                    PaidOrderActionButton(
                        accountType = accountType.value.label,
                        onClickReview = {
//                            navController.navigate(
//                                Screen.ClosedOrder.route+"?$ACCOUNT_TYPE_KEY=${accountType.value.label}"
//                            )
                        },
                        onClickNotReview = {
//                            navController.navigate(
//                                Screen.ClosedOrder.route+"?$PARENT_ROUTE_KEY=NotReview" +
//                                        "&$ACCOUNT_TYPE_KEY=${accountType.value.label}"
//                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    PaidOrderRatingCard(
                        onClickInfo = {
                            scope.launch {
                                bottomContentType.value = OrderBottomContentType.RATING
                                modalBottomSheetState.show()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    OrderFeedbackCard(
                        publicFeedbackState = publicFeedback.value,
                        privateFeedbackState = privateFeedback.value,
                        onEnterPublicFeedback = {
                            publicFeedback.value = publicFeedback.value.copy(text = it)
                        },
                        onEnterPrivateFeedback = {
                            privateFeedback.value = privateFeedback.value.copy(text = it)
                        },
                        onFocusPublicFeedback = {
                            publicFeedback.value = publicFeedback.value.copy(isFocused = it.isFocused)
                        },
                        onFocusPrivateFeedback = {
                            privateFeedback.value = privateFeedback.value.copy(isFocused = it.isFocused)
                        },
                        onClickInfo = {
                            scope.launch {
                                bottomContentType.value = OrderBottomContentType.FEEDBACK
                                modalBottomSheetState.show()
                            }
                        }
                    )

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