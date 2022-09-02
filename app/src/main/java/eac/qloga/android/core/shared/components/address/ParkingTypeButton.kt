package eac.qloga.android.core.shared.components.address

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.grayTextColor

@Composable
fun ParkingTypeButton(
    type: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = type,
            style = MaterialTheme.typography.bodySmall,
            color = grayTextColor
        )
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Rounded.ArrowForwardIos,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}