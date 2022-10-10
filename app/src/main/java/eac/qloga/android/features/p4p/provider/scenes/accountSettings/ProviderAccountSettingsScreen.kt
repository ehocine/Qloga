package eac.qloga.android.features.p4p.provider.scenes.accountSettings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
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
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.AccSettingsListItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent.EnterRegistrationDetails
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderAccountSettingsScreen(
    navController: NavController,
    viewModel: AccountSettingsViewModel = hiltViewModel(),
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val phoneSwitch = viewModel.phoneSwitch.value
    val timeOffSwitch = viewModel.timeOffSwitch.value
    val activeSwitch = viewModel.activeSwitch.value
    val calloutChargeSwitch = viewModel.calloutChargeSwitch
    val hideAll = viewModel.hideAll.value
    val spokenLanguages = viewModel.spokenLanguageState.value
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.ProviderAccountSettings.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    Buttons.SaveButton(
                        onClick = {
                            coroutineScope.launch {
                                navController.navigateUp()
                                //viewModel.onTriggerEvent(SaveProviderAccountSettings)
                            }
                        },
                        textColor = MaterialTheme.colorScheme.primary
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
                                    checked = false,
                                    onCheckedChange = { /*viewModel.onActiveSwitch()*/ },
                                    colors = SwitchDefaults.colors(
                                        uncheckedBorderColor = gray1,
                                        uncheckedTrackColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                            LightDividerLine(Modifier.padding(start = 16.dp))
                        }

                        AccSettingsListItem(
                            hint = viewModel.nameSurname.value.hint,
                            value = viewModel.nameSurname.value.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.EnterNameSurname(
                                    it
                                )
                            ) },
                            onFocusChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.FocusNameSurname(
                                    it
                                )
                            ) }
                        )

                        AccSettingsListItem(
                            title = "Description",
                            value = viewModel.businessDescriptionState.value.text.ifEmpty {
                                viewModel.businessDescriptionState.value.hint
                            },
                            editable = false,
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
//                                    navController.navigate(Screen.BusinessDetails.route){
//                                        launchSingleTop = true
//                                        popUpTo(Screen.ProviderAccountSettings.route)
//                                    }
                                }
                            },
                            keyboardType = KeyboardType.Text,
                            onValueChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.EnterDescription(
                                    it
                                )
                            ) },
                            onFocusChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.FocusDescription(
                                    it
                                )
                            ) }
                        )

                        AccSettingsListItem(
                            title = "Phone",
                            value = "+44 123456789",
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.SettingsPhone.route)
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Email",
                            value = "orgpork@gmail.com",
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.SettingsEmail.route)
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Address",
                            value = viewModel.listOfAddress.value[viewModel.selectedAddressIndex.value],
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pProviderScreens.AddPrvAddress.route)
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Spoken language",
                            value = viewModel.spokenLanguageString(spokenLanguages.filter { it.isSelected }),
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
                                    checked = calloutChargeSwitch.value,
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
                            title = if(viewModel.cancellationPeriod.value.text.isNotEmpty()){
                                viewModel.cancellationPeriod.value.hint
                            }else null,
                            hint = viewModel.cancellationPeriod.value.hint,
                            value = viewModel.cancellationPeriod.value.text,
                            editable = true,
                            keyboardType = KeyboardType.Number,
                            onValueChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.EnterCancellationPeriod(
                                    it
                                )
                            ) },
                            onFocusChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.FocusCancellationPeriod(
                                    it
                                )
                            ) }
                        )

                        AccSettingsListItem(
                            title = if(viewModel.coverageZone.value.text.isNotEmpty()){
                                viewModel.coverageZone.value.hint
                            }else null,
                            hint = viewModel.coverageZone.value.hint,
                            value = viewModel.coverageZone.value.text,
                            editable = true,
                            keyboardType = KeyboardType.Number,
                            onValueChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.EnterCoverageZone(it)
                            ) },
                            onFocusChange = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.FocusCoverageZone(it)
                            ) }
                        )

                        AccSettingsListItem(
                            title = "Verifications",
                            value = "ID, Phone, Address, Insurance",
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.Verifications.route)
                                }
                            }
                        )

                        AccSettingsListItem(
                            title = if(viewModel.website.value.text.isNotEmpty()){
                                viewModel.website.value.hint
                            }else null,
                            hint = viewModel.website.value.hint,
                            value = viewModel.website.value.text,
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
                            editable = false,
                            clickable = true,
                            title = "Registration detials",
                            value = viewModel.registrationDetailsState.value.text.ifEmpty {
                                viewModel.registrationDetailsState.value.hint
                            },
                            keyboardType = KeyboardType.Text,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.BusinessDetails.route)
                                }
                            },
                            onValueChange = { viewModel.onTriggerEvent(EnterRegistrationDetails(it)
                            ) },
                            onFocusChange = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.FocusRegistrationDetails(it)
                                )
                            }
                        )

                        AccSettingsListItem(
                            title = "Business insurance details",
                            value = viewModel.businessInsuranceState.value.text.ifEmpty {
                                viewModel.businessInsuranceState.value.hint
                            },
                            editable = false,
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.BusinessDetails.route)
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

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "VISIBLE DETAILS",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { viewModel.onHideAll() }
                            .padding(4.dp)
                    ) {
                        Text(
                            text = if(!hideAll) "HIDE ALL" else "VISIBLE ALL",
                            style = MaterialTheme.typography.titleMedium,
                            color = gray30
                        )
                    }
                }
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
                                    text = "Phone",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Switch(
                                    modifier = Modifier.height(24.dp),
                                    checked = phoneSwitch,
                                    onCheckedChange = { viewModel.onPhoneSwitch() },
                                    colors = SwitchDefaults.colors(
                                        uncheckedBorderColor = gray1,
                                        uncheckedTrackColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                            LightDividerLine(Modifier.padding(start = 16.dp))
                        }
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
                                    text = "Off-time",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Switch(
                                    modifier = Modifier.height(24.dp),
                                    checked = timeOffSwitch,
                                    onCheckedChange = { viewModel.onTimeOffSwitch()},
                                    colors = SwitchDefaults.colors(
                                        uncheckedBorderColor = gray1,
                                        uncheckedTrackColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(containerTopPadding))
            }
        }
    }
}