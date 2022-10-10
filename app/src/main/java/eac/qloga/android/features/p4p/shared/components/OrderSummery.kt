package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.LocationMarkerIconButton
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun OrderSummery(
    modifier: Modifier = Modifier,
    date: String,
    time: String,
    address: String,
    price: Double,
    onClickLocationMarker: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = date, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = time, style = MaterialTheme.typography.titleMedium)
            }
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "£$price",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = address,
                style = MaterialTheme.typography.titleMedium,
                color = gray30
            )
            Spacer(modifier = Modifier.width(8.dp))
            LocationMarkerIconButton(onClick = { onClickLocationMarker() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderSummery() {
    OrderSummery(
        date = "20/12/2022",
        time = "09:00",
        price = 95.00,
        address = "30 Cloth Market, Merchant House, Newcastle upon Tyne, GB",
        onClickLocationMarker = {}
    )
}