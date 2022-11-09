package eac.qloga.android.features.p4p.customer.scenes.favouriteProviders

import P4pCustomerScreens
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.UserButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.features.p4p.customer.shared.components.FavouriteProvidersListItem
import eac.qloga.android.features.p4p.customer.shared.viewModels.CustomerDashboardViewModel
import eac.qloga.android.features.p4p.shared.components.OrdersEmptyStateCard
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.account.AccountViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteProvidersScreen(
    navController: NavController,
    viewModel: CustomerDashboardViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = 24.dp
    val isProvidersEmpty = remember { mutableStateOf(false) }
    val lazyScrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    BackHandler {
        navController.popBackStack(route = P4pShowroomScreens.Enrolled.route, inclusive = false)
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pCustomerScreens.FavouriteProviders.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                onBackPress = { navController.navigateUp() },
                showBackPressButton = false,
                actions = {
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(titleBarHeight))

            if (isProvidersEmpty.value) {
                //TODO SVG
                OrdersEmptyStateCard(
                    modifier = Modifier.weight(1f),
                    imageId = R.drawable.ic_fv_emtpy
                )
            } else {
                val providersList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
                LazyColumn(
                    state = lazyScrollState,
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(providersList, key = { it }) {
                        FavouriteProvidersListItem(
                            modifier = Modifier
                                .clickable {
                                    scope.launch {
                                        //navController.navigate(Screen.FavouriteProviderView.route)
                                    }
                                }
                                .padding(horizontal = containerHorizontalPadding),
                            imageId = R.drawable.pvr_profile_ava,
                            name = "Kai's Cleaning agency",
                            location = "Edinburgh",
                            showBottomLine = it != providersList.last(),
                            tags = listOf("Cleaning", "Pets", "Care"),
                            onClickDirectInquiry = {
                                scope.launch {
                                    //navController.navigate(
                                    //    Screen.Services.route+"?$PARENT_ROUTE_KEY=${Screen.FavouriteProviders.route}"
                                    //)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}