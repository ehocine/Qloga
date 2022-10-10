package eac.qloga.android.features.p4p.provider.scenes.providedServiceConditions

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.scenes.account.ProfilesEvent
import eac.qloga.android.features.p4p.shared.scenes.account.ProfilesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvidedServiceConditionsScreen(
    navController: NavController,
    viewModel: ProfilesViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val descriptionCustomerCleaning  = "Customer provides all cleaning products like kitchen cleaner, " +
            "bathroom cleaner, bleach, window and glass cleaner, disinfectant," +
            " oven cleaning liquid, limescale remover, etc."
    val descNoCarpetCleaning = "Service person will not clean or hoover carpets"
    val providesCleaningSwitch = viewModel.providesCleaningSwitch.value
    val noCarpetCleaningSwitch = viewModel.noCarpetCleaningSwitch.value
    val noSideWindowsCleaningSwitch = viewModel.noSideWindowsCleaningSwitch.value

    val scrollState = rememberScrollState()

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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, gray1, RoundedCornerShape(16.dp))
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
                                text = "Customer provides cleaning products",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Switch(
                                modifier = Modifier.height(24.dp),
                                checked = providesCleaningSwitch,
                                onCheckedChange = { viewModel.onTriggerEvent(ProfilesEvent.SwitchProvidesCleaning) },
                                colors = SwitchDefaults.colors(
                                    uncheckedBorderColor = gray1,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.alpha(.75f),
                    text = descriptionCustomerCleaning,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, gray1, RoundedCornerShape(16.dp))
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
                                text = "No carpet cleaning",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Switch(
                                modifier = Modifier.height(24.dp),
                                checked = noCarpetCleaningSwitch,
                                onCheckedChange = { viewModel.onTriggerEvent(ProfilesEvent.SwitchNoCarpetCleaning) },
                                colors = SwitchDefaults.colors(
                                    uncheckedBorderColor = gray1,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.alpha(.75f),
                    text = descNoCarpetCleaning,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, gray1, RoundedCornerShape(16.dp))
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
                                    .padding(end = 4.dp),
                                text = "No cleaning of external-facing sides of windows",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Switch(
                                modifier = Modifier.height(24.dp),
                                checked = noSideWindowsCleaningSwitch,
                                onCheckedChange = { viewModel.onTriggerEvent(ProfilesEvent.SwitchNoSideWindowsCleaning) },
                                colors = SwitchDefaults.colors(
                                    uncheckedBorderColor = gray1,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.alpha(.75f),
                    text = descriptionCustomerCleaning,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}