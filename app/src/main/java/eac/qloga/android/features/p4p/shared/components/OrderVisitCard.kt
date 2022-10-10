package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.orange1

@Composable
fun OrderVisitCard(
    modifier: Modifier = Modifier,
    date: String,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
            text = date,
            style = MaterialTheme.typography.titleLarge,
        )
        ContainerBorderedCard(borderColor = info_sky) {
            Column {
                Item(
                    timeFrom = "11:00",
                    timeTo = "12:00",
                    onComplete = {},
                    onCancel = {},
                    onMarkArriveGps = {}
                )
                Item(
                    timeFrom = "11:00",
                    timeTo = "12:00",
                    showBottomDividerLine = false,
                    onComplete = {},
                    onCancel = {},
                    onMarkArriveGps = {}
                )
            }
        }
    }
}

@Composable
private fun Item(
    modifier: Modifier = Modifier,
    timeFrom: String,
    timeTo: String,
    showBottomDividerLine: Boolean = true,
    onCancel: () -> Unit,
    onComplete: () -> Unit,
    onMarkArriveGps: () -> Unit
) {
    val buttonHeight = 40.dp

    Column {
        Column(modifier = modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "$timeFrom - $timeTo",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "N/A",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .height(buttonHeight)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .border(width = 1.dp, color = Red10, shape = RoundedCornerShape(8.dp))
                        .clickable { onCancel() }
                        .padding(horizontal = 16.dp)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Cancel", style = MaterialTheme.typography.titleMedium, color = Red10)
                }
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .height(buttonHeight)
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { onComplete() }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Complete", style = MaterialTheme.typography.titleMedium, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(buttonHeight)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .border(width = 1.dp, color = orange1, shape = RoundedCornerShape(8.dp))
                    .clickable { onMarkArriveGps() }
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Mark Arrive no GPS", style = MaterialTheme.typography.titleMedium, color = orange1)
            }
        }
        if(showBottomDividerLine){
            Divider(modifier= Modifier.height(2.dp),color = info_sky.copy(alpha = .3f))
        }
    }
}