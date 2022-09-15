package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.orange1

@Composable
fun DeleteConfirmDialog(
    modifier: Modifier = Modifier,
    name: String,
    updatedDate: String,
    onDelete: () -> Unit,
    onDismissDialog: () -> Unit ,
) {
    Dialog(onDismissRequest = { onDismissDialog() }) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.background)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = "Please confirm",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Red
            )
            DividerLine()
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                text = name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                text = updatedDate,
                style = MaterialTheme.typography.titleSmall,
            )
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
                        .clickable {
                            onDismissDialog()
                        }
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
                        .clickable {
                            onDismissDialog()
                            onDelete()
                        }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Delete",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Red
                    )
                }
            }
        }
    }
}