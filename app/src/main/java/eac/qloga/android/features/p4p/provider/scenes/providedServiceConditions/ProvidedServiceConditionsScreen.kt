package eac.qloga.android.features.p4p.provider.scenes.providedServiceConditions

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.UiEvent
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.scenes.servicesConditions.ServicesConditionsViewModel
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderServicesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvidedServiceConditionsScreen(
    navController: NavController,
    viewModel: ProviderServicesViewModel = hiltViewModel(),
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val providerConditions = ProviderServicesViewModel.providerConditions
    val catConditions = ApiViewModel.conditions.value.filter {
        it.serviceCatId == ServicesConditionsViewModel.selectNavItem?.id
    }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.msg, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.ProvidedServiceConditions.titleName,
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
                .verticalScroll(scrollState)
                .padding(horizontal = containerHorizontalPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                catConditions.forEach { serviceCondition ->
                    Cards.ContainerBorderedCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                            ,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ){
                                Row(
                                    modifier= Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                    ,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(end = 8.dp),
                                        text = serviceCondition.name,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.W600
                                        )
                                    )
                                    Switch(
                                        modifier = Modifier.height(24.dp),
                                        checked = serviceCondition in providerConditions,
                                        onCheckedChange = {
                                            viewModel.updateCondition(serviceCondition)
                                        },
                                        colors = SwitchDefaults.colors(
                                            uncheckedBorderColor = gray1,
                                            uncheckedTrackColor = MaterialTheme.colorScheme.background
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = serviceCondition.descr,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}