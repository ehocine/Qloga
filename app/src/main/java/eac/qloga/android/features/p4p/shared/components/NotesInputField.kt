package eac.qloga.android.features.p4p.shared.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.features.p4p.showroom.shared.components.CustomInputTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotesInputField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    value: String,
    hint: String = "Your notes...",
    showBottomLine: Boolean = true,
    isFocused: Boolean = false,
    onFocusedChanged: (FocusState) -> Unit
) {
    val bottomLineColor = if(isFocused) green1 else gray1
    val animatedBottomLineColor = animateColorAsState(
        targetValue = bottomLineColor,
        animationSpec = tween(
            durationMillis = 600
        )
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
    ){
        CustomInputTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            singleLine = true,
            placeholder = hint,
            showBottomLine = showBottomLine,
            bottomLineColor = animatedBottomLineColor.value,
            minHeight = 52.dp,
            textStyle = MaterialTheme.typography.titleMedium,
            bottomBorderPadding = 0.dp,
            bottomBorderWidth = if(isFocused) 1.5.dp else .8.dp,
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
                    keyboardController?.hide()
                },
            ),
        )
    }
}