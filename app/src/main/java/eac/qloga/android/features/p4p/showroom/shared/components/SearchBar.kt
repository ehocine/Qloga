package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.QLOGATheme
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    value : String = "",
    hint : String = "Search",
    onClear: () -> Unit,
    isFocused: Boolean = false,
    height: Dp = 44.dp,
    background: Color = Color.Transparent,
    focusRequester: FocusRequester,
    onFocusedChanged: (FocusState) -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()){
        CustomInputTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = value,
            background = background,
            focusRequester = focusRequester,
            leadingIcon = { SearchIcon(if(isFocused) MaterialTheme.colorScheme.primary else gray1) },
            trailingIcon = { if(value.isNotEmpty()) CrossTrailingIcon(onClick = {onClear()}) },
            singleLine = true,
            placeholder = hint,
            minHeight = height,
            borderColor = if(isFocused) MaterialTheme.colorScheme.primary else gray1,
            borderSize = if(isFocused) 1.5.dp else 1.dp,
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.bodySmall,
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
                },
            ),
        )
    }
}

@Composable private fun SearchIcon(
    color: Color  = Color.Black,
    size: Dp = 28.dp
){
  Box {
      Icon(
          modifier = Modifier.size(size).padding(horizontal = 4.dp),
          painter = painterResource(id = R.drawable.ic_ql_search),
          contentDescription = "Search Icon",
          tint = color
      )
  }
}

@Preview
@Composable
fun PreviewSearchBar(){
    QLOGATheme(darkTheme = false, dynamicColor = false) {
        SearchBar(
            onSubmit = {},
            onValueChange = {},
            onClear = {},
            value = "",
            focusRequester = FocusRequester()
        ){}
    }
}

