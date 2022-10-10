package eac.qloga.android.features.p4p.shared.scenes.quote

import P4pCustomerScreens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.Containers.BottomButtonContainer
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.shared.components.QuoteOptionEditableItem
import eac.qloga.android.features.p4p.shared.components.QuoteOptionItem
import eac.qloga.android.features.p4p.shared.components.SelectedServicesItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.QuoteBottomBtnState
import eac.qloga.android.features.p4p.shared.viewmodels.ServiceViewModel
import eac.qloga.android.features.p4p.shared.viewmodels.AddressViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val totalSum = 450
    val selectedServices = viewModel.selectedCleaningCategories.value
    val calloutChargeSwitch = remember{ mutableStateOf(false) }
    val cancellationPeriod = remember{ mutableStateOf(InputFieldState(text = "12")) }
    val btnState = viewModel.quoteBottomBtnState.value

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(selectedServices){
        val totalCount = selectedServices.count { it.value.count > 0 }
        if(totalCount <= 0){
            viewModel.onChangeQuoteBottomBtnState(QuoteBottomBtnState.EMPTY_SERVICE)
        }
        if(totalCount > 0 && btnState == QuoteBottomBtnState.EMPTY_SERVICE){
            viewModel.onChangeQuoteBottomBtnState(QuoteBottomBtnState.SEND_QUOTE)
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.Quote.titleName,
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

                            // show the services which have count more than 0
                            if(cleaningType.value.count > 0){
                                SelectedServicesItem(
                                    title = cleaningType.value.title,
                                    label = "Rate (£/hour): 21.00",
                                    count = cleaningType.value.count
                                ) {
                                    viewModel.setSelectedServiceId(index)
//                                    navController.navigate(
//                                        Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.Quote.route}&$ID_KEY=$index"
//                                    )
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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

                //bottom card
                Spacer(modifier = Modifier.height(24.dp))

                ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                            ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 8.dp),
                                text = "Callout charge",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Switch(
                                checked = calloutChargeSwitch.value,
                                onCheckedChange = {
                                    calloutChargeSwitch.value = !calloutChargeSwitch.value
                                },
                                colors = SwitchDefaults.colors(
                                    uncheckedBorderColor = gray1,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                        DividerLines.LightDividerLine(Modifier.padding(start = 64.dp))
                        QuoteOptionEditableItem(
                            value = cancellationPeriod.value.text,
                            label = "Cancellation period(hours)",
                            showDividerLine = true,
                            leadingIcon = {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.ic_no_shake_hnd),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            onChangeValue = { cancellationPeriod.value = cancellationPeriod.value.copy(text = it) } ,
                            onFocusChange = {
                                scope.launch {
                                    cancellationPeriod.value = cancellationPeriod.value.copy(isFocused = it.isFocused)
                                    delay(300)
                                    if(cancellationPeriod.value.isFocused){
                                        scrollState.animateScrollTo(scrollState.maxValue)
                                    }
                                }
                            }
                        )
                        QuoteOptionItem(
                            title = "Address",
                            value = addressViewModel.selectedAddress.value,
                            iconId = R.drawable.ic_ql_home
                        ) {
                            scope.launch {
                                navController.navigate(P4pScreens.SelectAddress.route)
                            }
                        }

                        QuoteOptionItem(title = "Visits", value = "8", enable = true, iconId = R.drawable.ic_ql_flag) {
                            scope.launch {
//                                navController.navigate( Screen.Visits.route )
                            }
                        }
                        QuoteOptionItem(title = "Tracking", value = "", enable = true, iconId = R.drawable.ic_ql_foot) {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.Tracking.route+"?$PARENT_ROUTE_KEY=${Screen.Quote.route}"
//                                )
                            }
                        }
                        QuoteOptionItem(title = "Customers details", value = "", iconId = R.drawable.ic_info) {
                            scope.launch {
                                //TODO assign customer data to viewModel
                                navController.navigate(P4pCustomerScreens.CustomerProfile.route)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(144.dp))
            }

            //Send inquiry button
            BottomButtonContainer(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                AnimatedVisibility(
                    visible = QuoteBottomBtnState.EMPTY_SERVICE == btnState,
                    enter = slideInVertically(tween(700)) + fadeIn(tween(700)),
                    exit = fadeOut()
                ) {
                    FullRoundedButton(
                        buttonText = "Send Quote",
                        backgroundColor = gray1
                    ) {}
                }
                AnimatedVisibility(
                    visible = btnState == QuoteBottomBtnState.SEND_QUOTE,
                    enter = slideInVertically(tween(700)) + fadeIn(tween(700)),
                    exit = fadeOut()
                ) {
                    FullRoundedButton(
                        buttonText = "Send Quote",
                        backgroundColor = MaterialTheme.colorScheme.primary
                    ) {
                        viewModel.onChangeQuoteBottomBtnState(QuoteBottomBtnState.ACCEPT)
                    }
                }

                AnimatedVisibility(
                    visible = btnState == QuoteBottomBtnState.UPDATE,
                    enter = slideInVertically(tween(700)) + fadeIn(tween(700)),
                    exit = fadeOut()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        FullRoundedButton(
                            buttonText = "Update",
                            onClick = {}
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FullRoundedButton(
                            buttonText = "Delete",
                            backgroundColor = Red10,
                            onClick = {}
                        )
                    }
                }

                AnimatedVisibility(
                    visible = btnState == QuoteBottomBtnState.ACCEPT,
                    enter = slideInVertically(tween(700)) + fadeIn(tween(700)),
                    exit = fadeOut()
                ) {
                    Column {
                        FullRoundedButton(
                            buttonText = "Accept",
                            onClick = {}
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FullRoundedButton(
                                modifier = Modifier.weight(.4f),
                                buttonText = "Decline",
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