package eac.qloga.android.features.p4p.provider.scenes.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.NavigationActions
import eac.qloga.android.core.shared.components.BottomNav
import eac.qloga.android.core.shared.components.BottomNavItem
import eac.qloga.android.features.p4p.provider.scenes.customers.CustomersScreen
import eac.qloga.android.features.p4p.provider.scenes.favouriteCustomers.FavouriteCustomersScreen
import eac.qloga.android.features.p4p.provider.scenes.orders.ProviderOrdersScreen
import eac.qloga.android.features.p4p.provider.shared.components.ProviderInfoDialog
import eac.qloga.android.features.p4p.provider.shared.utils.ProviderBottomNavItems
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderNegotiationViewModel

@OptIn(ExperimentalMaterial3Api::class,)
@Composable
fun ProviderDashboardScreen(
    navController: NavController,
    viewModel: ProviderNegotiationViewModel = hiltViewModel()
) {
    val showBottomNavBar = remember{ mutableStateOf(true) }
    val selectedNavItem = viewModel.selectedNavItem.value
    val dontShowInfoDialogAgain = viewModel.notShowAgainProviderInfoDialog.value
    val showDialog = remember{ mutableStateOf(true) }

    LaunchedEffect(Unit){
        showDialog.value = !dontShowInfoDialogAgain
    }

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
                        ProviderBottomNavItems.ORDERS -> {
                            ProviderOrdersScreen(
                                navController = navController,
                                viewModel = viewModel,
                                hideNavBar = { showBottomNavBar.value = it }
                            )
                        }

                        ProviderBottomNavItems.FAVOURITES -> {
                            FavouriteCustomersScreen(
                                navController = navController,
                                actions = NavigationActions(navController)
                            )
                        }

                        ProviderBottomNavItems.CUSTOMERS -> {
                            CustomersScreen(
                                navController = navController,
                                viewModel = viewModel,
                                hideNavBar = { showBottomNavBar.value = it }
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
                                .weight(1f)
                            ,
                            label = ProviderBottomNavItems.ORDERS.label,
                            icon = ProviderBottomNavItems.ORDERS.icon,
                            count =  4,
                            isSelected = selectedNavItem == ProviderBottomNavItems.ORDERS,
                            onClick = {
                                viewModel.onSelectNavItem(ProviderBottomNavItems.ORDERS)
                            }
                        )
                        BottomNavItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                            ,
                            label = ProviderBottomNavItems.CUSTOMERS.label,
                            icon = ProviderBottomNavItems.CUSTOMERS.icon,
                            count =  0,
                            isSelected = selectedNavItem == ProviderBottomNavItems.CUSTOMERS,
                            onClick = {
                                viewModel.onSelectNavItem(ProviderBottomNavItems.CUSTOMERS)
                            }
                        )
                        BottomNavItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                            ,
                            label = ProviderBottomNavItems.FAVOURITES.label,
                            icon = ProviderBottomNavItems.FAVOURITES.icon,
                            count =  0,
                            isSelected = selectedNavItem == ProviderBottomNavItems.FAVOURITES,
                            onClick = {
                                viewModel.onSelectNavItem(ProviderBottomNavItems.FAVOURITES)
                            }
                        )
                    }
                }
            }
            if(showDialog.value && viewModel.showAccountSwitchInfoDialogCount.value < 1){
                Dialog(
                    onDismissRequest = {
                        showDialog.value = false
                        viewModel.onAccountSwitchInfoDialogInc()
                    }
                ) {
                    ProviderInfoDialog(
                        showAgain = dontShowInfoDialogAgain,
                        onCheckShowAgain = { viewModel.onShowProviderInfoDialog() }
                    ) {
                        showDialog.value = false
                        viewModel.onAccountSwitchInfoDialogInc()
                    }
                }
            }
        }
    }
}