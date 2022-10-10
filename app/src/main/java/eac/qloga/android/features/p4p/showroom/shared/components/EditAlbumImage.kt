package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.DateConverter
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.shared.components.BottomSheetDashLine
import eac.qloga.bare.enums.AccessLevel

@Composable
fun EditAlbumImage(
    imageNameState: InputFieldState,
    imageDescState: InputFieldState,
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            DividerLine()
            Spacer(modifier = Modifier.height(16.dp))
            TextInputFieldAlbum(
                modifier = Modifier.padding(horizontal = containerHorizontalPadding),
                value = imageNameState.text,
                hint = imageNameState.hint,
                isFocused = imageNameState.isFocused,
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
                    .padding(horizontal = containerHorizontalPadding),
                value = imageDescState.text,
                hint = imageDescState.hint,
                height = 180.dp,
                singleLine = false,
                isFocused = imageDescState.isFocused,
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

@Preview(showBackground = true)
@Composable
fun PreviewEditImage() {
    //CreateNewAlbum(onCreate = {}, onCancel = {})
}