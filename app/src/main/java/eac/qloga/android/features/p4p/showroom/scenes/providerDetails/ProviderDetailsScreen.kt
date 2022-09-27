package eac.qloga.android.features.p4p.showroom.scenes.providerDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.NavigationActions
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.Containers.BottomButtonContainer
import eac.qloga.android.core.shared.components.HeartIconButton
import eac.qloga.android.core.shared.components.PulsePlaceholder
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.*
import eac.qloga.android.features.p4p.shared.scenes.contactDetails.ContactDetailsViewModel
import eac.qloga.android.features.p4p.shared.scenes.ratingDetails.RatingDetailsViewModel
import eac.qloga.android.features.p4p.shared.scenes.reviews.ReviewsViewModel
import eac.qloga.android.features.p4p.shared.scenes.verifications.VerificationsViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.scenes.providerServices.ProviderServicesViewModel
import eac.qloga.android.features.p4p.showroom.scenes.providerWorkingSchedule.PrvWorkingScheduleViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.ExpandableText
import eac.qloga.android.features.p4p.showroom.shared.components.ProfileCategoryList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderDetailsScreen(
    navController: NavController,
    viewModel: ProviderDetailsViewModel = hiltViewModel(),
    navigationActions: NavigationActions
) {
    val imageHeight = 96.dp
    val containerTopPadding = 8.dp
    val provider = viewModel.provider.value
    val providerId = viewModel.providerID.value
    val profileImage = viewModel.avatarBitmap.value
    val name = provider.org?.name
    val type = if(viewModel.provider.value.org?.individual == true) "Individual" else ""
    val cancellationCount = provider.cancellations ?: 0
    val distanceCount = 12.5 //calculate using lat lng and current location
    val descriptionText = provider.org?.descr
    val isActive = provider.active?: false
    val servicesCount = provider.services?.size
    val portfolioCount = provider.portfolio?.size
    val reviewsCount = viewModel.reviews.value.size
    val verifications = VerificationConverter.verificationToString(provider.org?.verifications)
    val rating = RatingConverter.ratingToNorm(provider.rating?.toFloat())
    val contacts = AddressConverter.addressToString(provider.org?.contacts?.address)
    val loadingStateAvatar by viewModel.avatarState.collectAsState()
    //val providerID = 1003

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        viewModel.preCallsLoad()
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pShowroomScreens.ProviderDetails.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions = {
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        HeartIconButton(
                            onClick = { viewModel.onToggleFavouritePrv() },
                            isSelected = provider.id in viewModel.favouriteProviders.value.map { it.id }
                        )
                    }
                }
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

                        val painterImage = rememberAsyncImagePainter(
                            model = profileImage
                        )

                        if(loadingStateAvatar == LoadingState.LOADING && profileImage == null){
                            PulsePlaceholder(
                                modifier = Modifier.size(imageHeight),
                                roundedCornerShape = RoundedCornerShape(4.dp)
                            )
                        }else{
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                                    .height(imageHeight),
                                painter = painterImage,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.TopCenter
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 12.dp),
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
                            .padding(start = 20.dp)
                    ) {
                        if(name == null){
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(28.dp)
                                .padding(top = 4.dp)
                            ){
                                PulsePlaceholder(modifier = Modifier.height(28.dp))
                            }
                        }else{
                            Text(
                                modifier = Modifier,
                                text = name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Spacer(modifier= Modifier.height(8.dp))
                        if(isActive){
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .border(
                                        1.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    )
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Active",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier= Modifier.height(8.dp))
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

                            if(provider.calloutCharge){
                                Box(
                                    Modifier.clip(CircleShape)
                                ){
                                    Icon(
                                        modifier = Modifier.size(18.dp),
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
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

                Spacer(modifier = Modifier.height(16.dp))
                ExpandableText(text = descriptionText ?: "")
                Spacer(modifier = Modifier.height(24.dp))

                ContainerBorderedCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ProfileCategoryList(title = "Services", value = "$servicesCount", iconId = R.drawable.ic_ql_heart_hand) {
                            ProviderServicesViewModel.providerServices.value = provider.services
                            navController.navigate(P4pShowroomScreens.ProviderServices.route)
                        }

                        ProfileCategoryList(title = "Rating", value = "$rating", iconId = R.drawable.ic_rating_star) {
                            RatingDetailsViewModel.rating.value = provider.rating.toFloat()
                            RatingDetailsViewModel.ratings.value = provider.ratings
                            navigationActions.goToRatingDetails()
                        }

                        ProfileCategoryList(title = "Portfolio", value = "$portfolioCount", iconId = R.drawable.ic_portfolio_image) {
                            scope.launch {
                                navController.navigate(
                                    P4pShowroomScreens.PortfolioAlbums.route+"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.ProviderDetails.route}"
                                )
                            }
                        }

                        ProfileCategoryList(title = "Working hours & off time",value="", iconId = R.drawable.ic_ql_watch) {
                            PrvWorkingScheduleViewModel.workingHours.value = provider.org.workingHours
                            PrvWorkingScheduleViewModel.offTime.value = provider.org.offTime
                            navigationActions.goToProviderWorkingSchedule()
                        }

                        ProfileCategoryList(title = "Verifications", value = verifications, iconId = R.drawable.ic_verification) {
                            VerificationsViewModel.verifications.value = provider.org.verifications
                            navigationActions.goToVerifications()
                        }

                        ProfileCategoryList(title = "Reviews", value = "$reviewsCount", iconId = R.drawable.ic_reviews) {
                            ReviewsViewModel.reviews.value = viewModel.reviews.value
                            navigationActions.goToReviews()
                        }

                        ProfileCategoryList(title = "Contacts",value= contacts, iconId = R.drawable.ic_ql_contacts) {
                            ContactDetailsViewModel.contacts.value = provider.contacts
                            navigationActions.goToContactDetails()
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
