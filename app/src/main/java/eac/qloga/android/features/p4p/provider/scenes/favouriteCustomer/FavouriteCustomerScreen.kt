package eac.qloga.android.features.p4p.provider.scenes.favouriteCustomer

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.HeartIconButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderNegotiationViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.ProfileCategoryList
import eac.qloga.android.features.p4p.showroom.shared.components.RatingFiveStar
import eac.qloga.android.features.p4p.showroom.shared.components.StatusButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteCustomerScreen(
    navController: NavController,
    viewModel: ProviderNegotiationViewModel = hiltViewModel()
) {
    val profileImageId =  R.drawable.design_profile_img
    val name = "Nat Bluesky"
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val imageHeight  = 90.dp
    val imageWidth = 90.dp
    val isHeartSelected = remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.FavouriteCustomer.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions = {
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        HeartIconButton(
                            onClick = { isHeartSelected.value = !isHeartSelected.value },
                            isSelected = isHeartSelected.value
                        )
                    }
                }
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val titleBarHeight = paddingValues.calculateTopPadding()

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

                Spacer(modifier = Modifier.height(titleBarHeight))
                Spacer(modifier = Modifier.height(containerTopPadding))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Box {
                        Image(
                            modifier = Modifier
                                .width(imageWidth)
                                .height(imageHeight)
                                .clip(CircleShape)
                            ,
                            painter = painterResource(id = profileImageId),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopCenter
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(imageHeight)
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier= Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                )
                                .padding(vertical = 2.dp, horizontal = 8.dp)
                        ) {
                            Text(
                                text = "Active",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier= Modifier.height(8.dp))

                        Text(
                            modifier = Modifier.padding(end = 8.dp),
                            text = "Completed orders: 10",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.W500,
                            color = gray30
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                //rating button
                StatusButton(
                    leadingIcon = R.drawable.ic_rating_star,
                    label = "Rating",
                    count = "4.2/5",
                )
                Spacer(modifier = Modifier.height(16.dp))

                //points
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    RatingFiveStar(label = "Communications", ratings = 4)
                    Spacer(Modifier.height(8.dp))
                    RatingFiveStar(label = "Prompt payment", ratings = 3)
                    Spacer(Modifier.height(8.dp))
                    RatingFiveStar(label = "Sufficient enough chemicals and materials", ratings = 4)
                    Spacer(Modifier.height(8.dp))
                    RatingFiveStar(label = "Prompt payment", ratings = 3)
                }
                Spacer(modifier = Modifier.height(24.dp))

                Cards.ContainerBorderedCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ProfileCategoryList(
                            title = "Verifications",
                            value = "ID Address, Phone",
                            iconId = R.drawable.ic_verification
                        ) {
                            scope.launch {
                                // navController.navigate(Screen.Verifications.route)
                            }
                        }
                        ProfileCategoryList(
                            title = "Reviews",
                            value = "12",
                            iconId = R.drawable.ic_reviews
                        ) {
                            scope.launch {
                                // navController.navigate(Screen.Previews.route)
                            }
                        }
                        ProfileCategoryList(
                            title = "Contacts",
                            value = "",
                            iconId = R.drawable.ic_ql_contacts
                        ) {
                            scope.launch {
                                // navController.navigate(Screen.Contacts.route)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}