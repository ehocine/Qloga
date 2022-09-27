package eac.qloga.android.features.p4p.provider.shared.components

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.DividerLines.LightDividerLine
import eac.qloga.android.core.shared.components.PulsePlaceholder
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun FavouriteCustomersListItem(
    modifier: Modifier = Modifier,
    imageId: Bitmap?,
    name: String,
    location: String,
    showBottomLine: Boolean = true,
    address: String,
    onclickQuote: () -> Unit
) {
    val imageWidth = 90.dp
    val iconSize = 18.dp

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val painter = rememberAsyncImagePainter(model = imageId)

            Box(
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(top = 8.dp)
            ) {
                if(imageId == null){
                    PulsePlaceholder(
                        modifier = Modifier.size(imageWidth),
                        roundedCornerShape = CircleShape
                    )
                }else {
                    Image(
                        modifier = Modifier
                            .size(imageWidth)
                            .clip(CircleShape)
                        ,
                        painter = painter,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        alignment = Alignment.TopCenter
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "($location)",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Box {
                        Icon(
                            modifier = Modifier.size(iconSize),
                            imageVector = Icons.Rounded.ArrowForwardIos,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = address, style = MaterialTheme.typography.titleSmall, color = gray30)
                Spacer(modifier = Modifier.height(12.dp))
                FullRoundedButton(
                    modifier = Modifier.height(32.dp),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    buttonText = "Quote",
                    onClick = { onclickQuote() }
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        if(showBottomLine){
            LightDividerLine()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProvidersItem() {
    FavouriteCustomersListItem(
        imageId = null,
        name = "Kai's Cleaning agency",
        location = "Edinburgh",
        address = "Baird House 18, Holyrood Park Rd, Edinburgh, GB",
        onclickQuote = {}
    )
}