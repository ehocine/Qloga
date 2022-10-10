package eac.qloga.android.features.p4p.shared.scenes.inquiry

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.Containers.BottomButtonContainer
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.QuoteOptionItem
import eac.qloga.android.features.p4p.shared.components.SelectedServicesItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.viewmodels.ServiceViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.shared.viewmodels.AddressViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InquiryScreen(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val totalSum = 450
    val showUpdateDelete = remember{ mutableStateOf(false) }
    val selectedServices = viewModel.selectedCleaningCategories.value
    
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.Inquiry.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val titleBarHeight = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .verticalScroll(scrollState)
                    .padding(horizontal = containerHorizontalPadding)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(titleBarHeight))
                Spacer(modifier = Modifier.height(containerTopPadding))

                ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        selectedServices.forEachIndexed { index, cleaningType ->
                            if(cleaningType.value.count > 0){
                                SelectedServicesItem(
                                    title = cleaningType.value.title,
                                    label = "Rate (£/hour): 21.00",
                                    count = cleaningType.value.count
                                ) {
                                    //TODO to assign service to ServiceInfoViewModel
                                    navController.navigate(
                                        P4pScreens.ServiceInfo.route
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                FullRoundedButton(
                    buttonText = "Add Services",
                    backgroundColor = MaterialTheme.colorScheme.background,
                    showBorder = true,
                    borderColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.primary
                ) {
                    scope.launch {
                        navController.navigate(P4pScreens.InquiredServices.route)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "Total sum: ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.W600
                    )
                    Text(
                        text = "£$totalSum",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.W600
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.alpha(.75f),
                        text = "Cancellation period: ",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )

                    Text(
                        modifier = Modifier.alpha(.75f),
                        text = "0 hours",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.alpha(.75f),
                        text = "Callout charge: ",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )

                    Text(
                        modifier = Modifier.alpha(.75f),
                        text = "£84.00",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.alpha(.75f),
                        text = "First visit: ",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )

                    Text(
                        modifier = Modifier.alpha(.75f),
                        text = "22/06/2022 22:00",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )
                }

                //bottom card
                Spacer(modifier = Modifier.height(24.dp))

                ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        QuoteOptionItem(
                            title = "Address",
                            value = addressViewModel.selectedAddress.value,
                            iconId = R.drawable.ic_ql_home
                        ) {
                            scope.launch {
                                navController.navigate(P4pScreens.SelectAddress.route)
                            }
                        }
                        QuoteOptionItem(title = "Visits", value = "8", iconId = R.drawable.ic_ql_flag) {
                            scope.launch {
                                navController.navigate(P4pScreens.DisplayVisits.route)
                            }
                        }
                        QuoteOptionItem(
                            title = "Tracking",
                            value = "",
                            enable = true,
                            iconId = R.drawable.ic_ql_foot
                        ) {
                            scope.launch {
                                navController.navigate(P4pScreens.Tracking.route)
                            }
                        }
                        QuoteOptionItem(title = "Payments", value = "", iconId = R.drawable.ic_ql_pound) {
                            scope.launch {
                                navController.navigate(P4pScreens.PaymentsList.route)
                            }
                        }
                        QuoteOptionItem(title = "Providers Details", value = "", iconId = R.drawable.ic_info) {
                            scope.launch {
                                //TODO to assign provider id
                                navController.navigate(P4pProviderScreens.ProviderProfile.route)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }

            BottomButtonContainer(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Column {
                    if(!showUpdateDelete.value){
                        FullRoundedButton(buttonText = "Send a Direct Inquiry") {
                            showUpdateDelete.value = true
                        }
                    }
                    AnimatedVisibility(
                        visible = showUpdateDelete.value,
                        enter = slideInVertically(tween(700))+ fadeIn(tween(700))
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FullRoundedButton(
                                modifier = Modifier.weight(.4f),
                                buttonText = "Delete",
                                backgroundColor = Red10,
                                onClick = {}
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FullRoundedButton(
                                modifier = Modifier.weight(1f),
                                buttonText = "Update",
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}