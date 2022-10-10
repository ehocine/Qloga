package eac.qloga.android.features.p4p.showroom.shared.components

import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun AlbumImagePreview(
    modifier: Modifier = Modifier,
    bitmapImage: Bitmap? = null,
    imageUri: Uri? = null,
    imageId: Int,
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    enableClick: Boolean = true,
    onLongPress: () -> Unit,
    onSelect: () -> Unit,
    onClick: () -> Unit
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
                    onTap = { onClick() }
                )
            }
            .padding(8.dp)
    ) {
        Box {
            if(imageUri != null){
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .sizeIn(minWidth = 120.dp)
                        .height(94.dp),
                    painter = rememberAsyncImagePainter(model = imageUri), // later fetch from api and show with url , only for testing(ImageBitmap)
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }else if(bitmapImage != null){
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .sizeIn(minWidth = 120.dp)
                        .height(94.dp),
                        bitmap = bitmapImage.asImageBitmap(), // later fetch from api and show with url , only for testing(ImageBitmap)
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }else {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .sizeIn(minWidth = 120.dp)
                        .height(94.dp),
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

            if(isSelectable){
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
    }
}