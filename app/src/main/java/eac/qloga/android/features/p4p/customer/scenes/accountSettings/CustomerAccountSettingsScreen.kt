package eac.qloga.android.features.p4p.customer.scenes.accountSettings

import P4pCustomerScreens
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.*
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.AccSettingsListItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.verifications.VerificationsViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerAccountSettingsScreen(
    navController: NavController,
    viewModel: AccountSettingsViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val spokenLanguages = AccountSettingsViewModel.spokenLanguageState
    val userProfileLoadingState = apiViewModel.userProfileLoadingState.collectAsState()
    val savingState by viewModel.savingState.collectAsState()

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = userProfileLoadingState.value ){
        if(userProfileLoadingState.value == LoadingState.LOADED){
            viewModel.preLoadCalls()
        }
    }

    LaunchedEffect(Unit){
        viewModel.setAccType(AccountType.CUSTOMER)
        apiViewModel.getUserProfile()
        viewModel.setInitialStates()
        viewModel.getCountries()
    }

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.msg, Toast.LENGTH_LONG).show()
                }
                is UiEvent.NavigateBack -> { navController.navigateUp() }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pCustomerScreens.CustomerAccountSettings.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    Buttons.SaveButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.SaveCustomerAccountSettings
                                )
                            }
                        },
                        isLoading = savingState == LoadingState.LOADING,
                        textColor = MaterialTheme.colorScheme.primary
                    )
                }
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

                ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AccSettingsListItem(
                            hint = viewModel.fName.hint,
                            value = viewModel.fName.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = {viewModel.onTriggerEvent( AccountSettingsEvent.EnterCustomerFirstName(it))},
                            onFocusChange = {viewModel.onTriggerEvent(AccountSettingsEvent.FocusCustomerFirstName(it))}
                        )
                        AccSettingsListItem(
                            hint = viewModel.middleName.hint,
                            value = viewModel.middleName.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = { viewModel.onTriggerEvent(AccountSettingsEvent.EnterMiddleName(it))},
                            onFocusChange = { viewModel.onTriggerEvent(AccountSettingsEvent.FocusCoverageZone(it))}
                        )
                        AccSettingsListItem(
                            hint = viewModel.lastName.hint,
                            value = viewModel.lastName.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = {viewModel.onTriggerEvent( AccountSettingsEvent.EnterCustomerLastName(it))},
                            onFocusChange = {viewModel.onTriggerEvent(AccountSettingsEvent.FocusCustomerLastName(it))}
                        )
                        AccSettingsListItem(
                            hint = viewModel.maidenName.hint,
                            value = viewModel.maidenName.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = {viewModel.onTriggerEvent( AccountSettingsEvent.EnterMaidenName(it))},
                            onFocusChange = {viewModel.onTriggerEvent(AccountSettingsEvent.FocusMaidenName(it))}
                        )
                        BirthdaySettingsItem(
                            date = viewModel.birthday,
                            onPickDate = {
                                viewModel.onTriggerEvent(
                                    AccountSettingsEvent.SelectBirthday( it )
                                )
                            }
                        )
                        AccSettingsListItem(
                            title = "Phone",
                            value = AccountSettingsViewModel.phoneNumberFieldState.text,
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.SettingsPhone.route) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Email",
                            value = AccountSettingsViewModel.emailInputFieldState.text,
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.SettingsEmail.route) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Address",
                            value = viewModel.listOfAddress[viewModel.selectedAddressIndex],
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pProviderScreens.AddPrvAddress.route) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Spoken language",
                            value = viewModel.spokenLanguageString(spokenLanguages),
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.SettingsLanguage.route) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        )
                        if(viewModel.verifications.isNotEmpty()){
                            AccSettingsListItem(
                                title = "Verifications",
                                value = viewModel.verifications,
                                clickable = true,
                                onClick = {
                                    coroutineScope.launch {
                                        VerificationsViewModel.accountType = AccountType.CUSTOMER
                                        VerificationsViewModel.verifications.value =
                                            ApiViewModel.userProfile.value.verifications ?: emptyList()
                                        navController.navigate(P4pScreens.Verifications.route)
                                    }
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(containerTopPadding))
            }
        }
    }
}

@Composable
private fun BirthdaySettingsItem(
    date: String,
    onPickDate: (String) -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val chipWidth = 90.dp

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Birthday",
                style = MaterialTheme.typography.titleMedium,
                color = gray30
            )
            Box(
                modifier = Modifier
                    .widthIn(min = chipWidth)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        coroutineScope.launch {
                            PickerDialog.showDatePickerDialog(
                                context,
                                onSetDate = { onPickDate(it) }
                            )
                        }
                    }
                    .background(gray1)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = date.ifEmpty { "Select" },
                    style = MaterialTheme.typography.titleMedium,
                    color = if (date.isEmpty()) Color.White else MaterialTheme.colorScheme.primary
                )
            }
        }
        DividerLines.LightDividerLine(Modifier.padding(start = 16.dp))
    }
}