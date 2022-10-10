package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun SelectedServicesItem(
    title : String ,
    label: String,
    count: Int,
    showDivider: Boolean = true,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column( horizontalAlignment = Alignment.Start ){
                Text(
                    modifier = Modifier,
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    color = gray30
                )
            }

            Row( verticalAlignment = Alignment.CenterVertically ){
                Text(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .alpha(.75f),
                    text = "$count",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W400,
                    color = gray30
                )

                Box(modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onClick() }
                    .padding(2.dp)
                ){
                    Icon(
                        modifier = Modifier
                            .size(18.dp),
                        imageVector = Icons.Rounded.ArrowForwardIos,
                        contentDescription = "forward arrow",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        if(showDivider){
            DividerLine(Modifier.padding(start = 64.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopCardItem(){
    SelectedServicesItem(title = "Windows cleaning", label = "Rate ($/hour): 21.00", count = 4, onClick = {})
}
