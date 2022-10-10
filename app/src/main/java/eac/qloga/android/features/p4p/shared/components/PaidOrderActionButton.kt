package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.theme.grayTextColor

@Composable
fun PaidOrderActionButton(
    modifier: Modifier = Modifier,
    accountType: String,
    onClickReview: () -> Unit,
    onClickNotReview: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Buttons.FullRoundedButton(buttonText = "Review by $accountType") {
            onClickReview()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Buttons.FullRoundedButton(buttonText = "Not review", backgroundColor = grayTextColor) {
            onClickNotReview()
        }
    }
}