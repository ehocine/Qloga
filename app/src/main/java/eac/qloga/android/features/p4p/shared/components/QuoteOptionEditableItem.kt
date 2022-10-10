package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.components.EditTextInputField

@Composable
fun QuoteOptionEditableItem(
    value : String,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    showDividerLine: Boolean = false,
    iconSpacer: Dp = 16.dp,
    onChangeValue: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(leadingIcon != null){
                leadingIcon()
                Spacer(modifier = Modifier.width(iconSpacer))
            }

            Text(text = label, style = MaterialTheme.typography.titleMedium)
            Box(modifier = Modifier.weight(1f)){
                EditTextInputField(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.End
                    ),
                    value = value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { onChangeValue(it) },
                    onSubmit = { },
                    onFocusChange ={ onFocusChange(it) }
                )
            }
        }
        if(showDividerLine){
            DividerLines.LightDividerLine(Modifier.padding(start = 64.dp))
        }
    }
}