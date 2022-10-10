package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines.LightDividerLine
import eac.qloga.android.core.shared.components.EditTextInputField
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun AccSettingsListItem(
    title: String? = null,
    value: String,
    hint: String ="",
    active: Boolean = true,
    clickable: Boolean = false,
    editable: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onClick: () -> Unit  = {},
    onValueChange: (String) -> Unit = {},
    onFocusChange: (FocusState) -> Unit = {},
    onSubmit: () -> Unit = {}
){
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = clickable) { onClick() }
                .padding(horizontal = 16.dp, vertical = if (title == null) 14.dp else 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(!editable){
                Column(modifier = Modifier.weight(1f)) {
                    title?.let {
                        Text(
                            modifier = Modifier.alpha(.75f),
                            text = it,
                            style = MaterialTheme.typography.titleSmall,
                            color = gray30
                        )
                    }
                    Text(
                        modifier = Modifier.alpha(if(active) 1f else 0.75f).padding(end = 8.dp),
                        text = value,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (active) MaterialTheme.colorScheme.onBackground else gray30,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                if(clickable){
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.ArrowForwardIos,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }else{
                Column(modifier = Modifier.weight(1f)) {
                    title?.let {
                        Text(
                            modifier = Modifier.alpha(.75f),
                            text = it,
                            style = MaterialTheme.typography.titleSmall,
                            color = gray30
                        )
                    }
                    EditTextInputField(
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.titleMedium,
                        hint = hint,
                        value = value,
                        keyboardType = keyboardType,
                        onValueChange = { onValueChange(it) },
                        onSubmit = { onSubmit() },
                        onFocusChange ={ onFocusChange(it) }
                    )
                }
            }
        }
        LightDividerLine(Modifier.padding(start = 16.dp))
    }
}