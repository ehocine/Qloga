package eac.qloga.android.features.p4p.shared.scenes.inquiredServices

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.AddButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.ServicesItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.ServiceEvent
import eac.qloga.android.features.p4p.shared.viewmodels.ServiceViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InquiredServicesScreen(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel(),
) {
    val containerHorizontalPadding = 24.dp
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        // for testing only
        viewModel.onTriggerEvent(ServiceEvent.AddWindowClean)
        viewModel.onTriggerEvent(ServiceEvent.AddWindowClean)
        viewModel.onTriggerEvent(ServiceEvent.AddKitchenClean)
        viewModel.onTriggerEvent(ServiceEvent.AddKitchenClean)
        viewModel.onTriggerEvent(ServiceEvent.AddKitchenClean)
        viewModel.onTriggerEvent(ServiceEvent.AddKitchenClean)
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.InquiredServices.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    AddButton(onClick = {
                        scope.launch {
//                            when(parentRoute){
//                                Screen.ProviderDetails.route -> { navController.navigate(Screen.Inquiry.route) }
//                                Screen.CustomerDetails.route -> { navController.navigate(Screen.Quote.route) }
//                                Screen.FavouriteCustomers.route -> {
//                                    navController.navigate(Screen.Quote.route){
//                                        popUpTo(Screen.Services.route){
//                                            inclusive = true
//                                        }
//                                    }
//                                }
//                                Screen.FavouriteProviders.route -> { navController.navigate(Screen.Inquiry.route) }
//                                else -> { navController.navigateUp() }
//                            }
                        }
                    } )
                }
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = containerHorizontalPadding, end = 16.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topPadding))

            ServicesItem(
                additionalInfo = "Time norm: 60 min/room (recomended)",
                description = "Professional will come to your house and try " +
                "theirhardest to fix your boiler as soon as possible.",
                title = "Windows Cleaning",
                onSub = { viewModel.onTriggerEvent(ServiceEvent.SubWindowClean) },
                onAdd = { viewModel.onTriggerEvent(ServiceEvent.AddWindowClean) },
                onClickInfo = {
                   scope.launch {
//                       navController.navigate(
//                           Screen.SelectedService.route+"?$ID_KEY=0&$PARENT_ROUTE_KEY=${Screen.Services.route}"
//                       )
                   }
                },
                count = viewModel.windowCleanCount.value.count,
                price = "350.00",
            )

            ServicesItem(
                additionalInfo = "Time norm: 60 min/room (recomended)",
                description = "Professional will come to your house and try " +
                "theirhardest to fix your boiler as soon as possible.",
                title = "Kitchen Cleaning",
                onSub = { viewModel.onTriggerEvent(ServiceEvent.SubKitchenClean) },
                onAdd = { viewModel.onTriggerEvent(ServiceEvent.AddKitchenClean) },
                count = viewModel.kitchenCleanCount.value.count,
                onClickInfo = {
                    scope.launch {
//                        navController.navigate(
//                            Screen.SelectedService.route + "?$ID_KEY=1&$PARENT_ROUTE_KEY=${Screen.Services.route}"
//                        )
                    }
                },
                price = "250.00",
            )

            ServicesItem(
                additionalInfo = "Time norm: 60 min/room (recomended)",
                description = "Professional will come to your house and try " +
                "theirhardest to fix your boiler as soon as possible.",
                title = "Bedroom or living room  Cleaning",
                onSub = { viewModel.onTriggerEvent(ServiceEvent.SubBedroomClean) },
                onAdd = { viewModel.onTriggerEvent(ServiceEvent.AddBedroomClean)},
                onClickInfo = {
                    scope.launch {
//                        navController.navigate(
//                            Screen.SelectedService.route + "?$ID_KEY=2&$PARENT_ROUTE_KEY=${Screen.Services.route}"
//                        )
                    }
                },
                count = viewModel.bedRoomCleanCount.value.count,
                price = "550.00",
            )

            ServicesItem(
                additionalInfo = "Time norm: 60 min/room (recomended)",
                description = "Professional will come to your house and try " +
                "theirhardest to fix your boiler as soon as possible.",
                title = "Complete home Cleaning",
                onSub = { viewModel.onTriggerEvent(ServiceEvent.SubCompleteHomeClean) },
                onAdd = { viewModel.onTriggerEvent(ServiceEvent.AddCompleteHomeClean) },
                onClickInfo = {
                    scope.launch {
//                        navController.navigate(
//                            Screen.SelectedService.route + "?$ID_KEY=3&$PARENT_ROUTE_KEY=${Screen.Services.route}"
//                        )
                    }
                },
                count = viewModel.completeHomeCleanCount.value.count,
                price = "250.00",
            )

            ServicesItem(
                additionalInfo = "Time norm: 60 min/room (recomended)",
                description = "Professional will come to your house and try " +
                "theirhardest to fix your boiler as soon as possible.",
                title = "Complete Cleaning",
                onSub = { viewModel.onTriggerEvent(ServiceEvent.SubCompleteClean) },
                onAdd = { viewModel.onTriggerEvent(ServiceEvent.AddCompleteClean) },
                onClickInfo = {
                    scope.launch {
//                        navController.navigate(
//                            Screen.SelectedService.route + "?$ID_KEY=4&$PARENT_ROUTE_KEY=${Screen.Services.route}"
//                        )
                    }
                },
                count = viewModel.completeCleanCount.value.count,
                price = "250.00",
            )
        }
    }
}
