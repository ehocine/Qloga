package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.DateConverter
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.shared.components.BottomSheetDashLine
import eac.qloga.bare.enums.AccessLevel
import kotlinx.coroutines.launch

@Composable
fun CreateNewAlbum(
    albumNameState: InputFieldState,
    albumDescState: InputFieldState,
    accessLevel: AccessLevel,
    title: String,
    submitButtonName: String,
    onEnterName: (String) -> Unit,
    onFocusName: (FocusState) -> Unit,
    onFocusDesc: (FocusState) -> Unit,
    onEnterDesc: (String) -> Unit,
    onChangeAccess: (AccessLevel) -> Unit,
    onCreate: () -> Unit,
    onCancel: () -> Unit,
) {
    val containerHorizontalPadding = 24.dp
    val focusRequester = remember{ FocusRequester() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BottomSheetDashLine()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            DividerLine()
            Spacer(modifier = Modifier.height(16.dp))
            TextInputFieldAlbum(
                modifier = Modifier.padding(horizontal = containerHorizontalPadding),
                value = albumNameState.text,
                hint = albumNameState.hint,
                isFocused = albumNameState.isFocused,
                onValueChange = { onEnterName(it) },
                onFocusedChanged = { onFocusName(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = containerHorizontalPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(end = 4.dp),
                    text = "Modified:",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
                Text(
                    modifier = Modifier.padding(end = 4.dp),
                    text = DateConverter.longToStringDate(System.currentTimeMillis()),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextInputFieldAlbum(
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            focusRequester.requestFocus()
                        }
                    }
                    .padding(horizontal = containerHorizontalPadding),
                value = albumDescState.text,
                hint = albumDescState.hint,
                height = 180.dp,
                singleLine = false,
                focusRequester = focusRequester,
                isFocused = albumDescState.isFocused,
                contentVerticalAlignment = Alignment.Top,
                onValueChange = { onEnterDesc(it) },
                onFocusedChanged ={ onFocusDesc(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            AccessLevelPicker(
                modifier= Modifier.padding(horizontal = 24.dp),
                selected = accessLevel,
                onSelect = { onChangeAccess(it) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            DividerLine()
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .clickable { onCancel() }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.titleMedium,
                        color = orange1
                    )
                }
                Divider(
                    Modifier
                        .height(54.dp)
                        .width(1.dp)
                        .alpha(.2f)
                        .background(gray1))
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .clickable { onCreate() }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = submitButtonName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun AccessLevelPicker(
    modifier: Modifier= Modifier,
    selected: AccessLevel = AccessLevel.PRIVATE,
    onSelect: (AccessLevel) -> Unit
){
    val interactionSource = MutableInteractionSource()
    Column(modifier.fillMaxWidth()) {
        AccessLevel.values().forEach { access ->
            val isSelected = access == selected
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onSelect(access) }
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Box(
                    Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(2.dp, gray1, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .border(
                                2.dp,
                                if (isSelected) MaterialTheme.colorScheme.primary else gray1,
                                CircleShape
                            )
                            .background(if (isSelected) MaterialTheme.colorScheme.primary else gray1)
                    )
                }
                Spacer(Modifier.width(16.dp))
                Text(
                    text = access.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = when(access) {
                        AccessLevel.PRIVATE -> { MaterialTheme.colorScheme.primary }
                        AccessLevel.FAMILY -> info_sky
                        else -> Color.Red
                    }
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(.65f)
                    ,
                    text = when(access){
                        AccessLevel.PRIVATE -> { "( only you can see it )"}
                        AccessLevel.FAMILY -> { "( you and your family can see it )"}
                        else -> { "( anyone can see it )"}
                    }
                    ,
                    style = MaterialTheme.typography.titleSmall,
                    color = gray30,
                    textAlign = TextAlign.End
                )
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateNew() {
    //CreateNewAlbum(onCreate = {}, onCancel = {})
}