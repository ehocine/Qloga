package eac.qloga.android.core.shared.components

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.features.p4p.showroom.shared.components.CustomInputTextField

object InputFields {
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun NumberInputField(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        onSubmit: () -> Unit = {},
        value: String,
        hint: String = "",
        height: Dp = 48.dp,
        singleLine: Boolean = false,
        textAlign: TextAlign = TextAlign.Start,
        showBottomLine: Boolean = false,
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
                singleLine = singleLine,
                placeholder = hint,
                showBottomLine = showBottomLine,
                bottomLineColor = animatedBottomLineColor.value,
                minHeight = height,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    textAlign = textAlign
                ),
                bottomBorderPadding = 0.dp,
                bottomBorderWidth = if(isFocused) 1.5.dp else .8.dp,
                onValueChange = { onValueChange(it)},
                onFocusChange = { onFocusedChanged(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
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

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun TextInputField(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        onSubmit: () -> Unit = {},
        value: String,
        singleLine: Boolean = false,
        minHeight: Dp = 40.dp,
        hint: String = "",
        showBottomLine: Boolean = false,
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
                singleLine = singleLine,
                placeholder = hint,
                minHeight = minHeight,
                showBottomLine = showBottomLine,
                bottomLineColor = animatedBottomLineColor.value,
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
}