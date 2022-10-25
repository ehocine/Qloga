package eac.qloga.android.features.p4p.shared.scenes.settingsEmail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.EmailInputField
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.EmailValidator
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent.*
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SettingsEmailScreen(
    navController: NavController,
    viewModel: AccountSettingsViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val keyboardController = LocalSoftwareKeyboardController.current
    val email = if(AccountSettingsViewModel.accountType == AccountType.CUSTOMER) {
        AccountSettingsViewModel.emailInputFieldState
    }else {
        AccountSettingsViewModel.orgEmailInputFieldState
    }

    val isValidEmail by derivedStateOf {
        EmailValidator.isValidEmail(email.text)
    }

    val borderColor by derivedStateOf {
        when(true){
            !isValidEmail -> Red10
            (email.isFocused && isValidEmail) -> green1
            else -> gray1
        }
    }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.SettingsEmail.titleName,
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

                EmailInputField(
                    value = email.text,
                    hint = email.hint,
                    isFocused = email.isFocused,
                    borderColor = borderColor,
                    onValueChange = {
                        if(AccountSettingsViewModel.accountType == AccountType.CUSTOMER){
                            viewModel.onTriggerEvent(EnterEmail(it))
                        }else{
                            viewModel.onTriggerEvent(EnterOrgsEmail(it))
                        }
                    },
                    onSubmit = {
                        if(AccountSettingsViewModel.accountType == AccountType.CUSTOMER){
                            viewModel.onTriggerEvent(SubmitEmail)
                        }else{
                            viewModel.submitOrgEmail()
                        }
                    },
                    onFocusedChanged = {
                        if(AccountSettingsViewModel.accountType == AccountType.CUSTOMER){
                            viewModel.onTriggerEvent(FocusEmailInput(it))
                        }else{
                            viewModel.onTriggerEvent(FocusOrgsEmail(it))
                        }
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                //send code button
                FullRoundedButton(
                    buttonText = "Send verification email", textColor = Color.White,
                    backgroundColor = orange1,
                    enabled = email.text.isNotEmpty() && isValidEmail
                ) {
                    coroutineScope.launch {
                        if(AccountSettingsViewModel.accountType == AccountType.CUSTOMER){
                            viewModel.onTriggerEvent(SubmitEmail)
                        }else{
                            viewModel.submitOrgEmail()
                        }
                        keyboardController?.hide()
                    }
                }
                Spacer(Modifier.height(containerTopPadding))
            }
        }
    }
}