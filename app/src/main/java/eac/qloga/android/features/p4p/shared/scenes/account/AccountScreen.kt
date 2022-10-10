package eac.qloga.android.features.p4p.shared.scenes.account

import P4pCustomerScreens
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.CustomerAccount
import eac.qloga.android.features.p4p.shared.components.ProviderAccount
import eac.qloga.android.features.p4p.shared.components.TwoSwitchTabRow
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val accountType = AccountViewModel.selectedAccountType
    val scrollState = rememberScrollState()

    BackHandler {
        when(accountType){
            AccountType.PROVIDER -> {
                navController.navigate(P4pProviderScreens.ProviderDashboard.route)
            }
            AccountType.CUSTOMER -> {
                navController.navigate(P4pCustomerScreens.CustomerDashboard.route)
            }
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.Account.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                when(accountType){
                    AccountType.PROVIDER -> {
                        navController.navigate(P4pProviderScreens.ProviderDashboard.route)
                    }
                    AccountType.CUSTOMER -> {
                        navController.navigate(P4pCustomerScreens.CustomerDashboard.route)
                    }
                }
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

                TwoSwitchTabRow(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    tabsContent = AccountType.values().map { it.label },
                    selectedTapIndex = AccountType.values().indexOf(accountType),
                    onSelect = {
                        viewModel.onSwitchAccountType(AccountType.values()[it])
                    },
                )

                when(accountType){
                    AccountType.PROVIDER -> ProviderAccount(navController = navController)
                    AccountType.CUSTOMER -> CustomerAccount(navController = navController)
                }
            }
        }
    }
}