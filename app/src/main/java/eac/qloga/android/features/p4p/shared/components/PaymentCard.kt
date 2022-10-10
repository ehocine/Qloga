package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.p4p.shared.utils.OrderPaymentCategory

@Composable
fun PaymentCard(
    modifier: Modifier = Modifier,
    type: OrderPaymentCategory,
    date: String,
    time: String,
    payerName: String,
    masterCardNo: String,
    amount: Double,
    onClickCard: () -> Unit
) {
    val labelColor = if(type == OrderPaymentCategory.AUTHORIZED) MaterialTheme.colorScheme.primary else Red10

    ContainerBorderedCard {
        Column(
            modifier = modifier
                .clickable { onClickCard() }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = type.label, color = labelColor, style = MaterialTheme.typography.titleMedium)
                Text(text = "$date $time", color = gray30, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Payer:", style = MaterialTheme.typography.titleMedium)
                Text(text = payerName, color = gray30, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Mastercard:", style = MaterialTheme.typography.titleMedium)
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Top),
                        text = "*** *** *** ",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )
                    Text(text = masterCardNo, color = gray30, style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Amount:", style = MaterialTheme.typography.titleMedium)
                Text(text = "£$amount", color = gray30, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}