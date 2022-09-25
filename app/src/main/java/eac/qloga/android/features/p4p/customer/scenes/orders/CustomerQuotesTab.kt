package eac.qloga.android.features.p4p.customer.scenes.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.features.p4p.shared.components.QuoteCard

@Composable
fun CustomerQuotesTab(
    isQuotesEmpty: Boolean,
    navController: NavController
) {
    val containerHorizontalPadding = 24.dp
    val scrollState = rememberScrollState()

    Box {
        if(isQuotesEmpty){
            OrdersEmptyStateCard(modifier = Modifier.fillMaxSize(), imageId = R.drawable.ic_openreq2)
        }else{
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(containerHorizontalPadding)
            ){
                QuoteCard(
                    onClick = {
                        // navController.navigate(Screen.Quote.route)
                              },
                    showFirstLastVisit = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                QuoteCard(
                    onClick = {
                        //navController.navigate(Screen.Quote.route)
                              },
                    showFirstLastVisit = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                QuoteCard(
                    onClick = {
                        //navController.navigate(Screen.Quote.route)
                              },
                    showFirstLastVisit = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                QuoteCard(
                    onClick = {
                        //navController.navigate(Screen.Quote.route)
                              },
                    showFirstLastVisit = true
                )
            }
        }
    }
}