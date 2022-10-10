package eac.qloga.android.features.p4p.customer.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.IOSArrowForwardButton
import eac.qloga.android.core.shared.components.Chips.FullRoundedChip
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.components.Items.OpenRequestListItem
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.orange1

@Composable
fun OpenRequestListCard(
    modifier: Modifier = Modifier,
    cardNumber: Int,
    status: String,
    price: Double,
    placedDate: String,
    placedTime: String,
    orderedDate: String,
    orderedTime: String,
    validDate: String,
    validTime: String,
    visitCount: Int,
    searchCount: Int,
    visibleCount: Int,
    showBottomLine: Boolean = true,
    tags: List<String> = emptyList(),
    onclickArrow: () -> Unit,
    onClick: () -> Unit
) {
   Column(
       modifier = Modifier.clickable { onClick() }
   ) {
       Column(modifier = modifier
           .fillMaxWidth()
           .padding(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
       ) {
           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
           ) {
               Row {
                   Text(
                       text = "#$cardNumber",
                       style = MaterialTheme.typography.titleMedium,
                   )
                   Text(
                       text = "($status)",
                       style = MaterialTheme.typography.titleMedium,
                       color = gray30
                   )
               }
               Row(
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Text(
                       text = "£$price",
                       style = MaterialTheme.typography.titleMedium,
                       fontWeight = FontWeight.W600,
                   )
                   IOSArrowForwardButton(modifier = Modifier.padding(0.dp),onClick = {onclickArrow()})
               }
           }
           Column(modifier = Modifier.padding(end = 8.dp)) {
               OpenRequestListItem(
                   label = "Placed:",
                   extraTexts = {
                       Text(text = placedDate, style = MaterialTheme.typography.titleMedium)
                       Spacer(modifier = Modifier.width(8.dp))
                       Text(text = placedTime, style = MaterialTheme.typography.titleMedium)
                   }
               )
               OpenRequestListItem(
                   label = "Ordered:",
                   extraTexts = {
                       Text(text = orderedDate, style = MaterialTheme.typography.titleMedium)
                       Spacer(modifier = Modifier.width(8.dp))
                       Text(text = orderedTime, style = MaterialTheme.typography.titleMedium)
                   }
               )
               OpenRequestListItem(
                   label = "Valid until:",
                   showBottomLine = false,
                   extraTexts = {
                       Text(text = validDate, style = MaterialTheme.typography.titleMedium, color = orange1)
                       Spacer(modifier = Modifier.width(8.dp))
                       Text(text = validTime, style = MaterialTheme.typography.titleMedium, color = orange1)
                   }
               )
               Spacer(modifier = Modifier.height(8.dp))
               FlowRow {
                   tags.forEach { label ->
                       FullRoundedChip(label = label, countColor = orange1)
                   }
               }
               OpenRequestListItem(
                   label = "$visitCount Visit",
                   showBottomLine = false,
                   extraTexts = {
                       Row(
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Icon(
                               modifier = Modifier.size(16.dp),
                               painter = painterResource(id = R.drawable.ic_ql_search),
                               contentDescription = null,
                               tint = info_sky
                           )
                           Text(text = "$searchCount", style = MaterialTheme.typography.titleMedium)
                       }
                       Spacer(modifier = Modifier.width(8.dp))
                       Row(
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Icon(
                               modifier = Modifier.size(16.dp),
                               painter = painterResource(id = R.drawable.ic_ql_eye),
                               contentDescription = null,
                               tint = info_sky
                           )
                           Text(text = "$visibleCount", style = MaterialTheme.typography.titleMedium)
                       }
                   }
               )
           }
       }
       if(showBottomLine) DividerLine()
   }
}

@Preview(showBackground = true)
@Composable
fun PreviewOpenRequestCard() {
    OpenRequestListCard(
        cardNumber = 101,
        status = "Live",
        price = 84.00,
        placedDate = "22/06/2022",
        placedTime = "09:00",
        orderedDate = "22/04/2022",
        orderedTime = "10:10",
        validDate = "31/04/2022",
        validTime = "08:00",
        searchCount = 1,
        visibleCount = 10,
        visitCount = 1,
        tags = listOf("Cleaning", "Pets", "Care"),
        onclickArrow = {},
        onClick = {}
    )
}