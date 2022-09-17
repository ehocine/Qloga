package eac.qloga.android.features.p4p.shared.scenes.enrollment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.features.p4p.shared.components.EnrollmentStepsInfo
import eac.qloga.android.features.p4p.shared.components.VerifyPhoneInfo
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.EnrollmentType
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun EnrollmentScreen(
    navController: NavController,
    viewModel: EnrollmentViewModel = hiltViewModel()
) {

    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val containerHorizontalPadding = 24.dp
    val enrollmentType = viewModel.enrollmentType.value

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(Unit){
        viewModel.setEnrollmentType(EnrollmentType.CUSTOMER)
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            VerifyPhoneInfo()
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pScreens.Enrollment.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                ) {
                    navController.navigateUp()
                }
            }
        ) { paddingValues ->

            val topPadding = paddingValues.calculateTopPadding()
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = containerHorizontalPadding)
                        // padding is only for start because the divider line
                        // touches end of the screen. right -> end padding is manually
                        // added inside the each items
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(topPadding))
                    Spacer(modifier = Modifier.height(containerTopPadding / 2))

                    enrollmentType?.let {
                        EnrollmentStepsInfo(
                            enrollmentType = it,
                            onVerifyPhoneInfo = { coroutineScope.launch {
                                modalBottomSheetState.show()
                            }},
                            onConfirmAddressInfo = {},
                            onIdVerificationsInfo = {},
                            onAcceptTermsConditionsInfo = {}
                        )
                    }
                }

                FullRoundedButton(
                    modifier = Modifier
                        .padding(
                            bottom = 16.dp,
                            start = containerHorizontalPadding,
                            end = containerHorizontalPadding
                        )
                        .align(Alignment.BottomCenter),
                    buttonText = "Let's begin",
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = Color.White,
                    showBorder = false
                ) {
                    coroutineScope.launch {
                        navController.navigate(P4pScreens.VerifyPhone.route)
                    }
                }
            }
        }
    }
}