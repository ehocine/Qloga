package eac.qloga.android.features.p4p.provider.scenes.inquiresTab

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.features.p4p.shared.components.InquiryCard
import eac.qloga.android.features.p4p.shared.components.OrdersEmptyStateCard
import kotlinx.coroutines.launch

@Composable
fun ProviderInquiresTabScreen(
    isInquiresEmpty: Boolean,
    navController: NavController
) {
    val containerHorizontalPadding = 24.dp
    val scope = rememberCoroutineScope()

    if(isInquiresEmpty){
        OrdersEmptyStateCard(modifier = Modifier.fillMaxSize(), imageId = R.drawable.empty_state_holder5)
    }else{
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(containerHorizontalPadding)
        ){
            InquiryCard(
                onClickLocationMarker = {
                    scope.launch {
                        // navController.navigate(Screen.ShowLocationMapView.route)
                    }
                },
                showMapMarker = true,
                onClickCard = {
                    // navController.navigate(Screen.Inquiry.route)
                },
                showFirstLastVisit = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            InquiryCard(
                onClickLocationMarker = {
                    scope.launch {
                        // navController.navigate(Screen.ShowLocationMapView.route)
                    }
                },
                showMapMarker = true,
                onClickCard = {
                    // navController.navigate(Screen.Inquiry.route)
                },
                showFirstLastVisit = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            InquiryCard(
                onClickLocationMarker = {
                    scope.launch {
                        // navController.navigate(Screen.ShowLocationMapView.route)
                    }
                },
                showMapMarker = true,
                onClickCard = {
                    // navController.navigate(Screen.Inquiry.route)
                },
                showFirstLastVisit = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            InquiryCard(
                onClickLocationMarker = {
                    scope.launch {
                        // navController.navigate(Screen.ShowLocationMapView.route)
                    }
                },
                showMapMarker = true,
                onClickCard = {
                    // navController.navigate(Screen.Inquiry.route)
                },
                showFirstLastVisit = true
            )
        }
    }
}