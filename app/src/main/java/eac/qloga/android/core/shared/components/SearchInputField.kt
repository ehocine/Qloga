package eac.qloga.android.core.shared.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.QLOGATheme
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.features.p4p.showroom.shared.components.CustomInputTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchInputField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    value: String,
    hint: String = "",
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
            showBottomLine = true,
            bottomLineColor = animatedBottomLineColor.value,
            minHeight = 52.dp,
            textStyle = MaterialTheme.typography.bodySmall,
            bottomBorderPadding = 0.dp,
            leadingIcon = { SearchIcon( color =  MaterialTheme.colorScheme.primary) },
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

@Composable private fun SearchIcon(
    color: Color = Color.Black
){
    Box {
        Icon(
            modifier = Modifier.size(28.dp),
            imageVector = Icons.Rounded.Search,
            contentDescription = "Search Icon",
            tint = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextInputField(){
    QLOGATheme(darkTheme = false, dynamicColor = false) {
        SearchInputField(
            onSubmit = {},
            onValueChange = {},
            value = ""
        ){}
    }
}

