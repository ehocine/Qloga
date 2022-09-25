package eac.qloga.android.features.p4p.customer.scenes.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.features.p4p.shared.components.InquiryCard

@Composable
fun CustomerInquiresTab(
    isInquiresEmpty: Boolean,
    navController: NavController
) {
    val containerHorizontalPadding = 24.dp
    val scrollState = rememberScrollState()

    Box {
        if(isInquiresEmpty){
            OrdersEmptyStateCard(modifier = Modifier.fillMaxSize(), imageId = R.drawable.empty_state_holder1)
        }else{
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(containerHorizontalPadding)
            ){
                InquiryCard(
                    onClickCard = {
                        //navController.navigate(Screen.Inquiry.route)
                        },
                    showFirstLastVisit = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                InquiryCard(
                    onClickCard = {
                        //navController.navigate(Screen.Inquiry.route)
                    },
                    showFirstLastVisit = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                InquiryCard(
                    onClickCard = {
                        //navController.navigate(Screen.Inquiry.route)
                                  },
                    showFirstLastVisit = true
                )
            }
        }
    }
}