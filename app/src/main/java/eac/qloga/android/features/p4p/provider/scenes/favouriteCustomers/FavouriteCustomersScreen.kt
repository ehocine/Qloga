package eac.qloga.android.features.p4p.provider.scenes.favouriteCustomers

import P4pCustomerScreens
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.NavigationActions
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.Buttons.ProviderUserButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.blueTextColor
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.theme.purple_heart
import eac.qloga.android.core.shared.utils.AddressConverter
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.customer.scenes.customerProfile.CustomerProfileViewModel
import eac.qloga.android.features.p4p.provider.shared.components.FavouriteCustomersListItem
import eac.qloga.android.features.p4p.provider.shared.utils.ProviderBottomNavItems
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderDashboardViewModel
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.account.AccountViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteCustomersScreen(
    navController: NavController,
    actions: NavigationActions,
    viewModel: FavouriteCustomersViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = 24.dp
    val prvId = viewModel.providerId.value
    val favouriteCustomersList = viewModel.cstPublicProfileList.value
    val favouriteCustomersState = viewModel.cstPublicProfileState.collectAsState()
    val lazyScrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    BackHandler {
        navController.popBackStack(route = P4pShowroomScreens.Enrolled.route, inclusive = false)
    }

    LaunchedEffect(Unit){
        viewModel.preCallsLoad()
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.FavouriteCustomers.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                showBackPressButton = false,
                actions = {
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
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(titleBarHeight))

            when(true){
                favouriteCustomersList.isNotEmpty() -> {
                    LazyColumn(
                        state = lazyScrollState,
                        contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
                    ){
                        items(favouriteCustomersList, key = {it.id} ){ favouriteCustomer ->
                            val address = favouriteCustomer.contacts.address
                            val cstId = favouriteCustomer.id
                            val mediaId = favouriteCustomer.avatarId

                            FavouriteCustomersListItem(
                                modifier = Modifier
                                    .clickable {
                                        CustomerProfileViewModel.customerId.value = cstId
                                        CustomerProfileViewModel.providerId.value = prvId
                                        CustomerProfileViewModel.customerProfile.value = favouriteCustomer
                                        CustomerProfileViewModel.showHeart.value = true
                                        navController.navigate( P4pCustomerScreens.CustomerProfile.route )
                                    }
                                    .padding(horizontal = containerHorizontalPadding),
                                onclickQuote = {
                                    scope.launch {
                                        /*navController.navigate(
                                            Screen.Services.route+"?$PARENT_ROUTE_KEY=${Screen.FavouriteCustomers.route}"
                                        )
                                         */
                                    }
                                },
                                imageId = viewModel.avatarList.value.find { it.id == mediaId}?.bitmap,
                                name = favouriteCustomer.fullName,
                                location = favouriteCustomer.contacts.address.town,
                                showBottomLine = favouriteCustomer != favouriteCustomersList.last(),
                                address = AddressConverter.addressToString(address)
                            )
                        }
                    }
                }
                else -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        OrdersEmptyState(modifier = Modifier.size(400.dp),
                            onSearchClick = {
                                scope.launch {
                                    ProviderDashboardViewModel.selectedNavItem.value =
                                        ProviderBottomNavItems.CUSTOMERS
                                    navController.navigate(P4pProviderScreens.ProviderDashboard.route) {
                                        launchSingleTop = true
                                    }
                                }
                            },
                            ordersClick = {
                                scope.launch {
                                    ProviderDashboardViewModel.selectedNavItem.value =
                                        ProviderBottomNavItems.ORDERS
                                    navController.navigate(P4pProviderScreens.ProviderDashboard.route) {
                                        launchSingleTop = true
                                    }
                                }
                            },
                            heartClick = {

                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrdersEmptyState(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    ordersClick: () -> Unit,
    heartClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.ic_no_favorites),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Go to",
                    color = blueTextColor,
                    style = MaterialTheme.typography.titleLarge
                )
                Buttons.SearchButton(
                    onClick = { onSearchClick() },
                    color = orange1
                )
                Text(
                    text = "Customer search",
                    color = blueTextColor,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "or See existing",
                    color = blueTextColor,
                    style = MaterialTheme.typography.titleLarge
                )
                Buttons.OrdersButton(
                    onClick = { ordersClick() },
                    color = orange1
                )
                Text(
                    text = "Direct Inquiries",
                    color = blueTextColor,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "and Orders and make",
                    color = blueTextColor,
                    style = MaterialTheme.typography.titleLarge
                )
                Buttons.HeartButton(
                    onClick = { heartClick() },
                    color = purple_heart
                )
                Text(
                    text = "favorite",
                    color = blueTextColor,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}