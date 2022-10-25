package eac.qloga.android.features.p4p.shared.scenes.account

import P4pCustomerScreens
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.customer.shared.viewModels.CustomerDashboardViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderDashboardViewModel
import eac.qloga.android.features.p4p.shared.components.CustomerAccount
import eac.qloga.android.features.p4p.shared.components.ProviderAccount
import eac.qloga.android.features.p4p.shared.components.TwoSwitchTabRow
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.EnrollmentType
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavController,
    apiViewModel: ApiViewModel = hiltViewModel(),
    viewModel: AccountViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val accountType = AccountViewModel.selectedAccountType
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        apiViewModel.getEnrolls()
    }

    BackHandler {
        when(accountType){
            AccountType.PROVIDER -> {
                ProviderDashboardViewModel.alreadyShownProfileInfoDialog = true
                navController.navigate(P4pProviderScreens.ProviderDashboard.route)
            }
            AccountType.CUSTOMER -> {
                CustomerDashboardViewModel.alreadyShownProfileInfoDialog = true
                navController.navigate(P4pCustomerScreens.CustomerDashboard.route)
            }
            else -> {}
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
                        ProviderDashboardViewModel.alreadyShownProfileInfoDialog = true
                        navController.navigate(P4pProviderScreens.ProviderDashboard.route)
                    }
                    AccountType.CUSTOMER -> {
                        CustomerDashboardViewModel.alreadyShownProfileInfoDialog = true
                        navController.navigate(P4pCustomerScreens.CustomerDashboard.route)
                    }
                    else -> {}
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
                        try {
                            val accType = AccountType.values()[it]
                            val enrollmentType = EnrollmentViewModel.currentEnrollmentType.value
                            if(
                                enrollmentType == EnrollmentType.BOTH ||
                                accType == AccountType.CUSTOMER && enrollmentType == EnrollmentType.CUSTOMER||
                                accType == AccountType.PROVIDER && enrollmentType == EnrollmentType.PROVIDER
                            ){
                                viewModel.onSwitchAccountType(accType)
                            }else{
                                if(accType == AccountType.CUSTOMER){
                                    EnrollmentViewModel.enrollmentType.value = EnrollmentType.CUSTOMER
                                }
                                if(accType == AccountType.PROVIDER){
                                    EnrollmentViewModel.enrollmentType.value = EnrollmentType.PROVIDER
                                }
                                navController.navigate(P4pScreens.Enrollment.route)
                            }
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                    },
                )

                when(accountType){
                    AccountType.PROVIDER -> ProviderAccount(navController = navController, apiViewModel)
                    AccountType.CUSTOMER -> CustomerAccount(navController = navController, apiViewModel)
                    else -> {}
                }
            }
        }
    }
}