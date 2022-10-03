package eac.qloga.android.core.shared.components.address

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines.LightDividerLine
import eac.qloga.android.core.shared.components.EditTextInputField
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun AddressCardItem(
    title: String,
    showDivider: Boolean = true,
    textStyle: TextStyle = TextStyle(),
    value: String? = null,
    hint: String = "",
    verticalPadding: Dp = 12.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    onSubmit: ()-> Unit = {},
    editable: Boolean = false,
    onValueChange: (String) -> Unit = {},
    onFocusChange: (FocusState) -> Unit = {},
    actions: @Composable (() -> Unit) = {}
){
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .padding(vertical = if(!value.isNullOrEmpty()) 6.dp else verticalPadding, horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    if(!value.isNullOrEmpty()){
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                            color = gray30
                        )
                    }
                    if(editable){
                        EditTextInputField(
                            modifier = Modifier.fillMaxWidth(),
                            hint = hint,
                            value = value,
                            textStyle = MaterialTheme.typography.titleMedium,
                            keyboardType = keyboardType,
                            onValueChange = { onValueChange(it) },
                            onSubmit = { onSubmit() },
                            onFocusChange ={ onFocusChange(it) }
                        )
                    }else{
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }

                actions()
            }
        }
        if (showDivider) {
            LightDividerLine(modifier = Modifier.padding(start = 12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddressCard() {
    AddressCardItem("Line 1", hint = "Line 1", value = "")
}