package eac.qloga.android.features.p4p.showroom.scenes.preoviderDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.Containers.BottomButtonContainer
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.PARENT_ROUTE_KEY
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.ListInfo
import eac.qloga.android.features.p4p.showroom.shared.components.ProfileCategoryList
import eac.qloga.android.features.p4p.showroom.shared.components.StatusButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderDetailsScreen(
    navController: NavController
) {
    val profileImageId =  R.drawable.model_profile
    val imageHeight = 96.dp
    val type = "Individual"
    val cancellationCount = 6
    val distanceCount = 12.5
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pShowroomScreens.ProviderDetails.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(.3f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight),
                            painter = painterResource(id = profileImageId),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopCenter
                        )

                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = type,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W400
                            ),
                            color = gray30
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Help with daily cleaning",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier= Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Callout charge",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.W400,
                                color = gray30
                            )

                            Box(
                                Modifier
                                    .clip(CircleShape)
                                    .clickable { }){
                                Icon(
                                    modifier = Modifier.size(18.dp),
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Cancellation(hrs)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.W400,
                                color = gray30
                            )

                            Text(
                                text = "$cancellationCount",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.W500,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Distance(mls)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.W400,
                                color = gray30
                            )

                            Text(
                                text = "$distanceCount",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.W500,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                //cleaning button
                StatusButton(leadingIcon = R.drawable.ic_clean, label = "Cleaning", count = "4")

                Spacer(modifier = Modifier.height(16.dp))

                //points
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    ListInfo(label = "Window Cleaning", measureValue = "(window / hour)", price = "266.0")
                    ListInfo(label = "Complete home Cleaning", measureValue = "(window / 30 min)", price =  "134.0")
                    ListInfo(label = "Kitchen Cleaning", measureValue = "(kitchen / 90 min)", price = "245.0")
                    ListInfo(label = "Bathroom and toilet Cleaning", measureValue = "(room / 40 min)", price =  "134.0")
                    ListInfo(label = "Bedroom or living room Cleaning", measureValue =  "(room / 60 min)", price = "123.0")
                    ListInfo(label = "Deep uphoistery and carpet Cleaning", measureValue = "(sqrt meter / 60 min)", price = "100.0")
                }

                Spacer(modifier = Modifier.height(16.dp))

                ContainerBorderedCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ProfileCategoryList(title = "Rating", value = "4.4/5", iconId = R.drawable.ic_rating_star) {
                            scope.launch {
                                //navController.navigate(Screen.RatingDetails.route)
                            }
                        }

                        ProfileCategoryList(title = "Portfolio", value = "8", iconId = R.drawable.ic_portfolio_image) {
                            scope.launch {
                                navController.navigate(P4pShowroomScreens.PortfolioAlbums.route+"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.ProviderDetails.route}")
                            }
                        }

                        ProfileCategoryList(title = "Verifications", value = "ID Address, Phone", iconId = R.drawable.ic_verification) {
                            scope.launch {
                                //navController.navigate(Screen.Verifications.route)
                            }
                        }

                        ProfileCategoryList(title = "Reviews", value = "12", iconId = R.drawable.ic_reviews) {
                            scope.launch {
                                //navController.navigate(Screen.Previews.route)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(78.dp))
            }

            //inquiry button
            BottomButtonContainer(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                FullRoundedButton(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    buttonText = "Direct Inquiry"
                ) {
                    scope.launch {
                        /*
                        navController.navigate(
                            Screen.Services.route+"?$PARENT_ROUTE_KEY=${Screen.ProviderDetails.route}"
                        )
                        */
                    }
                }
            }
        }
    }
}
