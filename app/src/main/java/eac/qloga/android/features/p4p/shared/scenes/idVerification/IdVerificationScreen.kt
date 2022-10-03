package eac.qloga.android.features.p4p.shared.scenes.idVerification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.DotCircleArcCanvas
import eac.qloga.android.core.shared.components.DottedLine
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.grayTextColor
import eac.qloga.android.core.shared.theme.lightGrayBackground
import eac.qloga.android.features.p4p.shared.components.enrollment.DocumentTypeItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.EnrollmentType
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdVerificationScreen(
    navController: NavController,
    viewModel: EnrollmentViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = 24.dp
    val enrollmentType = EnrollmentViewModel.enrollmentType.value

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.IdVerification.titleName,
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
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(topPadding + 4.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .height(20.dp)
                            .align(Alignment.CenterStart)
                            .padding(end = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DottedLine(
                            arcStrokeColor = gray1,
                            vertical = false
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    ) {
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
                            .padding(start = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DottedLine(
                            arcStrokeColor = gray1,
                            vertical = false
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(lightGrayBackground)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Select a document",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "You will take a picture of it in the next step",
                        style = MaterialTheme.typography.titleSmall,
                        color = grayTextColor
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                DocumentTypeItem(iconId = R.drawable.ic_passport, label = "Passport") {
                    coroutineScope.launch {
                        navController.navigate(P4pScreens.Passport.route)
                    }
                }

                DocumentTypeItem(
                    iconId = R.drawable.ic_drivers_license,
                    label = "Driver's License"
                ) {
                    // TODO onDriversLicense()
                }

                DocumentTypeItem(
                    iconId = R.drawable.ic_national_id,
                    label = "National Identity Card"
                ) {
                    // TODO onNationalId()
                }

                DocumentTypeItem(
                    iconId = R.drawable.ic_residence_pr,
                    label = "Residence Permit Card"
                ) {
                    // TODO onResidencePR()
                }
            }

            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                if (enrollmentType == EnrollmentType.CUSTOMER) {
                    //skip button
                    FullRoundedButton(
                        modifier = Modifier
                            .padding(horizontal = containerHorizontalPadding),
                        buttonText = "Skip",
                        textColor = gray1,
                        backgroundColor = Color.Transparent,
                        borderColor = gray1,
                        showBorder = true
                    ) {
                        coroutineScope.launch {
                            navController.navigate(P4pScreens.EnrollmentTermsConditions.route) {
                                launchSingleTop = true
                            }
                        }
                    }
                }

                //next button
                FullRoundedButton(
                    modifier = Modifier
                        .padding(horizontal = containerHorizontalPadding, vertical = 16.dp),
                    buttonText = "Next",
                    textColor = MaterialTheme.colorScheme.background,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                ) {
                    coroutineScope.launch {
                        navController.navigate(P4pScreens.EnrollmentTermsConditions.route) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}
