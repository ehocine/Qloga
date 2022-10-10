package eac.qloga.android.features.p4p.customer.scenes.accountSettings

import P4pCustomerScreens
import android.os.Build
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.PickerDialog
import eac.qloga.android.features.p4p.shared.components.AccSettingsListItem
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerAccountSettingsScreen(
    navController: NavController,
    viewModel: AccountSettingsViewModel = hiltViewModel(),
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val spokenLanguages = viewModel.spokenLanguageState.value

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        viewModel.setAccountType(AccountType.CUSTOMER)
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pCustomerScreens.CustomerAccountSettings.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    Buttons.SaveButton(onClick = {
                        coroutineScope.launch {
                            navController.navigateUp()
                            viewModel.onTriggerEvent(AccountSettingsEvent.SaveCustomerAccountSettings)
                        }
                    }, textColor = MaterialTheme.colorScheme.primary)
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
                            hint = viewModel.customerFirstName.value.hint,
                            value = viewModel.customerFirstName.value.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = {viewModel.onTriggerEvent( AccountSettingsEvent.EnterCustomerFirstName(it))},
                            onFocusChange = {viewModel.onTriggerEvent(AccountSettingsEvent.FocusCustomerFirstName(it))}
                        )
                        AccSettingsListItem(
                            hint = viewModel.middleName.value.hint,
                            value = viewModel.middleName.value.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = { viewModel.onTriggerEvent(AccountSettingsEvent.EnterMiddleName(it))},
                            onFocusChange = { viewModel.onTriggerEvent(AccountSettingsEvent.FocusCoverageZone(it))}
                        )
                        AccSettingsListItem(
                            hint = viewModel.customerLastName.value.hint,
                            value = viewModel.customerLastName.value.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = {viewModel.onTriggerEvent( AccountSettingsEvent.EnterCustomerLastName(it))},
                            onFocusChange = {viewModel.onTriggerEvent(AccountSettingsEvent.FocusCustomerLastName(it))}
                        )
                        AccSettingsListItem(
                            hint = viewModel.maidenName.value.hint,
                            value = viewModel.maidenName.value.text,
                            editable = true,
                            keyboardType = KeyboardType.Text,
                            onValueChange = {viewModel.onTriggerEvent( AccountSettingsEvent.EnterMaidenName(it))},
                            onFocusChange = {viewModel.onTriggerEvent(AccountSettingsEvent.FocusMaidenName(it))}
                        )
                        BirthdaySettingsItem(
                            date = viewModel.birthday.value,
                            onPickDate = { viewModel.onTriggerEvent(
                                AccountSettingsEvent.SelectBirthday(
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
//                                    navController.navigate(Screen.AccountPhoneVerify.route) {
//                                        launchSingleTop = true
//                                    }
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Email",
                            value = "orgpork@gmail.com",
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
//                                    navController.navigate(Screen.AccountEmailVerify.route) {
//                                        launchSingleTop = true
//                                    }
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Address",
                            value = viewModel.listOfAddress.value[viewModel.selectedAddressIndex.value],
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
//                                    navController.navigate(Screen.AccountSettingsAddress.route) {
//                                        launchSingleTop = true
//                                    }
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Spoken language",
                            value = viewModel.spokenLanguageString(spokenLanguages.filter { it.isSelected }),
                            clickable = true,
                            onClick = {
                                coroutineScope.launch {
//                                    navController.navigate(Screen.LanguageSelection.route) {
//                                        launchSingleTop = true
//                                    }
                                }
                            }
                        )
                        AccSettingsListItem(
                            title = "Verifications",
                            value = "ID, Phone, Address",
                            clickable = true,
                            onClick = {
//                                coroutineScope.launch {
//                                    navController.navigate(Screen.Verifications.route)
//                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        modifier = Modifier.alpha(.75f),
                        text = "FAMILY STATUS",
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )
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
                                    text = "Families",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = gray30
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(end = 4.dp),
                                    text = "Stokes",
                                    style = MaterialTheme.typography.titleMedium
                                )

                            }
                            DividerLines.LightDividerLine(Modifier.padding(start = 16.dp))
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
                                    text = "Roles",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = gray30
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(end = 4.dp),
                                    text = "Brother",
                                    style = MaterialTheme.typography.titleMedium,
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

@RequiresApi(Build.VERSION_CODES.O)
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
                            PickerDialog.showDatePickerDialog(context, onSetDate = {
                                onPickDate(it)
                            })
                        }
                    }
                    .background(gray1)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = date.ifEmpty { "11/2/22" },
                    style = MaterialTheme.typography.titleMedium,
                    color = if (date.isEmpty()) Color.White else MaterialTheme.colorScheme.primary
                )
            }
        }
        DividerLines.LightDividerLine(Modifier.padding(start = 16.dp))
    }
}