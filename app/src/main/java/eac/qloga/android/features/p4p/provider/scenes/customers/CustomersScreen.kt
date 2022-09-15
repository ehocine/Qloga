package eac.qloga.android.features.p4p.provider.scenes.customers

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FilterButton
import eac.qloga.android.core.shared.components.Buttons.MapButton
import eac.qloga.android.core.shared.components.Buttons.ProviderUserButton
import eac.qloga.android.core.shared.components.Customer
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.shared.components.CustomersMapView
import eac.qloga.android.features.p4p.provider.shared.components.ProviderBottomSheetCustomers
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderNegotiationViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.IconStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CustomersScreen(
    navController: NavController,
    hideNavBar: (Boolean) -> Unit = {},
    viewModel: ProviderNegotiationViewModel = hiltViewModel()
    ) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val isMapShown = viewModel.isShownMap.value
    val title = "Izya Shniperson"
    val rating = 4.2
    val imageId = R.drawable.ql_cst_avtr_acc
    val description = "Residential cleaning services " +
            "for all parts of your home. With tasks" +
            " covering dish Residential cleaning " +
            "services for all parts of your home." +
            " With tasks covering dish  Residential " +
            "cleaning services for all parts of your home." +
            " With tasks covering dish Residential cleaning" +
            " services for all parts of your home. With tasks " +
            "covering."

    val lazyColumnState = rememberLazyListState()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    LaunchedEffect(modalBottomSheetState.isVisible){
        hideNavBar(!modalBottomSheetState.isVisible)
    }

    BackHandler {
        navController.popBackStack(route = P4pShowroomScreens.Enrolled.route, inclusive = false)
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            ProviderBottomSheetCustomers(
                selectedVerificationIndexes = viewModel.selectedCustomerVerificationOption.value,
                onSelectVerificationOption = { viewModel.onChangeCustomerVerificationOption(it) }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pProviderScreens.Customers.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    showBackPressButton = false,
                    leadingActions = {
                        MapButton(
                            onClick = { viewModel.toggleMapShow() },
                            color = if(isMapShown) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    actions = {
                        FilterButton( onClick = {
                            hideNavBar(!modalBottomSheetState.isVisible)
                            scope.launch {
                                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }, Color.Black )
                        ProviderUserButton(
                            onClick = {
                                scope.launch {
                                    /*navController.navigate(
                                        Screen.Account.route+"?$ACCOUNT_TYPE_KEY=${AccountType.PROVIDER.label}" +
                                                "&$PARENT_ROUTE_KEY=${Screen.ProviderNavContainer.route}"
                                    )
                                     */
                                }
                            },
                            color = info_sky
                        )
                    }
                ) {
                    navController.navigateUp()
                }
            }
        ) { paddingValues ->
            val topPadding = paddingValues.calculateTopPadding()

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(topPadding))

                    when(true){
                        isMapShown -> {
                            CustomersMapView(coordinates = viewModel.customersCoordinates.value)
                        }
                        else -> {
                            LazyColumn(
                                state = lazyColumnState,
                                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp)
                            ){
                                val customers = listOf(1,2,3,4,5,6,7,8,9,10,11,12)
                                items(customers){ _ ->
                                    Customer(
                                        title = title,
                                        rating = rating,
                                        imageId = imageId,
                                        description = description,
                                        onClickItem = {
                                            scope.launch {
                                                // navController.navigate(Screen.CustomerDetails.route)
                                            }
                                        },
                                        statusCompose = {
                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    IconStatus(modifier= Modifier.weight(1f),label = "3.5 miles" , iconId = R.drawable.ic_ql_miles)
                                                    IconStatus(modifier= Modifier.weight(1f),label = "free" , iconId = R.drawable.ic_ql_free)
                                                }
                                                Spacer(Modifier.height(12.dp))
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Visits(modifier= Modifier.weight(1f), count = 12)
                                                    Column(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(horizontal = 4.dp),
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Icon(
                                                            modifier = Modifier.size(22.dp),
                                                            painter = painterResource(id = R.drawable.ic_ql_money_bag),
                                                            contentDescription = null,
                                                            tint = gray30
                                                        )
                                                        Spacer(Modifier.height(4.dp))
                                                        Text(
                                                            text = "£75.00/",
                                                            style = MaterialTheme.typography.labelMedium,
                                                            textAlign = TextAlign.Center,
                                                            fontWeight = FontWeight.W600
                                                        )
                                                        Text(
                                                            text = "hours",
                                                            style = MaterialTheme.typography.labelMedium,
                                                            textAlign = TextAlign.Center,
                                                            fontWeight = FontWeight.W600,
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    )
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(paddingValues.calculateBottomPadding() + 32.dp))
                }
            }
        }
    }
}

@Composable
private fun Visits(
    modifier: Modifier = Modifier,
    count: Int
) {
    Column(
        modifier = modifier
            .padding(start = 4.dp, bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Visits",
            style = MaterialTheme.typography.labelMedium,
            color = gray30,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W600
        )
        Text(
            text = "$count",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W600
        )
    }
}



