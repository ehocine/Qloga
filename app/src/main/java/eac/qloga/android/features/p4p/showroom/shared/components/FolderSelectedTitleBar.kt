package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R

@Composable
fun FolderSelectedTitleBar(
    modifier: Modifier = Modifier,
    leftActions: @Composable (() -> Unit)  = {},
    rightActions: @Composable (() -> Unit)  = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leftActions()
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            rightActions()
        }
    }
}

@Preview(showBackground = true )
@Composable
fun PreviewFolderSelected() {
    FolderSelectedTitleBar(
        leftActions = {
            SelectedTitleBarItem(
                iconSize = 24.dp,
                iconId = R.drawable.ic_ql_cross,
                onClick = { /*TODO*/ }
            )

            Box(
                modifier = Modifier ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "3",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        },
        rightActions = {
            SelectedTitleBarItem(
                iconSize = 32.dp,
                iconId = R.drawable.ic_ql_info,
                onClick = { /*TODO*/ }
            )

            SelectedTitleBarItem(
                iconSize = 32.dp,
                iconId = R.drawable.ic_ql_edit,
                onClick = { /*TODO*/ }
            )

            SelectedTitleBarItem(
                iconSize = 32.dp,
                iconId = R.drawable.ic_ql_delete,
                onClick = { /*TODO*/ }
            )

            SelectedTitleBarItem(
                iconSize = 32.dp,
                iconId = R.drawable.ic_ql_select_all,
                onClick = { /*TODO*/ }
            )
        }
    )
}