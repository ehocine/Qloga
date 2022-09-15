package eac.qloga.android.features.p4p.provider.scenes.ordersTab

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.features.p4p.shared.components.AcceptedCard
import eac.qloga.android.features.p4p.shared.components.DeclinedCard
import eac.qloga.android.features.p4p.shared.components.FundCard
import eac.qloga.android.features.p4p.shared.components.OrdersEmptyStateCard
import kotlinx.coroutines.launch

@Composable
fun ProviderOrdersTabScreen(
    isOrdersEmpty: Boolean,
    navController: NavController
) {
    val containerHorizontalPadding = 24.dp
    val scope = rememberCoroutineScope()

    Box {
        if(isOrdersEmpty){
            OrdersEmptyStateCard(modifier = Modifier.fillMaxSize(), imageId = R.drawable.empty_state_holder7)
        }else{
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(containerHorizontalPadding)
            ){
                DeclinedCard(
                    showMapMarker = true,
                    onClickArrow = {
                        scope.launch {
                            // navController.navigate(Screen.ProviderOrder.route)
                        }
                    },
                    showFirstLastVisit = true,
                    onClickCard = {
                        // navController.navigate(Screen.ProviderOrder.route)
                    },
                    onClickLocationMarker = {
                        scope.launch {
                            // navController.navigate(Screen.ShowLocationMapView.route)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                AcceptedCard(
                    showMapMarker = true,
                    onClickArrow = {
                        scope.launch {
                           // navController.navigate(Screen.ProviderOrder.route)
                        }
                    },
                    onClickCard = {
                        // navController.navigate(Screen.ProviderOrder.route)
                    },
                    showFirstLastVisit = true,
                    onClickLocationMarker = {
                        scope.launch {
                            // navController.navigate(Screen.ShowLocationMapView.route)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                FundCard(
                    showMapMarker = true,
                    onClickArrow = {
                        scope.launch {
                            // navController.navigate(Screen.ProviderOrder.route)
                        }
                    },
                    onClickCard = {
                        // navController.navigate(Screen.ProviderOrder.route)
                    },
                    showFirstLastVisit = false,
                    onClickLocationMarker = {
                        scope.launch {
                            // navController.navigate(Screen.ShowLocationMapView.route)
                        }
                    }
                )
            }
        }
    }
}