package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun TimeOffItem(
    modifier: Modifier = Modifier,
    type: String ,
    date: String,
    time: String,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.fillMaxWidth().padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.widthIn(min = 54.dp),
                text = type,
                style = MaterialTheme.typography.titleMedium,
                color = gray30
            )
            Text(text = date, style = MaterialTheme.typography.titleLarge)
            Text(text = time, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimeOffItem() {
    TimeOffItem(
        type = "from",
        date = "3/12/2022",
        time = "8:30",
    )
}