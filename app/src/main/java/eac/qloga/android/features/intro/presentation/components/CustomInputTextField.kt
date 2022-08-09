package eac.qloga.android.features.intro.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.util.Extensions.clearFocusOnKeyboardDismiss
import eac.qloga.android.ui.theme.QLOGATheme
import eac.qloga.android.ui.theme.gray1

@Composable
fun CustomInputTextField(
    modifier: Modifier = Modifier,
    height: Dp = 50.dp,
    borderColor: Color? = null,
    bottomLineColor: Color? = null,
    bottomBorderWidth: Dp = .8.dp,
    inputFieldPadding: PaddingValues = PaddingValues(0.dp),
    borderSize: Dp = 0.dp,
    value : String = "",
    focusRequester: FocusRequester = FocusRequester(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    placeholder: String = "",
    placeholderAlpha: Float = 0.30f,
    shape: Shape = RoundedCornerShape(0.dp),
    showBottomLine: Boolean = false,
    textStyle: TextStyle,
    placeholderTextStyle: TextStyle? = null,
    bottomBorderPadding: Dp = 8.dp,
    contentVerticalAlignment: Alignment.Vertical = CenterVertically,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onFocusChange: (FocusState) -> Unit,
) {
    val focusRequesterCurrent = remember { focusRequester }
    val interactionSource = MutableInteractionSource()

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { focusRequesterCurrent.requestFocus() }
                .border(borderSize, borderColor ?: Color.Transparent, shape)
            ,
            verticalAlignment = contentVerticalAlignment
        ) {
            if (leadingIcon != null) {
                Spacer(modifier = Modifier.width(4.dp))
                leadingIcon()
                Spacer(modifier = Modifier.width(4.dp))
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(inputFieldPadding),
            ) {
                Column{
                    BasicTextField(
                        modifier = Modifier
                            .focusRequester(focusRequesterCurrent)
                            .alpha(.75f)
                            .fillMaxWidth()
                            .onFocusChanged { onFocusChange(it) }
                            .clearFocusOnKeyboardDismiss()
                        ,
                        textStyle = textStyle,
                        singleLine = singleLine,
                        value = value,
                        onValueChange = { onValueChange(it)},
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                    )
                    if(showBottomLine){
                        Spacer(modifier = Modifier.height(3.dp))
                        Box(modifier = Modifier
                                .alpha(.3f)
                                .height(bottomBorderWidth)
                                .fillMaxWidth()
                                .padding(horizontal = bottomBorderPadding)
                                .background( bottomLineColor?: gray1)
                        )
                    }
                }
                if(value.isEmpty()){
                    Text(
                        modifier = Modifier.alpha(placeholderAlpha),
                        text = placeholder.trim(),
                        style = placeholderTextStyle?:textStyle,
                        maxLines = if(singleLine) 1 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            if(trailingIcon != null){
                Spacer(modifier = Modifier.width(4.dp))
                trailingIcon()
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomInput(){
    QLOGATheme(darkTheme = false) {
        CustomInputTextField(
            showBottomLine = false,
            onValueChange = {},
            textStyle = MaterialTheme.typography.bodyLarge,
            onFocusChange = {}
        )
    }
}