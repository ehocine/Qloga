package eac.qloga.android.features.p4p.provider.scenes.quotesTab

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.features.p4p.shared.components.OrdersEmptyStateCard
import eac.qloga.android.features.p4p.shared.components.QuoteCard
import kotlinx.coroutines.launch

@Composable
fun ProviderQuotesTabScreen(
    isQuotesEmpty: Boolean,
    navController: NavController
) {
    val containerHorizontalPadding = 24.dp
    val scope = rememberCoroutineScope()

    if(isQuotesEmpty){
        OrdersEmptyStateCard(modifier = Modifier.fillMaxSize(), imageId = R.drawable.empty_state_holder8)
    }else{
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(containerHorizontalPadding)
        ){
            QuoteCard(
                onClickLocationMarker = {
                    scope.launch {
                        // navController.navigate(Screen.ShowLocationMapView.route)
                    }
                },
                showMapMarker = true,
                onClick = {
                    // navController.navigate(Screen.Quote.route)
                },
                showFirstLastVisit = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuoteCard(
                onClickLocationMarker = {
                    scope.launch {
                        // navController.navigate(Screen.ShowLocationMapView.route)
                    }
                },
                showMapMarker = true,
                onClick = {
                    // navController.navigate(Screen.Quote.route)
                },
                showFirstLastVisit = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuoteCard(
                onClickLocationMarker = {
                    scope.launch {
                        // navController.navigate(Screen.ShowLocationMapView.route)
                    }
                },
                showMapMarker = true,
                onClick = {
                    // navController.navigate(Screen.Quote.route)
                },
                showFirstLastVisit = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuoteCard(
                onClickLocationMarker = {
                    scope.launch {
                        // navController.navigate(Screen.ShowLocationMapView.route)
                    }
                },
                showMapMarker = true,
                onClick = {
                    // navController.navigate(Screen.Quote.route)
                },
                showFirstLastVisit = true
            )
        }
    }
}