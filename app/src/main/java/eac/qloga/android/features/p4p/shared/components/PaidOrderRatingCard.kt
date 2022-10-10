package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.features.p4p.showroom.shared.components.RatingFiveStar

@Composable
fun PaidOrderRatingCard(
    modifier: Modifier = Modifier,
    onClickInfo: () -> Unit
) {
    val startDividerPadding = 64.dp
    val starSize = 20.dp
    val communicationRating = remember{ mutableStateOf(4) }
    val timelyArrivedRating = remember{ mutableStateOf(3) }
    val qualityOfServiceRating = remember{ mutableStateOf(4) }
    val friendlinessRating = remember{ mutableStateOf(5) }
    val performanceRating = remember{ mutableStateOf(4) }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal =16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = "RATING", style = MaterialTheme.typography.titleMedium)
            Box(modifier = Modifier
                .clip(CircleShape)
                .clickable { onClickInfo() }
                .padding(4.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = null,
                    tint = orange1
                )
            }
        }

        Cards.ContainerBorderedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RatingFiveStar(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    label = "Communications",
                    ratings = communicationRating.value,
                    textStyle = MaterialTheme.typography.titleMedium,
                    starColor = MaterialTheme.colorScheme.primary,
//                    onClickStar = { communicationRating.value = it },
                )
                DividerLines.LightDividerLine(Modifier.padding(start = startDividerPadding))
                RatingFiveStar(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    label = "Timely arrival",
                    ratings = timelyArrivedRating.value,
                    textStyle = MaterialTheme.typography.titleMedium,
                    starColor = MaterialTheme.colorScheme.primary,
//                    onClickStar = { timelyArrivedRating.value = it },
                    )
                DividerLines.LightDividerLine(Modifier.padding(start = startDividerPadding))
                RatingFiveStar(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    label = "Quality of service",
                    ratings = qualityOfServiceRating.value,
                    textStyle = MaterialTheme.typography.titleMedium,
                    starColor = MaterialTheme.colorScheme.primary,
//                    onClickStar = { qualityOfServiceRating.value = it },
                    )
                DividerLines.LightDividerLine(Modifier.padding(start = startDividerPadding))
                RatingFiveStar(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    label = "Friendliness",
                    ratings = friendlinessRating.value,
                    textStyle = MaterialTheme.typography.titleMedium,
                    starColor = MaterialTheme.colorScheme.primary,
//                    onClickStar = { friendlinessRating.value = it },
                    )
                DividerLines.LightDividerLine(Modifier.padding(start = startDividerPadding))
                RatingFiveStar(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    label = "Performance & effectiveness",
                    ratings = performanceRating.value,
                    textStyle = MaterialTheme.typography.titleMedium,
                    starColor = MaterialTheme.colorScheme.primary,
//                    onClickStar = { performanceRating.value = it }
                )
            }
        }
    }
}