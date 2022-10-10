package eac.qloga.android.features.p4p.shared.scenes.orderPayment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.features.p4p.shared.components.PaymentCard
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.OrderPaymentCategory
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderPaymentScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val containerTopPadding = 16.dp
    val containerHorizontalPadding = 24.dp
    val interactionSource = MutableInteractionSource()

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.OrderPayment.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = containerHorizontalPadding)
            ) {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))
                PaymentCard(
                    type = OrderPaymentCategory.AUTHORIZED,
                    date = "12/03/2022",
                    time = "03:09",
                    payerName = "Amy Strokes",
                    masterCardNo = "9322",
                    amount = 9999.9,
                    onClickCard = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                PaymentCard(
                    type = OrderPaymentCategory.UNAUTHORIZED,
                    date = "12/03/2022",
                    time = "03:09",
                    payerName = "Amy Strokes",
                    masterCardNo = "8342",
                    amount = 9999.9,
                    onClickCard = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                PaymentCard(
                    type = OrderPaymentCategory.UNAUTHORIZED,
                    date = "12/03/2022",
                    time = "03:09",
                    payerName = "Amy Strokes",
                    masterCardNo = "7342",
                    amount = 9999.9,
                    onClickCard = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
//                            navController.navigate(Screen.TermsConditions.route){
//                                launchSingleTop = true
//                            }
                        },
                        text = "Terms & Conditions",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}