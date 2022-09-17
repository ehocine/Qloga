package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun VerifyPhoneInfo() {

    val infoMsg = "Your verified mobile number is mandatory and needed for " +
            "maintaining communication with providers."

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(.15f)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(gray1)
            ,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = infoMsg, style = MaterialTheme.typography.titleMedium)
    }
}