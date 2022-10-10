package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.features.p4p.showroom.shared.components.RatingFiveStar

@Composable
fun ClosedOrderRatingCard(
    modifier: Modifier = Modifier,
    communicationRating: Int,
    timelyArrived: Int,
    qualityOfService: Int,
    friendliness: Int,
    performance: Int,
) {
    val starSize = 20.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        RatingFiveStar(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            label = "Communications",
            ratings = communicationRating,
            textStyle = MaterialTheme.typography.titleMedium,
        )
        RatingFiveStar(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            label = "Timely arrival",
            ratings = timelyArrived,
            textStyle = MaterialTheme.typography.titleMedium,
        )
        RatingFiveStar(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            label = "Quality of service",
            ratings = qualityOfService,
            textStyle = MaterialTheme.typography.titleMedium,
        )
        RatingFiveStar(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            label = "Friendliness",
            ratings = friendliness,
            textStyle = MaterialTheme.typography.titleMedium,
        )
        RatingFiveStar(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            label = "Performance & effectiveness",
            ratings = performance,
            textStyle = MaterialTheme.typography.titleMedium,
        )
    }
}