package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Buttons.IOSArrowForwardButton
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.theme.gradientLightGreen1
import eac.qloga.android.core.shared.theme.gradientLightGreen2
import eac.qloga.android.features.p4p.shared.utils.OrderCategory

@Composable
fun OrderTypeStatus(
    orderCategory: OrderCategory,
    onClickItem: () -> Unit,
) {
    val backendStatusColor = if(orderCategory == OrderCategory.Completed) {
        listOf(gradientLightGreen1, gradientLightGreen2)
    } else listOf(Color.Transparent, Color.Transparent)

    ContainerBorderedCard(
        cornerRadius = 12.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickItem() }
                .background( Brush.linearGradient(backendStatusColor))
                .padding(horizontal = 8.dp, vertical = 10.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = orderCategory.label,
                style = MaterialTheme.typography.titleMedium,
                color = orderCategory.color
            )
            IOSArrowForwardButton(onClick = { onClickItem() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderTypeStatus() {
    OrderTypeStatus(
        orderCategory = OrderCategory.FundsReservation,
        onClickItem = {}
    )
}