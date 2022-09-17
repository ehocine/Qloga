package eac.qloga.android.features.p4p.shared.scenes.verifyPhone

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.DotCircleArcCanvas
import eac.qloga.android.core.shared.components.DottedLine
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.grayTextColor
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.features.p4p.shared.components.enrollment.CodeInputField
import eac.qloga.android.features.p4p.shared.components.enrollment.CountryCodeList
import eac.qloga.android.features.p4p.shared.components.enrollment.PhoneNumberInputField
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.EnrollmentEvent
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun VerifyPhoneScreen(
    navController: NavController,
    viewModel: EnrollmentViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = 24.dp
    val maxCodeLen = 6
    val infoMsg = "Your verified mobile number is mandatory and " +
            "needed for maintaining communication with providers."
    val isCodeSent = viewModel.isCodeSent.value
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            viewModel.countryCodes.value?.let { countryCodes ->
                CountryCodeList(
                    selectCountryCode = viewModel.selectedCountryCode.value,
                    countryCodes = countryCodes,
                    onSelectCountryCode = {
                        viewModel.onTriggerEvent(EnrollmentEvent.SelectCountryCode(it))
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pScreens.VerifyPhone.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                ) {
                    navController.navigateUp()
                }
            }
        ) { paddingValue ->
            val topPadding = paddingValue.calculateTopPadding()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focusManager.clearFocus()
                            }
                        )
                    },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(topPadding + 4.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        ){
                            DotCircleArcCanvas(
                                arcStrokeColor = gray1,
                                circleColor = MaterialTheme.colorScheme.primary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .height(20.dp)
                                .align(Alignment.CenterEnd)
                                .padding(start = 20.dp)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            DottedLine(
                                arcStrokeColor = gray1,
                                vertical = false
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(modifier = Modifier.padding(horizontal = containerHorizontalPadding)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, gray1, RoundedCornerShape(16.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = infoMsg,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = containerHorizontalPadding)
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = "Country code",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Row(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    coroutineScope.launch {
                                        focusManager.clearFocus()
                                        modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }
                                .padding(8.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .alpha(.5f)
                                    .padding(end = 8.dp),
                                text = viewModel.selectedCountryCode.value.name + "(${viewModel.selectedCountryCode.value.dialCode})",
                                style = MaterialTheme.typography.titleSmall,
                                color = grayTextColor
                            )

                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Rounded.ArrowForwardIos,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    //number input
                    PhoneNumberInputField(
                        modifier = Modifier.padding(horizontal = containerHorizontalPadding),
                        onValueChange = { viewModel.onTriggerEvent(EnrollmentEvent.EnterNumber(it))},
                        isFocused = viewModel.numberFieldState.value.isFocused,
                        onSubmit = { viewModel.onTriggerEvent(EnrollmentEvent.SendCode) },
                        hint = viewModel.numberFieldState.value.hint,
                        value = viewModel.numberFieldState.value.text,
                        onFocusedChanged = { viewModel.onTriggerEvent(
                            EnrollmentEvent.FocusNumberInput(it)
                        )}
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    //send code button
                    FullRoundedButton(
                        modifier = Modifier.padding(horizontal = containerHorizontalPadding),
                        buttonText = "Send code", textColor = Color.White,
                        backgroundColor = orange1,
                        enabled = viewModel.numberFieldState.value.text.isNotEmpty()
                    ) {
                        viewModel.onTriggerEvent(EnrollmentEvent.SendCode)
                        keyboardController?.hide()
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedVisibility(visible = isCodeSent) {
                        CodeInputField(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = containerHorizontalPadding),
                            onValueChange = { viewModel.onTriggerEvent(EnrollmentEvent.EnterCode(it))},
                            onSubmit = { viewModel.onTriggerEvent(EnrollmentEvent.SubmitCode) },
                            value = viewModel.codeState.value.text,
                            hint = "Enter 6-digits code",
                            maxLength = maxCodeLen,
                            isFocused = viewModel.codeState.value.isFocused,
                            onFocusedChanged = {
                                coroutineScope.launch {
                                    viewModel.onTriggerEvent(EnrollmentEvent.FocusCodeInput(it))
                                    // delaying for 300 milli sec because scroll only after keyboard is
                                    // fully visible and layout resize is happened already otherwise
                                    // no any scroll effect will happen
                                    delay(300)
                                    scrollState.animateScrollTo(scrollState.maxValue)
                                }
                            }
                        )
                    }
                    Box(modifier = Modifier.height(54.dp))
                }
                //next button
                FullRoundedButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(vertical = 16.dp, horizontal = containerHorizontalPadding),
                    buttonText = "Next",
                    textColor = MaterialTheme.colorScheme.primary,
                    borderColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = Color.Transparent,
                    showBorder = true,
                ) {
                    coroutineScope.launch{
                        navController.navigate(P4pScreens.ConfirmAddress.route)
                        keyboardController?.hide()
                    }
                }
            }
        }
    }
}