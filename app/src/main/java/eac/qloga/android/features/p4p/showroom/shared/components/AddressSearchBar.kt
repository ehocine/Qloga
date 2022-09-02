package eac.qloga.android.core.shared.components.address

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.QLOGATheme
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.features.p4p.showroom.shared.components.CrossTrailingIcon
import eac.qloga.android.features.p4p.showroom.shared.components.CustomInputTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddressSearchBar(
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    value : String,
    hint : String = "",
    onClear: () -> Unit,
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
        modifier = Modifier
            .fillMaxWidth()
    ){
        CustomInputTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            trailingIcon = { if(value.isNotEmpty()) CrossTrailingIcon(onClick = {onClear()}) },
            singleLine = true,
            placeholder = hint,
            showBottomLine = true,
            bottomLineColor = animatedBottomLineColor.value,
            bottomBorderPadding = 0.dp,
            minHeight = 52.dp,
            textStyle = MaterialTheme.typography.titleMedium,
            onValueChange = { onValueChange(it)},
            onFocusChange = { onFocusedChanged(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
                capitalization = KeyboardCapitalization.Sentences
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSubmit()
                    keyboardController?.hide()
                },
            ),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchBar(){
    QLOGATheme(darkTheme = false, dynamicColor = false) {
        AddressSearchBar(
            onSubmit = {},
            onValueChange = {},
            onClear = {},
            value = ""
        ){}
    }
}

