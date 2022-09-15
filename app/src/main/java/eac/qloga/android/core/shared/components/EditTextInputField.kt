package eac.qloga.android.core.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import eac.qloga.android.business.util.Extensions.clearFocusOnKeyboardDismiss
import eac.qloga.android.core.shared.theme.gray30

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditTextInputField(
    modifier: Modifier = Modifier,
    value: String? = null,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    onSubmit: () -> Unit = {},
    hint: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    textStyle: TextStyle = TextStyle(),
    onFocusChange: (FocusState) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val visibleHint = remember { mutableStateOf(true)}
    val focusRequester = FocusRequester()

    LaunchedEffect(key1 = value)
    {
        visibleHint.value = value.isNullOrEmpty()
    }

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(.75f)
                .clickable { focusRequester.requestFocus() }
                .focusRequester(focusRequester)
                .onFocusChanged { onFocusChange(it) }
                .clearFocusOnKeyboardDismiss()
            ,
            textStyle = textStyle,
            singleLine = singleLine,
            value = value ?: "",
            onValueChange = { onValueChange(it)},
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
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

        if(visibleHint.value || value.isNullOrEmpty())
        {
            Text(
                modifier = Modifier.alpha(.75f),
                color = gray30,
                text = hint?.trim() ?: "",
                style = textStyle,
            )
        }
    }
}