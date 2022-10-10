package eac.qloga.android.features.p4p.shared.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.components.InputFields.TextInputField
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.InputFieldState

@Composable
fun OrderFeedbackCard(
    modifier: Modifier = Modifier,
    publicFeedbackState: InputFieldState,
    privateFeedbackState: InputFieldState,
    onEnterPublicFeedback: (String) -> Unit,
    onEnterPrivateFeedback: (String) -> Unit,
    onFocusPublicFeedback: (FocusState) -> Unit,
    onFocusPrivateFeedback: (FocusState) -> Unit,
    onClickInfo: () -> Unit
) {
    val startDividerPadding = 64.dp

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = "FEEDBACK", style = MaterialTheme.typography.titleMedium)
            Box(modifier = Modifier
                .clip(CircleShape)
                .clickable { onClickInfo() }
                .padding(4.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = null,
                    tint = orange1
                )
            }
        }
        Cards.ContainerBorderedCard(
            modifier = Modifier.animateContentSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                TextInputField(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp , bottom =4.dp),
                    onValueChange = { onEnterPublicFeedback(it) },
                    value = publicFeedbackState.text,
                    hint = publicFeedbackState.hint,
                    onFocusedChanged = { onFocusPublicFeedback(it) }
                )
                DividerLines.LightDividerLine(Modifier.padding(start = startDividerPadding))
                TextInputField(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 4.dp),
                    onValueChange = { onEnterPrivateFeedback(it) },
                    value = privateFeedbackState.text,
                    hint = privateFeedbackState.hint,
                    onFocusedChanged = { onFocusPrivateFeedback(it) }
                )
            }
        }
    }
}