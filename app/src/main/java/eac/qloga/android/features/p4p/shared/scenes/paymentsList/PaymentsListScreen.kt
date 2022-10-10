package eac.qloga.android.features.p4p.shared.scenes.paymentsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.InfoButton
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.Items.PaymentsListItem
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.components.VerticalDottedLine
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsListScreen(
    navController: NavController,
) {
    val horizontalContentPadding = Dimensions.ScreenHorizontalPadding.dp
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.PaymentsList.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                onBackPress = { navController.navigateUp() },
                actions = { InfoButton(onClick = { /*TODO*/ }, color = orange1)}
            )
        }
    ) { paddingValues ->
        val titleBarHeight = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = horizontalContentPadding)
            ,
        ) {
            //space for title bar
            Spacer(modifier = Modifier.height(titleBarHeight))

            VerticalDottedLine()
            ContainerBorderedCard {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
//                            scope.launch {
//                                navController.navigate(
//                                    Screen.Tracking.route+"?$PARENT_ROUTE_KEY=${Screen.PaymentsList.route}"
//                                ){
//                                    popUpTo(Screen.PaymentsList.route){
//                                        inclusive = true
//                                    }
//                                }
//                            }
                        }
                ) {
                    PaymentsListItem(label = "Date & Time", value = "22/06/2022 22:00")
                    PaymentsListItem(label = "Status payment", value = "Authorised")
                    PaymentsListItem(label = "Details", value = "Deposit: Callout charge")
                    PaymentsListItem(label = "Amount", value = "£84.00")
                }
            }
            VerticalDottedLine()
            ContainerBorderedCard {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PaymentsListItem(label = "Date & Time", value = "22/06/2022 22:00")
                    PaymentsListItem(label = "Status payment", value = "Payment request")
                    PaymentsListItem(label = "Details", value = "Remaining amount(2 hours)")
                    PaymentsListItem(label = "Amount", value = "£84.00")
                }
            }
            VerticalDottedLine()
            ContainerBorderedCard {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PaymentsListItem(label = "Date & Time", value = "22/06/2022 22:00")
                    PaymentsListItem(label = "Status payment", value = "Payment request")
                    PaymentsListItem(label = "Details", value = "Deposit: Callout charge")
                    PaymentsListItem(label = "Amount", value = "£84.00")
                }
            }
            VerticalDottedLine()
        }
    }
}