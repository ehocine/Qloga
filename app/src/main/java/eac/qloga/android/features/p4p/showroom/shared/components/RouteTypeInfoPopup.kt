package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.utils.Extensions.advancedShadow
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.orange1

@Composable
fun RouteTypeInfoPopup(
    modifier: Modifier = Modifier
) {
    val roundedCorner = 24.dp
    val iconWidth = 40.dp
    val info1 = "When you select one of the route types, it will be set from your current location"
    val info2 = "Range of mark arrive (if your GPS coordinates are not accurate)"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .advancedShadow(color = gray1, alpha = .8f,24.dp,24.dp)
            .clip(RoundedCornerShape(roundedCorner))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.width(iconWidth),
                painter = painterResource(id = R.drawable.ic_ql_cycle_walk),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = info1,
                style = MaterialTheme.typography.titleMedium,
                color = gray30
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.width(iconWidth),
                painter = painterResource(id = R.drawable.ic_ql_range),
                contentDescription = null,
                tint = orange1
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = info2,
                style = MaterialTheme.typography.titleMedium,
                color = gray30
            )
        }
    }
}