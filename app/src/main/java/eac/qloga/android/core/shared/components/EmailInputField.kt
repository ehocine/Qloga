package eac.qloga.android.core.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.BUTTON_HEIGHT
import eac.qloga.android.features.p4p.showroom.shared.components.CustomInputTextField

@Composable
fun EmailInputField(
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    value : String = "",
    hint : String = "",
    borderColor: Color,
    isFocused: Boolean = false,
    onFocusedChanged: (FocusState) -> Unit
) {
    val inputBarHeight = BUTTON_HEIGHT.dp

    Box(modifier = Modifier
        .fillMaxWidth()
    ){
        CustomInputTextField(
            modifier = Modifier
                .fillMaxWidth()
            ,
            value = value,
            singleLine = true,
            placeholder = hint,
            inputFieldPadding = PaddingValues(8.dp),
            minHeight = inputBarHeight,
            borderColor = borderColor,
            borderSize = if(isFocused) 1.4.dp else 1.dp,
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.titleMedium,
            onValueChange = { onValueChange(it)},
            onFocusChange = { onFocusedChanged(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            ),
            keyboardActions = KeyboardActions(
                 onDone = {
                    onSubmit()
                },
            ),
        )
    }
}
