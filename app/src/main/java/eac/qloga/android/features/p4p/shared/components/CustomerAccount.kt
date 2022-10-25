package eac.qloga.android.features.p4p.shared.components

import P4pCustomerScreens
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.PulsePlaceholder
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.shared.models.MediaSize
import eac.qloga.android.features.p4p.customer.scenes.customerProfile.CustomerProfileViewModel
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.prvCstTC.PrvCstTCViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import eac.qloga.bare.dto.person.Person
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun CustomerAccount(
    navController: NavController,
    apiViewModel: ApiViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var avatarImage by remember{ mutableStateOf<Bitmap?>(null)}
    var avatarLoadingState by remember { mutableStateOf(LoadingState.IDLE) }

    LaunchedEffect(key1 = Unit ){
        avatarLoadingState = LoadingState.LOADING
        apiViewModel.getAvatarBitmap(ApiViewModel.userProfile.value.avatarId,MediaSize.Sz150x150)
        avatarImage = ApiViewModel.bitmapImages[ApiViewModel.userProfile.value.avatarId]
        avatarLoadingState = LoadingState.LOADED
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier.width(IntrinsicSize.Min)) {
            val painter = rememberAsyncImagePainter(model = avatarImage ?: R.drawable.cst_default_ava)

            if(avatarLoadingState == LoadingState.LOADING){
                PulsePlaceholder(
                    modifier = Modifier.size(120.dp),
                    roundedCornerShape = CircleShape
                )
            }else{
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .padding(top = if(avatarImage == null) 6.dp else 0.dp)
                    ,
                    painter = painter,
                    contentDescription = "",
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp))
                    .align(Alignment.BottomEnd)
                    .clickable {
                        coroutineScope.launch {
                            //navController.navigate(Screen.Albums.route)
                        }
                    }
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 4.dp, start = 4.dp)
            ){
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                    ,
                    painter = painterResource(id = R.drawable.ic_ql_camera),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    //list of options
    Spacer(modifier = Modifier.height(16.dp))
    AccountOptionItem(title = "Settings", iconId = R.drawable.ic_ql_settings) {
        coroutineScope.launch {
            AccountSettingsViewModel.accountType = AccountType.CUSTOMER
            navController.navigate(P4pCustomerScreens.CustomerAccountSettings.route)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    AccountOptionItem(title = "Show public profile", iconId = R.drawable.ic_ql_profile) {
        coroutineScope.launch {
            CustomerProfileViewModel.showHeart.value = false
            navController.navigate(P4pCustomerScreens.CustomerProfile.route){
                launchSingleTop = true
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    AccountOptionItem(title = "Frequently asked questions", iconId = R.drawable.ic_ql_questions) {
        coroutineScope.launch {
            navController.navigate(P4pScreens.FAQDashboard.route){
                launchSingleTop = true
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    AccountOptionItem(title = "Customer's Terms & Conditions", iconId = R.drawable.ic_ql_terms_conditions) {
        coroutineScope.launch {
            PrvCstTCViewModel.accountType = AccountType.CUSTOMER
            navController.navigate(P4pScreens.PrvCstTC.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}