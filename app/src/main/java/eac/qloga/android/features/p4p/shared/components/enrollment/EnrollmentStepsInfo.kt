package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.features.p4p.shared.utils.EnrollmentType

@Composable
fun EnrollmentStepsInfo(
    modifier: Modifier = Modifier,
    enrollmentType: EnrollmentType,
    onVerifyPhoneInfo: () -> Unit,
    onConfirmAddressInfo: () -> Unit,
    onIdVerificationsInfo: () -> Unit,
    onAcceptTermsConditionsInfo: () -> Unit,
) {

    val containerHorizontalPadding = 24.dp
    val finalMsg = if(EnrollmentType.CUSTOMER == enrollmentType) "Customer ready to work!" else "Provider ready to work!"

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        EnrollmentItemList(label = "Verify phone"){ onVerifyPhoneInfo() }
        EnrollmentItemList(label = "Confirm address"){ onConfirmAddressInfo() }
        EnrollmentItemList(label = "Identity verifications"){ onIdVerificationsInfo() }
        EnrollmentItemList(label = "Accept T&C's"){ onAcceptTermsConditionsInfo() }

        /*********  *******/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = containerHorizontalPadding)
            ,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "",
                    tint = Color.White
                )
            }

            Box(modifier = Modifier
                .weight(1f)
                .padding(start = 18.dp)
            ) {
                Text(
                    text = finalMsg,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "",
                tint = gray1
            )
        }
        /****************/
    }
}