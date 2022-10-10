package eac.qloga.android.features.p4p.shared.scenes.tracking

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
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.shared.components.TrackingActorsStatus
import eac.qloga.android.features.p4p.showroom.shared.components.TrackingItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TrackingScreen(
    navController: NavController,
) {
    val horizontalContentPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerVerticalPadding = Dimensions.ScreenTopPadding.dp
    val bottomSheetCornerRadius  = 16.dp
    val note = "Note: Visit 3 is marked as \"Provider not " +
            "arrived\" after 24 hours of no response " +
            "from the provider."

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
                    label = P4pScreens.Tracking.titleName,
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
                    //space for title bar
                    Spacer(modifier = Modifier.height(titleBarHeight))
                    Spacer(modifier = Modifier.height(containerVerticalPadding))

                    TrackingItem(
                        idColor = info_sky,
                        title = "Fun reservation is needed",
                        label = "action: fund reservation is needed",
                        note = "",
                        date = "25/01/2022",
                        time = "14:00"
                    )
                    TrackingItem(
                        idColor = MaterialTheme.colorScheme.primary,
                        title = "Inquiry",
                        label = "action: Update",
                        note = note,
                        date = "25/01/2022",
                        time = "14:00"
                    )
                    TrackingItem(
                        idColor = MaterialTheme.colorScheme.primary,
                        title = "Inquiry",
                        label = "action: Update",
                        note = note,
                        date = "25/01/2022",
                        time = "14:00"
                    )

                    TrackingItem(
                        idColor = Red10,
                        title = "Visit cancelled",
                        label = "action: visit cancelled",
                        note = note,
                        date = "25/01/2022",
                        time = "14:00"
                    )
                    TrackingItem(
                        idColor = MaterialTheme.colorScheme.primary,
                        title = "Approved",
                        label = "action: approve",
                        note = note,
                        date = "25/01/2022",
                        time = "14:00"
                    )
                }
            }
        }
    }
}