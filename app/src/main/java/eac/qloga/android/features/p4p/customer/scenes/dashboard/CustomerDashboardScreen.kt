package eac.qloga.android.features.p4p.customer.scenes.dashboard

import P4pCustomerScreens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.BottomNav
import eac.qloga.android.core.shared.components.BottomNavItem
import eac.qloga.android.features.p4p.customer.scenes.favouriteProviders.FavouriteProvidersScreen
import eac.qloga.android.features.p4p.customer.scenes.openRequests.OpenRequestsScreen
import eac.qloga.android.features.p4p.customer.scenes.orders.CustomerOrdersScreen
import eac.qloga.android.features.p4p.customer.shared.components.CustomerBottomNavItems
import eac.qloga.android.features.p4p.customer.shared.viewModels.CustomerDashboardViewModel
import eac.qloga.android.features.p4p.shared.components.ProfileInfoDialog
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.account.AccountViewModel
import eac.qloga.android.features.p4p.shared.scenes.providerSearch.ProviderSearchScreen
import eac.qloga.android.features.p4p.shared.utils.AccountType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDashboardScreen(
    navController: NavController,
    viewModel: CustomerDashboardViewModel = hiltViewModel(),
) {
    val selectedNavItem = viewModel.selectNavItem
    val showBottomNavBar = remember { mutableStateOf(true) }
    val alreadyShownProfileInfoDialog = CustomerDashboardViewModel.alreadyShownProfileInfoDialog
    val scope = rememberCoroutineScope()

    Scaffold{ paddingValues ->
        val titleBarHeight = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when(selectedNavItem){
                        CustomerBottomNavItems.ORDERS -> {
                            CustomerOrdersScreen(
                                navController = navController,
                                hideNavBar = { showBottomNavBar.value = !it}
                            )
                        }

                        CustomerBottomNavItems.FAVOURITES -> {
                            FavouriteProvidersScreen(
                                navController = navController,
                            )
                        }

                        CustomerBottomNavItems.PROVIDERS -> {
                            ProviderSearchScreen(
                                navController = navController,
                                parentRoute = P4pCustomerScreens.CustomerDashboard.route,
                                hideNavBar = { showBottomNavBar.value = !it }
                            )
                        }

                        CustomerBottomNavItems.REQUESTS -> {
                            OpenRequestsScreen(
                                navController = navController,
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = showBottomNavBar.value,
                ) {
                    BottomNav(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        BottomNavItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Start)
                                .weight(1f)
                            ,
                            label = CustomerBottomNavItems.ORDERS.label,
                            icon = CustomerBottomNavItems.ORDERS.icon,
                            count =  4,
                            isSelected = selectedNavItem == CustomerBottomNavItems.ORDERS,
                            onClick = {
                                viewModel.onSelectNavItem(CustomerBottomNavItems.ORDERS)
                            }
                        )

                        BottomNavItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                            ,
                            label = CustomerBottomNavItems.REQUESTS.label,
                            icon = CustomerBottomNavItems.REQUESTS.icon,
                            count =  0,
                            isSelected = selectedNavItem == CustomerBottomNavItems.REQUESTS,
                            onClick = {
                                viewModel.onSelectNavItem(CustomerBottomNavItems.REQUESTS)
                            }
                        )

                        BottomNavItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Start)
                                .weight(1f)
                            ,
                            label = CustomerBottomNavItems.PROVIDERS.label,
                            icon = CustomerBottomNavItems.PROVIDERS.icon,
                            count =  0,
                            isSelected = selectedNavItem == CustomerBottomNavItems.PROVIDERS,
                            onClick = {
                                viewModel.onSelectNavItem(CustomerBottomNavItems.PROVIDERS)
                            }
                        )

                        BottomNavItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Start)
                                .weight(1f)
                            ,
                            label = CustomerBottomNavItems.FAVOURITES.label,
                            icon = CustomerBottomNavItems.FAVOURITES.icon,
                            count =  0,
                            isSelected = selectedNavItem == CustomerBottomNavItems.FAVOURITES,
                            onClick = {
                                viewModel.onSelectNavItem(CustomerBottomNavItems.FAVOURITES)
                            }
                        )
                    }
                }
            }

            if(viewModel.showProfileInfoDialog && !alreadyShownProfileInfoDialog){
                Dialog(
                    onDismissRequest = {
                        viewModel.onDismissInfoDialog()
                    }
                ) {
                    ProfileInfoDialog(
                        accountType = AccountType.CUSTOMER,
                        isDontShowAgainChecked = !viewModel.showProfileInfoDialogCheck,
                        onClickCheckBox = { viewModel.toggleProfileInfoDialogCheck()},
                        goToProfile = {
                            scope.launch{
                                AccountViewModel.selectedAccountType = AccountType.CUSTOMER
                                navController.navigate(P4pScreens.Account.route)
                                viewModel.onDismissInfoDialog()
                            }
                        }
                    )
                }
            }
        }
    }
}