package eac.qloga.android.features.negotiation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.services.BrowserState
import eac.qloga.android.core.util.LoadingState
import eac.qloga.android.core.viewmodels.ApiViewModel
import eac.qloga.android.core.viewmodels.AuthenticationViewModel
import eac.qloga.android.features.intro.presentation.components.ItemCard
import eac.qloga.android.features.intro.presentation.components.LeftNavBar
import eac.qloga.android.features.intro.util.ServiceCategory
import eac.qloga.android.features.shared.util.NavigationActions
import eac.qloga.android.ui.theme.LightGreen10
import eac.qloga.android.ui.theme.green1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListPrvScreen(
    navController: NavController,
    authViewModel: AuthenticationViewModel,
    apiViewModel: ApiViewModel,
    viewModel: OrderListPrvViewModel,
    actions: NavigationActions
) {
    val context = LocalContext.current
    val selectedNavItemIndex = remember { mutableStateOf(0) }
    val selectedNavItem = remember { mutableStateOf<ServiceCategory?>(null) }
    val containerHorizontalPadding = 24.dp
    val scope = rememberCoroutineScope()
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)

    LaunchedEffect(key1 = true) {
        apiViewModel.getEnrollsLoadingState.emit(LoadingState.IDLE)
    }


    Scaffold { paddingValues ->

        val titleBarHeight = paddingValues.calculateTopPadding()
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = containerHorizontalPadding,
                    end = containerHorizontalPadding,
                    top = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LeftNavBar(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp))
                    .background(LightGreen10)
                    .padding(horizontal = 8.dp),
                topSpace = 0.dp,
                selectedNav = selectedNavItem.value,
                onClickItem = { selectedNavItem.value = it }
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                CustomerCard(
                    onClickCard = {
//                       scope.launch {
                        //                         navController.navigate(Screen.CustomerNavContainer.route){
                        //                           launchSingleTop = true
                        //                     }
                        //               }
                    }
                )
                ProviderCard(
                    onClickCard = {
                        //             scope.launch {
                        //               navController.navigate(Screen.ProviderNavContainer.route){
                        //                 launchSingleTop = true
                        //           }
                        //     }
                    }
                )

                when (oktaState) {
                    is BrowserState.Loading -> {
                        CircularProgressIndicator(color = green1)
                    }
                    is BrowserState.LoggedOut -> {
                        actions.goToSignIn.invoke()
                    }
                    else -> {
                        Button(onClick = {
                            authViewModel.oktaLogout(context)
                        }) {
                            Text(text = "Log out")
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun CustomerCard(
    modifier: Modifier = Modifier,
    onClickCard: () -> Unit,
) {
    val cardHeight = 280.dp

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(LightGreen10)
            .padding(top = 16.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)
    ) {
        ItemCard(
            modifier = Modifier.height(cardHeight),
            imageModifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 16.dp),
            label = "Customer",
            iconId = R.drawable.ql_cst_avtr_acc,
            onClick = { onClickCard() }
        )
    }
}

@Composable
fun ProviderCard(
    modifier: Modifier = Modifier,
    onClickCard: () -> Unit,
) {
    val cardHeight = 280.dp

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(LightGreen10)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
        ) {
            ItemCard(
                modifier = Modifier
                    .height(cardHeight),
                imageModifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp),
                label = "Provider",
                iconId = R.drawable.pvr_profile_ava,
                onClick = { onClickCard() }
            )
        }
    }
}
