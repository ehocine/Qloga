package eac.qloga.android.core.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eac.qloga.android.R

@Composable
fun TitleBarDelete(
    onDelete: () -> Unit
) {
    val iconSize = 20.dp

    Box(modifier = Modifier
        .clip(CircleShape)
        .clickable { onDelete() }
        .padding(8.dp)
    ){
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(id = R.drawable.ic_ql_delete),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onError
        )
    }
}