package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun DayWorkingHourItem(
    modifier: Modifier = Modifier,
    day: String = "",
    from: String,
    to: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.widthIn(min = 40.dp),
            text = day,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .widthIn(min = 44.dp)
                    .padding(end = 16.dp),
                text = "from",
                style = MaterialTheme.typography.titleMedium,
                color = gray30
            )
            Text(
                modifier = Modifier.widthIn(min = 54.dp),
                text = from,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.widthIn(min = 32.dp).padding(end = 16.dp),
                text = "to",
                style = MaterialTheme.typography.titleMedium,
                color = gray30
            )
            Text(
                modifier = Modifier.widthIn(min = 54.dp),
                text = to,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDayWorkingHourItem() {
    DayWorkingHourItem(
        day = "FRI",
        from = "7:30",
        to = "8:30"
    )
}