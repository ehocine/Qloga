package eac.qloga.android.features.p4p.showroom.shared.components

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
import androidx.compose.ui.unit.sp
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.p4p.shared.utils.VisitInfo

@Composable
fun VisitDayListItem(
    title: String,
    daySign : String,
    showDivider: Boolean = true,
    visitInfo: VisitInfo,
    onExpand : () -> Unit
){
    Column {
        if(showDivider){
            DividerLine(Modifier.padding(start = 28.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpand() }
                .padding(14.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400
                    )
                )

                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = daySign,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                ){
                if(!visitInfo.time.isNullOrEmpty()){
                    Text(
                        modifier = Modifier
                            .alpha(.75f)
                            .padding(end = 8.dp),
                        text = if(visitInfo.time.size < 2) "${visitInfo.time[0].start} - ${visitInfo.time[0].end}" else "${visitInfo.time.size} Visits",
                        style = MaterialTheme.typography.bodySmall,
                        color = gray30
                    )
                }

                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                    ,
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "forward arrow",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewVisitDayListItem() {
    Box(modifier = Modifier.fillMaxSize()) {
        VisitDayListItem(
            title = "07/10/2022 ",
            showDivider = true,
            daySign = "M",
            visitInfo = VisitInfo("22/02/2022", "Tuesday"),
            onExpand = {}
        )
    }
}