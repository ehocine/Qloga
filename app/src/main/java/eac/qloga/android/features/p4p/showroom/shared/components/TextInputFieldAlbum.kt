package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.business.util.Extensions.clearFocusOnKeyboardDismiss
import eac.qloga.android.core.shared.theme.gray1

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextInputFieldAlbum(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit = {},
    value : String = "",
    hint : String = "",
    height: Dp = 44.dp,
    focusRequester: FocusRequester = FocusRequester(),
    contentVerticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    singleLine: Boolean = true,
    isFocused: Boolean = false,
    onFocusedChanged: (FocusState) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = modifier
        .fillMaxWidth()
        ,
        contentAlignment = Alignment.Center
    ){
        CustomInputTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clearFocusOnKeyboardDismiss()
            ,
            value = value,
            singleLine = singleLine,
            inputFieldPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            minHeight = height,
            placeholder = hint,
            borderColor = if(isFocused) MaterialTheme.colorScheme.primary else gray1,
            borderSize = if(isFocused) 1.5.dp else 1.dp,
            shape = RoundedCornerShape(12.dp),
            focusRequester = focusRequester,
            contentVerticalAlignment = contentVerticalAlignment,
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
                    keyboardController?.hide()
                    onSubmit()
                },
            ),
        )
    }
}