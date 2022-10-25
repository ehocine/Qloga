package eac.qloga.android.features.p4p.customer.scenes.customerProfile

import P4pCustomerScreens
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.HeartIconButton
import eac.qloga.android.core.shared.components.PulsePlaceholder
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.*
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.contactDetails.ContactDetailsViewModel
import eac.qloga.android.features.p4p.shared.scenes.reviews.ReviewsViewModel
import eac.qloga.android.features.p4p.shared.scenes.verifications.VerificationsViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.showroom.shared.components.ProfileCategoryList
import eac.qloga.android.features.p4p.showroom.shared.components.RatingFiveStar
import eac.qloga.android.features.p4p.showroom.shared.components.StatusButton
import eac.qloga.p4p.cst.dto.CstPublicProfile
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerProfileScreen(
    navController: NavController,
    viewModel: CustomerProfileViewModel = hiltViewModel()
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val imageHeight  = 90.dp
    val imageWidth = 90.dp
    val profileImageId by viewModel.profileImage
    val customer  = CustomerProfileViewModel.customerProfile.collectAsState().value ?: CstPublicProfile()
    val isHeartSelected by viewModel.isFavourite
    val profileImageState by viewModel.profileImageState.collectAsState()
    val contactAddress = AddressConverter.addressToString(customer.contacts.address)
    val rating = RatingConverter.ratingToNorm(customer.rating?.toFloat()) ?: "not yet"
    val verifications = VerificationConverter.verificationToString(customer.vrfs)

    val userProfileState by viewModel.userProfileState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = Unit, key2 = customer){
        viewModel.preCallsLoad()
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pCustomerScreens.CustomerProfile.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions = {
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        if(CustomerProfileViewModel.showHeart.value){
                            HeartIconButton(
                                onClick = { viewModel.toggleHeart() },
                                isSelected = isHeartSelected
                            )
                        }
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
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(titleBarHeight))
                Spacer(modifier = Modifier.height(containerTopPadding))

                if(userProfileState == LoadingState.LOADING){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }else{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Box {
                            val painter = rememberAsyncImagePainter(model = profileImageId ?: R.drawable.cst_default_ava)

                            if(profileImageState == LoadingState.LOADING && profileImageId == null){
                                PulsePlaceholder(
                                    modifier = Modifier
                                        .height(imageHeight)
                                        .width(imageWidth),
                                    roundedCornerShape = CircleShape
                                )
                            }else{
                                Image(
                                    modifier = Modifier
                                        .width(imageWidth)
                                        .height(imageHeight)
                                        .clip(CircleShape)
                                    ,
                                    painter = painter,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.TopCenter
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight)
                                .padding(start = 16.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = customer.fullName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.W600,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier= Modifier.height(8.dp))
                            if(customer.active == true){
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
                            }
                            Spacer(modifier= Modifier.height(8.dp))

                            Text(
                                modifier = Modifier.padding(end = 8.dp),

                                text = "Completed orders: ${customer.orderQuantity ?: 0}",
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
                        count = "$rating/5",
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        customer.ratings?.forEach {  rating ->
                            RatingFiveStar(
                                ratings = RatingConverter.ratingToNorm(
                                    rating.rating.toFloat()
                                )?.toFloat()?.roundToInt()!!,
                                label = rating?.categoryName ?: ""
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Cards.ContainerBorderedCard {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            if(verifications.isNotEmpty()){
                                ProfileCategoryList(
                                    title = "Verifications",
                                    value = verifications,
                                    iconId = R.drawable.ic_verification
                                ) {
                                    VerificationsViewModel.accountType = AccountType.CUSTOMER
                                    VerificationsViewModel.verifications.value = customer.vrfs
                                    navController.navigate(P4pScreens.Verifications.route)
                                }
                            }
                            if(viewModel.reviews.value.isNotEmpty()){
                                ProfileCategoryList(
                                    title = "Reviews",
                                    value = "${viewModel.reviews.value.size }",
                                    iconId = R.drawable.ic_reviews
                                ) {
                                    ReviewsViewModel.reviews.value = viewModel.reviews.value
                                    navController.navigate(P4pScreens.Reviews.route)
                                }
                            }
                            if(contactAddress.isNotEmpty()){
                                ProfileCategoryList(
                                    title = "Contacts",
                                    value = contactAddress ,
                                    iconId = R.drawable.ic_ql_contacts
                                ) {
                                    ContactDetailsViewModel.contacts.value = customer.contacts
                                    navController.navigate(P4pScreens.ContactDetails.route)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}