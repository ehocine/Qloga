package eac.qloga.android.features.p4p.customer.scenes.orders

import P4pCustomerScreens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.features.p4p.shared.components.AcceptedCard
import eac.qloga.android.features.p4p.shared.components.DeclinedCard
import eac.qloga.android.features.p4p.shared.components.FundCard

@Composable
fun CustomerOrdersTab(
    isOrdersEmpty: Boolean,
    navController: NavController
) {
    val containerHorizontalPadding = 24.dp
    val scrollState = rememberScrollState()

    Box {
        if(isOrdersEmpty){
            //TODO svg file not right
            //OrdersEmptyStateCard(modifier = Modifier.weight(1f), imageId = R.drawable.ic_group_12sssvgiso)
        }else{
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(containerHorizontalPadding)
            ){
                DeclinedCard(
                    showMapMarker = false,
                    showFirstLastVisit = true,
                    onClickCard = {
                        navController.navigate(P4pCustomerScreens.CustomerOrder.route)
                    }
                ){
                    navController.navigate(P4pCustomerScreens.CustomerOrder.route)
                }
                Spacer(modifier = Modifier.height(16.dp))
                AcceptedCard(
                    showMapMarker = false,
                    showFirstLastVisit = true,
                    onClickCard = {
                        navController.navigate(P4pCustomerScreens.CustomerOrder.route)
                    }
                ){
                    navController.navigate(P4pCustomerScreens.CustomerOrder.route)
                }
                Spacer(modifier = Modifier.height(16.dp))
                FundCard(
                    showMapMarker = false,
                    showFirstLastVisit = false,
                    onClickCard = {
                        navController.navigate(P4pCustomerScreens.CustomerOrder.route)
                    }
                ){
                    navController.navigate(P4pCustomerScreens.CustomerOrder.route)
                }
            }
        }
    }
}