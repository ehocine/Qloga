package eac.qloga.android.features.p4p.shared.scenes.reviews

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.NavigationActions
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.RatingConverter
import eac.qloga.android.features.p4p.shared.components.ReviewUserItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(
    navigationActions: NavigationActions,
    viewModel: ReviewsViewModel = hiltViewModel()
) {
    val screenPadding = 24.dp
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val scrollState = rememberScrollState()
    val reviews  = ReviewsViewModel.reviews.value

    LaunchedEffect(key1 = Unit, key2 = ReviewsViewModel.reviews.value){
        viewModel.loadAvatar()
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.Reviews.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navigationActions.upPress()
            }
        }
    ) { paddingValues->

        val titleBarHeight = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = screenPadding)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(titleBarHeight))
            Spacer(modifier = Modifier.height(containerTopPadding))

            reviews.forEach { review ->
                val avgRating = RatingConverter.ratingAvg(review.ratings)
                if(avgRating != null){
                    ReviewUserItem(
                        imageId = viewModel.avatarList.value.find { it.id == review.reviewerId }?.bitmap,
                        rating = avgRating.toFloat(),
                        label = review.feedback ?: "No feedbacks"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPreviewsScreen(){
    ReviewsScreen(
        navigationActions = NavigationActions(NavController(Application()))
    )
}
