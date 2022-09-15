package eac.qloga.android.features.p4p.provider.scenes.favouriteCustomers

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
import eac.qloga.android.core.shared.components.Buttons.ProviderUserButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.shared.components.FavouriteCustomersListItem
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderNegotiationViewModel
import eac.qloga.android.features.p4p.shared.components.OrdersEmptyStateCard
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteCustomersScreen(
    navController: NavController,
    viewModel: ProviderNegotiationViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = 24.dp
    val isProvidersEmpty = remember{ mutableStateOf(false) }
    val lazyScrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    BackHandler {
        navController.popBackStack(route = P4pShowroomScreens.Enrolled.route, inclusive = false)
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
                                /*
                                navController.navigate(
                                    Screen.Account.route+"?$ACCOUNT_TYPE_KEY=${AccountType.PROVIDER.label}" +
                                            "&$PARENT_ROUTE_KEY=${Screen.ProviderNavContainer.route}"
                                ){
                                    popUpTo(P4pProviderScreens.ProviderDashboard.route)
                                }
                                 */
                            }
                        },
                        color = info_sky
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

            if(isProvidersEmpty.value){
                //TODO SVG
                OrdersEmptyStateCard(modifier = Modifier.weight(1f), imageId = R.drawable.empty_state_holder4)
            }else{
                val providersList = listOf( 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)

                LazyColumn(
                    state = lazyScrollState,
                    contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
                ){
                    items(providersList, key = {it} ){
                        FavouriteCustomersListItem(
                            modifier = Modifier
                                .clickable {
                                    scope.launch {
                                       navController.navigate(P4pProviderScreens.FavouriteCustomer.route)
                                    }
                                }
                                .padding(horizontal = containerHorizontalPadding)
                            ,
                            onclickQuote = {
                                scope.launch {
                                    /*navController.navigate(
                                        Screen.Services.route+"?$PARENT_ROUTE_KEY=${Screen.FavouriteCustomers.route}"
                                    )
                                     */
                                }
                            },
                            imageId = R.drawable.ql_cst_avtr_acc,
                            name = "Kai's Cleaning agency",
                            location = "Edinburgh",
                            showBottomLine = it != providersList.last(),
                            address = "Baird House 18, Holyrood Park Rd, Edinburgh, GB"
                        )
                    }
                }
            }
        }
    }
}