package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun NavItem(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    iconSize: Dp = 38.dp,
    iconUrl: String,
    label: String,
    isSelected: Boolean = false,
    strokeColor: Color,
    BGColor: Color,
    onClick: () -> Unit
) {
    val labelFontSize = 13.sp

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = if (isSelected) 2.dp else 1.5.dp,
                    color = if (isSelected) strokeColor else gray1.copy(alpha = .6f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onClick() }
                .background(BGColor),
            contentAlignment = Alignment.Center
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(iconUrl)
                    .size(Size.ORIGINAL) // Set the target size to load the image at.
                    .build()
            )
            Image(
                modifier = Modifier.size(iconSize),
                painter = painter,
                contentDescription = null,
//                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = labelFontSize,
                fontWeight = FontWeight.W600
            )
        )
    }
}