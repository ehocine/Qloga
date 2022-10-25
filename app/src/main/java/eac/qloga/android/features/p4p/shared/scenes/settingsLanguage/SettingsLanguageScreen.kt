package eac.qloga.android.features.p4p.shared.scenes.settingsLanguage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.components.LanguageOptions
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.viewmodels.AccountSettingsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsLanguageScreen(
    navController: NavController,
    viewModel: AccountSettingsViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val optionLanguages = AccountSettingsViewModel.optionLanguages.collectAsState().value
    val spokenLanguages = if(AccountSettingsViewModel.accountType == AccountType.CUSTOMER){
        AccountSettingsViewModel.spokenLanguageState
    }else AccountSettingsViewModel.spokenLanguageProvider

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.SettingsLanguage.titleName,
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
                if(optionLanguages.isNotEmpty()){
                    LanguageOptions(
                        spokenLanguageState = spokenLanguages,
                        languagesOptions = optionLanguages,
                        onSelect = {
                            viewModel.onTriggerEvent(
                                if(AccountSettingsViewModel.accountType == AccountType.CUSTOMER){
                                    AccountSettingsEvent.SelectSpokenLanguage(it)
                                }else AccountSettingsEvent.SelectSpokenLanguageProvider(it)
                            )
                        }
                    )
                }
            }
        }
    }
}
