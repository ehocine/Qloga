package eac.qloga.android.features.p4p.shared.components

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import eac.qloga.android.core.shared.utils.PROVIDER_ID
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.shared.models.MediaSize
import eac.qloga.android.data.shared.utils.apiHandler
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.scenes.providerProfile.ProviderProfileViewModel
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.prvCstTC.PrvCstTCViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.bare.dto.person.Person
import eac.qloga.p4p.prv.dto.Provider
import kotlinx.coroutines.launch

@Composable
fun ProviderAccount(
    navController: NavController,
    apiViewModel: ApiViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var avatarImage by remember{ mutableStateOf<Bitmap?>(null) }
    var avatarImageLoading by remember{ mutableStateOf(LoadingState.IDLE) }

    LaunchedEffect(key1 = Unit){
        apiViewModel.getProvider()
        apiViewModel.getOrgs()
        apiViewModel.getUserProfile()
        val avatarId = ApiViewModel.orgs[0].avatarId
        avatarImageLoading = LoadingState.LOADING
        apiViewModel.getAvatarBitmap(avatarId, MediaSize.Sz150x150)
        avatarImage = ApiViewModel.bitmapImages[avatarId]
        avatarImageLoading = LoadingState.LOADED
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier.width(IntrinsicSize.Min)) {
            val painter = rememberAsyncImagePainter(
                model = avatarImage ?: R.drawable.pvr_profile_ava
            )

            if(avatarImageLoading == LoadingState.LOADING){
                PulsePlaceholder(modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp)))
            }else{
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp))
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
            AccountSettingsViewModel.accountType = AccountType.PROVIDER
            navController.navigate(P4pProviderScreens.ProviderAccountSettings.route)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    val count = ApiViewModel.providerServices.value.count()
    AccountOptionItem(title = "Services", value = "$count", iconId = R.drawable.ic_ql_services) {
        coroutineScope.launch {
            navController.navigate(P4pProviderScreens.ServicesConditions.route){
                launchSingleTop = true
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    AccountOptionItem(title = "Working hour & off time", iconId = R.drawable.ic_ql_time) {
        coroutineScope.launch {
            navController.navigate(P4pProviderScreens.WorkingScheduleEdit.route){
                launchSingleTop = true
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    AccountOptionItem(title = "Manage Portfolio", iconId = R.drawable.ic_ql_portfolio) {
        coroutineScope.launch {
            navController.navigate(
                P4pShowroomScreens.PortfolioAlbums.route
            ){
                launchSingleTop = true
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    AccountOptionItem(title = "Show public profile", iconId = R.drawable.ic_ql_profile) {
        coroutineScope.launch {
            ProviderProfileViewModel.showHeartBtn = false
            Log.d("TAG", "ProviderAccount: ${ApiViewModel.provider}")
            ProviderProfileViewModel.provider.emit(Provider())
            ProviderProfileViewModel.provider.emit(ApiViewModel.provider ?: Provider())
            //ProviderProfileViewModel.provider = ApiViewModel.provider ?: Provider()
            ProviderProfileViewModel.providerId = ApiViewModel.orgs[0].id
            navController.navigate(
                P4pProviderScreens.ProviderProfile.route
            ){
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
    AccountOptionItem(title = "Provider's Terms & Conditions", iconId = R.drawable.ic_ql_terms_conditions) {
        coroutineScope.launch {
            PrvCstTCViewModel.accountType = AccountType.PROVIDER
            navController.navigate(P4pScreens.PrvCstTC.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}