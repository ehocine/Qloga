package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.purple10
import eac.qloga.android.features.p4p.shared.components.BottomSheetDashLine

@Composable
fun TrackingActorsStatus() {
    val circleSize = 24.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomSheetDashLine()
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Actors", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .size(circleSize)
                    .background(purple10)
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "QLOGA",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .size(circleSize)
                    .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Customer",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .size(circleSize)
                    .background(info_sky)
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Provider",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}