package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun OrderStatusButton(
    modifier: Modifier = Modifier,
    label: String
) {
    Cards.ContainerBorderedCard(
        cornerRadius = 12.dp
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
            ,
        ) {
            Text(text = label, style = MaterialTheme.typography.labelLarge, color = gray1)
        }
    }
}