package eac.qloga.android.features.p4p.provider.scenes.providerProfile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.NavigationActions
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.Containers.BottomButtonContainer
import eac.qloga.android.core.shared.components.HeartIconButton
import eac.qloga.android.core.shared.components.PulsePlaceholder
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.*
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
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
import retrofit2.HttpException
import retrofit2.http.GET

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderProfileScreen(
    navController: NavController,
    viewModel: ProviderProfileViewModel = hiltViewModel(),
    navigationActions: NavigationActions,
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val imageHeight = 96.dp
    val containerTopPadding = 8.dp
    val provider by ProviderProfileViewModel.provider.collectAsState()
    val loadingStateAvatar by viewModel.avatarState.collectAsState()
    val profileImage = viewModel.avatarBitmap.value
    val name = provider.org?.name
    val type = if(provider.org?.individual == true) "Individual" else ""
    val cancellationCount = provider.cancellations ?: 0
    val distanceCount = provider.coverageZone
    val descriptionText = provider.org?.descr
    val isActive = provider.active?: false
    val servicesCount = if(provider.services != null) provider.services.size else null
    val portfolioCount = if(provider.portfolio != null) provider.portfolio.size else null
    val reviewsCount = viewModel.reviews.value.size
    val verifications = if(provider.org?.verifications != null )
        VerificationConverter.verificationToString(provider.org?.verifications) else null
    val rating = if(provider.rating != null)
        RatingConverter.ratingToNorm(provider.rating?.toFloat()) else null
    val contacts = if(provider.org?.contacts != null)
        AddressConverter.addressToString(provider.org?.contacts?.address) else null
    val providerState by viewModel.providerState.collectAsState()

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        viewModel.preCallsLoad()
        apiViewModel.getProvider()
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.ProviderProfile.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions = {
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        if(ProviderProfileViewModel.showHeartBtn){
                            HeartIconButton(
                                onClick = { viewModel.onToggleFavouritePrv() },
                                isSelected = provider.id in viewModel.favouriteProviders.value.map { it.id }
                            )
                        }
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
                            model = profileImage ?: R.drawable.prv_default_ava
                        )

                        if(loadingStateAvatar == LoadingState.LOADING && provider.org?.avatarId != null ) {
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
                            Box(
                                Modifier.clip(CircleShape)
                            ){
                                if(provider.calloutCharge){
                                    Icon(
                                        modifier = Modifier.size(18.dp),
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }else{
                                    Icon(
                                        modifier = Modifier.size(18.dp),
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = "",
                                        tint = Red10
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

                if(!descriptionText.isNullOrEmpty()){
                    Spacer(modifier = Modifier.height(16.dp))
                    ExpandableText(text = descriptionText)
                }
                Spacer(modifier = Modifier.height(24.dp))

                ContainerBorderedCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if(servicesCount != null && servicesCount > 0){
                            ProfileCategoryList(title = "Services", value = "$servicesCount", iconId = R.drawable.ic_ql_heart_hand) {
                                ProviderServicesViewModel.providerServices.value = provider.services
                                navController.navigate(P4pShowroomScreens.ProviderServices.route)
                            }
                        }

                        if( rating != null){
                            ProfileCategoryList(title = "Rating", value = "$rating", iconId = R.drawable.ic_rating_star) {
                                RatingDetailsViewModel.rating.value = provider.rating.toFloat()
                                RatingDetailsViewModel.ratings.value = provider.ratings
                                navigationActions.goToRatingDetails()
                            }
                        }

                        if(portfolioCount != null && portfolioCount > 0){
                            ProfileCategoryList(title = "Portfolio", value = "$portfolioCount", iconId = R.drawable.ic_portfolio_image) {
                                scope.launch {
                                    navController.navigate(
                                        P4pShowroomScreens.PortfolioAlbums.route+"?$PARENT_ROUTE_KEY=${P4pProviderScreens.ProviderProfile.route}"
                                    )
                                }
                            }
                        }

                        if(provider.org.workingHours.isNotEmpty() || provider.org.offTime.isNotEmpty()){
                            ProfileCategoryList(title = "Working hours & off time", value="", iconId = R.drawable.ic_ql_watch) {
                                PrvWorkingScheduleViewModel.workingHours.value = provider.org.workingHours
                                PrvWorkingScheduleViewModel.offTimes = provider.org.offTime
                                navigationActions.goToProviderWorkingSchedule()
                            }
                        }

                        if(verifications != null && verifications.isNotEmpty()){
                            ProfileCategoryList(title = "Verifications", value = verifications, iconId = R.drawable.ic_verification) {
                                VerificationsViewModel.verifications.value = provider.org.verifications
                                navigationActions.goToVerifications()
                            }
                        }

                        if(reviewsCount > 0){
                            ProfileCategoryList(title = "Reviews", value = "$reviewsCount", iconId = R.drawable.ic_reviews) {
                                ReviewsViewModel.reviews.value = viewModel.reviews.value
                                navigationActions.goToReviews()
                            }
                        }

                        if(contacts != null && contacts.isNotEmpty()){
                            ProfileCategoryList(title = "Contacts",value= contacts, iconId = R.drawable.ic_ql_contacts) {
                                ContactDetailsViewModel.contacts.value = provider.contacts
                                navigationActions.goToContactDetails()
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(78.dp))
            }

            //inquiry button
            if(ProviderProfileViewModel.providerId != ApiViewModel.orgs[0].id){
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
            if(providerState == LoadingState.LOADING && provider.org?.name.isNullOrEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}