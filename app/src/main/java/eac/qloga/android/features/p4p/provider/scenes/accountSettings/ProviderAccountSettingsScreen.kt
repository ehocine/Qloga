package eac.qloga.android.features.p4p.provider.scenes.accountSettings

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.DividerLines.LightDividerLine
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.UiEvent
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.AccSettingsListItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.verifications.VerificationsViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent.SaveProviderAccountSettings
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderAccountSettingsScreen(
    navController: NavController,
    viewModel: AccountSettingsViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val activeSwitch = viewModel.activeSwitch
    val calloutChargeSwitch = viewModel.calloutChargeSwitch
    val spokenLanguages = AccountSettingsViewModel.spokenLanguageProvider
    val focusManager = LocalFocusManager.current
    val savingState by viewModel.savingState.collectAsState()

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.msg, Toast.LENGTH_LONG).show()
                }
                is UiEvent.NavigateBack -> { navController.navigateUp()}
                else -> {}
            }
        }
    }

    LaunchedEffect(key1 = Unit, key2 = savingState){
        viewModel.setInitialStates()
        viewModel.getCountries()
        if(savingState != LoadingState.LOADING){
            apiViewModel.getOrgs()
            apiViewModel.getUserProfile()
            apiViewModel.getProvider()
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.ProviderAccountSettings.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    Buttons.SaveButton(
                        onClick = {
                            viewModel.onTriggerEvent(SaveProviderAccountSettings)
                        },
                        textColor = MaterialTheme.colorScheme.primary,
                        isLoading = savingState == LoadingState.LOADING
                    )
                },
                onBackPress = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { focusManager.clearFocus() }
                    )
                }
                .padding(horizontal = containerHorizontalPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                ContainerBorderedCard {
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
                                    .padding(horizontal = 16.dp, vertical = 14.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.dp),
                                    text = "Active",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Switch(
                                    modifier = Modifier.height(24.dp),
                                    checked = activeSwitch,
                                    onCheckedChange = { viewModel.onActiveSwitch() },
                                    colors = SwitchDefaults.colors(
                                        uncheckedBorderColor = gray1,
                                        uncheckedTrackColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                            LightDividerLine(Modifier.padding(start = 16.dp))
                        }

                        AccSettingsListItem(
                            hint = viewModel.orgName.hint,
                            value = viewModel.orgName.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.EnterOrgName(it)
                                )
                            },
                            onFocusChange = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.FocusOrgName(it)
                                )
                            }
                        )

                        AccSettingsListItem(
                            title = "Phone",
                            value = AccountSettingsViewModel.phoneNumberFieldState.text,
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.SettingsPhone.route)
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Email",
                            value = AccountSettingsViewModel.orgEmailInputFieldState.text,
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.SettingsEmail.route)
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Address",
                            value = viewModel.listOfAddress[viewModel.selectedAddressIndex],
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pProviderScreens.AddPrvAddress.route)
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Spoken language",
                            value = viewModel.spokenLanguageString(spokenLanguages),
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.SettingsLanguage.route)
                                }
                            }
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Row(
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 14.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.dp),
                                    text = "Callout charge",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Switch(
                                    modifier = Modifier.height(24.dp),
                                    checked = calloutChargeSwitch,
                                    onCheckedChange = { viewModel.onCalloutChargeSwitch()},
                                    colors = SwitchDefaults.colors(
                                        uncheckedBorderColor = gray1,
                                        uncheckedTrackColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                            LightDividerLine(Modifier.padding(start = 16.dp))
                        }

                        AccSettingsListItem(
                            title = if(viewModel.cancellationPeriod.text.isNotEmpty()){
                                viewModel.cancellationPeriod.hint
                            }else null,
                            hint = viewModel.cancellationPeriod.hint,
                            value = viewModel.cancellationPeriod.text,
                            editable = true,
                            keyboardType = KeyboardType.Number,
                            onValueChange = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.EnterCancellationPeriod( it )
                                )
                            },
                            onFocusChange = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.FocusCancellationPeriod( it )
                                )
                            }
                        )

                        AccSettingsListItem(
                            title = if(viewModel.coverageZone.text.isNotEmpty()){
                                viewModel.coverageZone.hint
                            }else null,
                            hint = viewModel.coverageZone.hint,
                            value = viewModel.coverageZone.text,
                            editable = true,
                            keyboardType = KeyboardType.Number,
                            onValueChange = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.EnterCoverageZone(it)
                                )
                            },
                            onFocusChange = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.FocusCoverageZone(it)
                                )
                            }
                        )

                        if(viewModel.orgVerifications.isNotEmpty()){
                            AccSettingsListItem(
                                title = "Verifications",
                                value = viewModel.orgVerifications,
                                clickable = true,
                                onClick = {
                                    VerificationsViewModel.accountType = AccountType.PROVIDER
                                    VerificationsViewModel.verifications.value = ApiViewModel.orgs[0].verifications
                                    navController.navigate(P4pScreens.Verifications.route)
                                }
                            )
                        }

                        AccSettingsListItem(
                            title = if(viewModel.website.text.isNotEmpty()){
                                viewModel.website.hint
                            }else null,
                            hint = viewModel.website.hint,
                            value = viewModel.website.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.EnterWebsite(it)
                            ) },
                            onFocusChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.FocusWebsite(it)
                            ) }
                        )

                        AccSettingsListItem(
                            title = "Business details",
                            value = "Description, Registration Details",
                            editable = false,
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.BusinessDetails.route){
                                        popUpTo(P4pProviderScreens.ProviderAccountSettings.route)
                                    }
                                }
                            },
                            keyboardType = KeyboardType.Text,
                            onValueChange = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.EnterBusinessInsurance(it)
                                )
                            },
                            onFocusChange = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.FocusBusinessInsurance( it )
                                )
                            }
                        )
                    }
                }
                Spacer(Modifier.height(containerTopPadding))
            }
        }
    }
}