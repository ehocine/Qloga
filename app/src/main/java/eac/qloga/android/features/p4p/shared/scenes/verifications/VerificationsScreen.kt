package eac.qloga.android.features.p4p.shared.scenes.verifications

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.NavigationActions
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.features.p4p.shared.components.VerificationsItemList
import eac.qloga.android.features.p4p.shared.components.VerificationsStatusButton
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.bare.enums.VerificationHolderType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationsScreen(
    navigationActions: NavigationActions,
    viewModel: VerificationsViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = 24.dp
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val verifications = viewModel.groupedVerification.value

    LaunchedEffect(key1 = Unit, key2 = VerificationsViewModel.verifications.value){
        viewModel.groupVerifications()
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.Verifications.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navigationActions.upPress()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = containerHorizontalPadding)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topPadding))
            Spacer(modifier = Modifier.height(containerTopPadding))

            verifications.forEach { (holderType, vrfs) ->
                if(holderType == VerificationHolderType.ORG){
                    VerificationsStatusButton(
                        iconId = R.drawable.ic_verification,
                        label = "Provider verifications"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    vrfs.forEach {  verification ->
                        VerificationsItemList(
                            title = verification.type.tip,
                            value = verification.notes
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }else{
                    VerificationsStatusButton(
                        iconId = R.drawable.ic_verification,
                        label = "Provider admin verifications"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    vrfs.forEach { verification ->
                        VerificationsItemList(
                            title = verification.type.tip,
                            value = verification.notes
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVerifications() {
    VerificationsScreen(NavigationActions(NavController(Application())))
}

