package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun AlbumsFolderPreview(
    modifier: Modifier = Modifier,
    iconId: Int,
    folderName: String,
    mediaCount: Int,
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    enableClick: Boolean = true,
    onLongPress: () -> Unit = {},
    onSelect: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onLongPress()
                    },
                    onTap = {
                        if(enableClick){
                            onClick()
                        }
                    }
                )
            }
            .padding(8.dp)
    ) {
        Box {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .sizeIn(minWidth = 120.dp)
                    .height(94.dp),
                painter = painterResource(id = iconId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            if(isSelectable || isSelected){
                Box(
                    Modifier
                        .padding(4.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Box(
                        Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .clickable { onSelect() }
                            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ){
                        if(isSelected){
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f).padding(end = 2.dp)) {
                Text(
                    text = folderName,
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "$mediaCount",
                    style = MaterialTheme.typography.titleSmall,
                    color = gray30
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_ql_key),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAlbumsFolder() {
    Box(
        modifier = Modifier.width(160.dp)
    ) {
        AlbumsFolderPreview(
            iconId = R.drawable.arch4,
            folderName = "Kitchen",
            mediaCount = 12,
            onLongPress = {},
            onSelect = {},
            onClick = {}
        )
    }
}