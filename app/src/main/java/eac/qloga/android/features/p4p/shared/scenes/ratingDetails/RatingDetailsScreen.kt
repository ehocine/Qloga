package eac.qloga.android.features.p4p.shared.scenes.ratingDetails

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.NavigationActions
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.RatingConverter
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.shared.components.RatingFiveStar
import eac.qloga.android.features.p4p.showroom.shared.components.StatusButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingDetailsScreen(
    navigationActions: NavigationActions
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val screenPadding = 24.dp
    val rating = RatingDetailsViewModel.rating.value
    val ratings = RatingDetailsViewModel.ratings.value
    val ratingCount = RatingConverter.ratingToNorm(rating)

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.RatingDetails.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navigationActions.upPress()
            }
        }
    ) { paddingValues ->

        val titleBarHeight = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = screenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(titleBarHeight))
            Spacer(modifier = Modifier.height(containerTopPadding))

            StatusButton(leadingIcon = R.drawable.ic_rating_star, label = "Rating Details", count = "$ratingCount/5")
            Spacer(modifier = Modifier.height(16.dp))


            ratings.forEach {
                val convertedRating = RatingConverter.ratingToNorm(it.rating.toFloat())
                if(convertedRating != null){
                    RatingFiveStar(
                        label = it.categoryName,
                        ratings = convertedRating.toInt(),
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRatingDetails() {
    RatingDetailsScreen(NavigationActions(NavController(Application())))
}

