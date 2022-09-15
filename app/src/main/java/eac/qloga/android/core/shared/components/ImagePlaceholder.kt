package eac.qloga.android.core.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun ImagePlaceholder(
    modifier: Modifier = Modifier ,
    imageModifier: Modifier = Modifier,
    imageId: Any
) {
//    val ctx = LocalContext.current
//    val painter = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(ctx)
//            .decoderFactory(SvgDecoder.Factory())
//            .data("android.resource://${ctx.applicationContext.packageName}/${imageId}")
//            .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
//            .build()
//    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = imageModifier,
            painter = rememberAsyncImagePainter(model = imageId),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}