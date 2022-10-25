package eac.qloga.android.features.p4p.shared.scenes.settingsPhone

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.components.enrollment.CodeInputField
import eac.qloga.android.features.p4p.shared.components.enrollment.PhoneNumberInputField
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SettingsPhoneScreen(
    navController: NavController,
    viewModel: AccountSettingsViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val keyboardController = LocalSoftwareKeyboardController.current
    val isCodeSent = viewModel.isCodeSent
    val maxCodeLen = 6
    val phone = AccountSettingsViewModel.phoneNumberFieldState

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.SettingsPhone.titleName,
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
                .padding(horizontal = containerHorizontalPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                Cards.ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .animateContentSize()
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ////number input
                        PhoneNumberInputField(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            onValueChange = { viewModel.onTriggerEvent(AccountSettingsEvent.EnterNumber(it))},
                            isFocused = phone.isFocused,
                            onSubmit = { viewModel.onTriggerEvent(AccountSettingsEvent.SendCode) },
                            hint = phone.hint,
                            value = phone.text,
                            showBottomLine = false,
                            onFocusedChanged = { viewModel.onTriggerEvent(AccountSettingsEvent.FocusNumberInput(it))}
                        )
                        DividerLine(
                            Modifier
                                .padding(start = 44.dp)
                                .alpha(.5f))
                        CodeInputField(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            onValueChange = { viewModel.onTriggerEvent(AccountSettingsEvent.EnterCode(it))},
                            onSubmit = { viewModel.onTriggerEvent(AccountSettingsEvent.SubmitCode) },
                            value = viewModel.codeState.text,
                            hint = viewModel.codeState.hint,
                            showBottomLine = false,
                            maxLength = maxCodeLen,
                            onFocusedChanged = { viewModel.onTriggerEvent(AccountSettingsEvent.FocusCodeInput(it))}
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                //send code button
                FullRoundedButton(
                    buttonText = if(!isCodeSent) "Send code" else "Submit code", textColor = Color.White,
                    backgroundColor = orange1,
                    enabled = phone.text.isNotEmpty() || viewModel.codeState.text.length == maxCodeLen
                ) {
                    coroutineScope.launch {
                        if(!isCodeSent) {
                            viewModel.onTriggerEvent(AccountSettingsEvent.SendCode)
                        }else{
                            viewModel.onTriggerEvent(AccountSettingsEvent.SubmitCode)
                        }
                        keyboardController?.hide()
                    }
                }
                Spacer(Modifier.height(containerTopPadding))
            }
        }
    }
}