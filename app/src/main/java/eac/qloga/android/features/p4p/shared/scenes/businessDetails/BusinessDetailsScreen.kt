package eac.qloga.android.features.p4p.shared.scenes.businessDetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.SaveButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.TextInputFieldAlbum
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun BusinessDetailsScreen(
    navController: NavController,
    viewModel: AccountSettingsViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val keyboardController = LocalSoftwareKeyboardController.current
    val descriptionDetailsState = viewModel.businessDescriptionState.value
    val insuranceDetailsState = viewModel.businessInsuranceState.value
    val registrationDetailsState = viewModel.registrationDetailsState.value

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.BusinessDetails.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions = {
                    SaveButton(
                        onClick = {
                            coroutineScope.launch {
                                navController.navigateUp()
                            }
                        },
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

                DescriptionEditField(
                    descriptionDetailsState = descriptionDetailsState,
                    onEnter = { viewModel.onTriggerEvent(AccountSettingsEvent.EnterDescription(it))},
                    onFocus = { viewModel.onTriggerEvent(AccountSettingsEvent.FocusDescription(it))}
                )
                RegisterEditField(
                    registrationDetailsState = registrationDetailsState,
                    onEnter = { viewModel.onTriggerEvent(AccountSettingsEvent.EnterRegistrationDetails(it))},
                    onFocus = { viewModel.onTriggerEvent(AccountSettingsEvent.FocusRegistrationDetails(it))}
                )
                InsuranceEditField(
                    insuranceDetailsState = insuranceDetailsState,
                    onEnter = { viewModel.onTriggerEvent(AccountSettingsEvent.EnterBusinessInsurance(it))},
                    onFocus = { viewModel.onTriggerEvent(AccountSettingsEvent.FocusBusinessInsurance(it))}
                )
            }
        }
    }
}

@Composable
fun RegisterEditField(
    modifier: Modifier = Modifier,
    registrationDetailsState: InputFieldState,
    onEnter: (String) -> Unit,
    onFocus: (FocusState) -> Unit
){
    val inputFieldHeight = 100.dp

    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "REGISTRATION DETAILS", style = MaterialTheme.typography.titleMedium, color = gray30)
        Spacer(Modifier.height(4.dp))
        TextInputFieldAlbum(
            value = registrationDetailsState.text,
            hint = registrationDetailsState.hint,
            height = inputFieldHeight,
            singleLine = false,
            isFocused = registrationDetailsState.isFocused,
            contentVerticalAlignment = Alignment.Top,
            onValueChange = { onEnter(it) },
            onFocusedChanged ={ onFocus(it) }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun DescriptionEditField(
    modifier: Modifier = Modifier,
    descriptionDetailsState: InputFieldState,
    onEnter: (String) -> Unit,
    onFocus: (FocusState) -> Unit
){
    val inputFieldHeight = 180.dp

    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "DESCRIPTION", style = MaterialTheme.typography.titleMedium, color = gray30)
        Spacer(Modifier.height(4.dp))
        TextInputFieldAlbum(
            modifier = Modifier,
            value = descriptionDetailsState.text,
            hint = descriptionDetailsState.hint,
            height = inputFieldHeight,
            singleLine = false,
            isFocused = descriptionDetailsState.isFocused,
            contentVerticalAlignment = Alignment.Top,
            onValueChange = { onEnter(it) },
            onFocusedChanged ={ onFocus(it) }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun InsuranceEditField(
    modifier: Modifier = Modifier,
    insuranceDetailsState: InputFieldState,
    onEnter: (String) -> Unit,
    onFocus: (FocusState) -> Unit
){
    val inputFieldHeight = 100.dp

    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "BUSINESS INSURANCE DETAILS", style = MaterialTheme.typography.titleMedium, color = gray30)
        Spacer(Modifier.height(4.dp))
        TextInputFieldAlbum(
            modifier = Modifier,
            value = insuranceDetailsState.text,
            hint = insuranceDetailsState.hint,
            height = inputFieldHeight,
            singleLine = false,
            isFocused = insuranceDetailsState.isFocused,
            contentVerticalAlignment = Alignment.Top,
            onValueChange = { onEnter(it) },
            onFocusedChanged ={ onFocus(it) }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
