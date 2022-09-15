package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import eac.qloga.android.core.shared.components.Buttons.IOSArrowForwardButton
import eac.qloga.android.core.shared.components.Chips.FullRoundedChip
import eac.qloga.android.core.shared.components.LocationMarkerIconButton
import eac.qloga.android.core.shared.theme.*

@Composable
fun AcceptedCard(
    modifier: Modifier = Modifier,
    showMapMarker: Boolean = false,
    showFirstLastVisit: Boolean,
    onClickLocationMarker:() -> Unit = {},
    onClickCard: () -> Unit,
    onClickArrow: () -> Unit,
) {
    val visitCount = 5
    val price = 84.00
    val title = "Accepted"
    val firstVisitDate = "22/06/2022"
    val lastVisitDate = "22/06/2022"
    val firstVisitTime = "10:00"
    val lastVisitTime = "09:00"
    val address = "30 Cloth Market, Merchant House, Newcastle upon Tyne, GB"
    val services = mapOf("Cleaning" to 4, "Pets" to 0, "Care" to 5)
    val gradientColors = listOf(gradientLightGreen1, gradientLightGreen2)
    val cardCornerRadius = 16.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cardCornerRadius))
            .clickable { onClickCard() }
            .border(1.dp, gray1.copy(.5f), RoundedCornerShape(cardCornerRadius))
    ) {
        InternalCardOne(
            title = title,
            visitCount = visitCount,
            price = "$price",
            firstVisitDate = firstVisitDate,
            firstVisitTime = firstVisitTime,
            lastVisitDate = lastVisitDate,
            lastVisitTime = lastVisitTime,
            address = address,
            showMapMarker = showMapMarker,
            showFirstLastVisit = showFirstLastVisit,
            gradientColors = gradientColors,
            services = services,
            onClickArrow = {onClickArrow()},
            onClickLocationMarker = { onClickLocationMarker() }
        )
    }
}

@Composable
fun DeclinedCard(
    modifier: Modifier = Modifier,
    showMapMarker: Boolean = false ,
    showFirstLastVisit: Boolean,
    onClickLocationMarker: () -> Unit = {},
    onClickCard: () -> Unit,
    onClickArrow: () -> Unit,
) {
    val visitCount = 5
    val price = 84.00
    val title = "Declined"
    val firstVisitDate = "22/06/2022"
    val lastVisitDate = "22/06/2022"
    val firstVisitTime = "10:00"
    val lastVisitTime = "09:00"
    val address = "30 Cloth Market, Merchant House, Newcastle upon Tyne, GB"
    val services = mapOf("Cleaning" to 4, "Pets" to 0, "Care" to 5)
    val gradientColors = listOf(gradientLightRed1, gradientLightRed2)
    val cardCornerRadius = 16.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cardCornerRadius))
            .clickable{ onClickCard() }
            .border(1.dp, gray1.copy(.5f), RoundedCornerShape(cardCornerRadius))
    ) {
        InternalCardOne(
            title = title,
            visitCount = visitCount,
            price = "$price",
            firstVisitDate = firstVisitDate,
            firstVisitTime = firstVisitTime,
            lastVisitDate = lastVisitDate,
            lastVisitTime = lastVisitTime,
            address = address,
            showMapMarker = showMapMarker,
            gradientColors = gradientColors,
            services = services,
            showFirstLastVisit = showFirstLastVisit,
            onClickArrow = { onClickArrow() },
            onClickLocationMarker = { onClickLocationMarker() }
        )
    }
}

@Composable
fun InquiryCard(
    modifier: Modifier = Modifier,
    showMapMarker: Boolean = false,
    showFirstLastVisit: Boolean,
    onClickLocationMarker: () -> Unit = {},
    onClickCard: () -> Unit,
) {
    val visitCount = 5
    val price = 84.00
    val title = "Inquiry"
    val firstVisitDate = "22/06/2022"
    val lastVisitDate = "22/06/2022"
    val firstVisitTime = "10:00"
    val lastVisitTime = "09:00"
    val address = "30 Cloth Market, Merchant House, Newcastle upon Tyne, GB"
    val services = mapOf("Cleaning" to 4, "Pets" to 0, "Care" to 5)
    val gradientColors = listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.background)
    val cardCornerRadius = 16.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cardCornerRadius))
            .clickable { onClickCard() }
            .border(1.dp, gray1.copy(.5f), RoundedCornerShape(cardCornerRadius))
    ) {
        InternalCardOne(
            title = title,
            visitCount = visitCount,
            price = "$price",
            firstVisitDate = firstVisitDate,
            firstVisitTime = firstVisitTime,
            lastVisitDate = lastVisitDate,
            lastVisitTime = lastVisitTime,
            address = address,
            showFirstLastVisit = showFirstLastVisit,
            showMapMarker = showMapMarker,
            gradientColors = gradientColors,
            services = services,
            onClickArrow = { onClickCard() },
            onClickLocationMarker = { onClickLocationMarker() }
        )
    }
}

@Composable
fun QuoteCard(
    modifier: Modifier = Modifier,
    showMapMarker: Boolean = false,
    showFirstLastVisit: Boolean,
    onClickLocationMarker: () -> Unit = {},
    onClick: () -> Unit,
) {
    val visitCount = 5
    val price = 84.00
    val title = "Quote"
    val firstVisitDate = "22/06/2022"
    val lastVisitDate = "22/06/2022"
    val firstVisitTime = "10:00"
    val lastVisitTime = "09:00"
    val address = "30 Cloth Market, Merchant House, Newcastle upon Tyne, GB"
    val services = mapOf("Cleaning" to 4, "Pets" to 0, "Care" to 5)
    val gradientColors = listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.background)
    val cardCornerRadius = 16.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cardCornerRadius))
            .clickable { onClick() }
            .border(1.dp, gray1.copy(.5f), RoundedCornerShape(cardCornerRadius))
    ) {
        InternalCardOne(
            title = title,
            visitCount = visitCount,
            price = "$price",
            firstVisitDate = firstVisitDate,
            firstVisitTime = firstVisitTime,
            lastVisitDate = lastVisitDate,
            lastVisitTime = lastVisitTime,
            address = address,
            showFirstLastVisit = showFirstLastVisit,
            showMapMarker = showMapMarker,
            gradientColors = gradientColors,
            services = services,
            onClickArrow = { onClick() },
            onClickLocationMarker = { onClickLocationMarker() }
        )
    }
}

@Composable
fun FundCard(
    modifier: Modifier = Modifier,
    showMapMarker: Boolean = false,
    showFirstLastVisit: Boolean,
    onClickLocationMarker: () -> Unit = {},
    onClickCard: () -> Unit,
    onClickArrow: () -> Unit
) {
    val price = 84.00
    val title = "Fund reservation is needed"
    val date = "22/06/2022"
    val time = "10:00"
    val address = "30 Cloth Market, Merchant House, Newcastle upon Tyne, GB"
    val services = mapOf("Hair" to 4, "Pets" to 0)
    val gradientColors = listOf(gradientLightOrange1, gradientLightOrange2)
    val cardCornerRadius = 16.dp
    val verticalPadding = 8.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cardCornerRadius))
            .clickable { onClickCard() }
            .border(1.dp, gray1.copy(.5f), RoundedCornerShape(cardCornerRadius))
            .padding(bottom = verticalPadding)
    ) {
        InternalCardOne(
            title = title,
            price = "$price",
            date = date,
            time = time,
            showMapMarker = showMapMarker,
            address = address,
            gradientColors = gradientColors,
            showFirstLastVisit = showFirstLastVisit,
            services = services,
            onClickArrow = { onClickArrow() },
            onClickLocationMarker = { onClickLocationMarker() }
        )
    }
}

@Composable
private fun InternalCardOne(
    modifier: Modifier = Modifier,
    title: String,
    visitCount: Int = 0,
    price: String,
    firstVisitDate: String = "",
    firstVisitTime: String = "",
    lastVisitDate: String = "",
    lastVisitTime: String = "",
    showFirstLastVisit: Boolean,
    address: String,
    date: String = "",
    time: String = "",
    gradientColors: List<Color>,
    services: Map<String, Int>,
    showMapMarker: Boolean = false,
    onClickLocationMarker: () -> Unit = {},
    onClickArrow: () -> Unit,
) {
    Column {
        Row(modifier = modifier
            .fillMaxWidth()
            .background(Brush.linearGradient(gradientColors))
            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            IOSArrowForwardButton(color = MaterialTheme.colorScheme.onBackground,onClick = { onClickArrow() })
        }
        Divider(color = gray1.copy(.5f), thickness = 1.dp)
    }

    if(showFirstLastVisit){
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$visitCount Visits",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600,
                color = gray30.copy(.65f)
            )
            Text(
                text = "£$price",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "First visit:",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "$firstVisitDate  $firstVisitTime",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "Last visit:",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "$lastVisitDate  $lastVisitTime",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }else{
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(end = 32.dp),
                text = "$visitCount Visits",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600,
                color = gray30
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "£$price",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.width(8.dp))
                //IOSArrowForwardButton( onClick = { onClickArrow() })
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }

    Row(
        modifier = Modifier.padding(start  = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if(showMapMarker){
            LocationMarkerIconButton(onClick = { onClickLocationMarker() })
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = address,
            style = MaterialTheme.typography.titleMedium,
            color = gray30
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    FlowRow(modifier = Modifier.padding(horizontal = 16.dp)) {
        services.forEach { (label, count) ->
            FullRoundedChip(label = label, count = count, countColor = orange1)
        }
    }
    Spacer(Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun PreviewDeclinedCard() {
    DeclinedCard(
        onClickArrow = {},
        showMapMarker = true,
        showFirstLastVisit = true,
        onClickLocationMarker = {},
        onClickCard = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewInquiryCard() {
    InquiryCard(onClickCard = {}, showMapMarker = true, showFirstLastVisit = true)
}

@Preview(showBackground = true)
@Composable
fun PreviewFundCard() {
    FundCard(
        onClickArrow = {},
        showMapMarker = false,
        showFirstLastVisit = false,
        onClickCard = {},
        onClickLocationMarker = {}
    )
}