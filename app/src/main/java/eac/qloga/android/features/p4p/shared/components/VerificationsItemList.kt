package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun VerificationsItemList(
    title: String,
    value: String
){
    val rightTextWidth = 110.dp
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(14.dp),
                imageVector = Icons.Rounded.Check,
                contentDescription = " ",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            modifier = Modifier
                .width(rightTextWidth)
                .padding(start = 8.dp, end = 12.dp),
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Visible,
            maxLines = 2
        )

        Text(
            modifier = Modifier
                .weight(1f)
            ,
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = gray30
        )
    }
}
