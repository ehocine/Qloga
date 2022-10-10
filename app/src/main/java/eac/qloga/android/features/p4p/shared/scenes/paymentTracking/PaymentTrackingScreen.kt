package eac.qloga.android.features.p4p.shared.scenes.paymentTracking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.InfoButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.shared.components.PaymentTrackingItem
import eac.qloga.android.features.p4p.showroom.shared.components.TrackingActorsStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PaymentTrackingScreen(
    navController: NavController,
) {
    val horizontalContentPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerVerticalPadding = Dimensions.ScreenTopPadding.dp
    val bottomSheetCornerRadius  = 16.dp

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = bottomSheetCornerRadius, topEnd = bottomSheetCornerRadius),
        sheetContent ={ TrackingActorsStatus() }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pScreens.PaymentTracking.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    onBackPress = { navController.navigateUp() },
                    actions = {
                        InfoButton(
                            onClick = {
                                scope.launch {
                                    modalBottomSheetState.show()
                                }
                            }, color = orange1
                        )
                    }
                )
            }
        ) { paddingValues ->
            val titleBarHeight = paddingValues.calculateTopPadding()

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(horizontal = horizontalContentPadding)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(titleBarHeight))
                    Spacer(modifier = Modifier.height(containerVerticalPadding))

                    PaymentTrackingItem(
                        idColor = info_sky,
                        title = "Authorised",
                        label = "Deposit: Callout charge",
                        date = "25/01/2022",
                        time = "14:00",
                        price = 84.00
                    )
                    PaymentTrackingItem(
                        idColor = MaterialTheme.colorScheme.primary,
                        title = "Payment request",
                        label = "Remaining amount(2 hours)",
                        date = "25/01/2022",
                        time = "14:00",
                        price = 84.00
                    )
                    PaymentTrackingItem(
                        idColor = MaterialTheme.colorScheme.primary,
                        title = "Payment request",
                        label = "Deposit: Callout charge",
                        date = "25/01/2022",
                        time = "14:00",
                        price = 84.00
                    )
                }
            }
        }
    }
}