package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eac.qloga.android.core.shared.components.ImagePlaceholder

@Composable
fun OrdersEmptyStateCard(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    imageId: Int
) {
    ImagePlaceholder(
        modifier = modifier.fillMaxSize(),
        imageModifier = imageModifier.fillMaxSize(),
        imageId = imageId
    )
}