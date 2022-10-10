package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DottedLine
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun PaymentTrackingItem(
    modifier: Modifier = Modifier,
    idColor: Color,
    title: String,
    label: String,
    price: Double,
    date: String,
    time: String
) {
    val circleSize = 32.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(circleSize)
                    .background(idColor)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_ql_check_mark),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            DottedLine(modifier = Modifier.width(circleSize))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = idColor,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 4.dp),
                    text = "$date $time",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.W600,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    color = gray30
                )
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = "£$price",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W600
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}