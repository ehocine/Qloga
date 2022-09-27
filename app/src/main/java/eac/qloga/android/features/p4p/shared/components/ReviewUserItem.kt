package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.PulsePlaceholder
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.grayTextColor
import kotlin.math.roundToInt

@Composable
fun ReviewUserItem(
    imageId: Any?,
    rating: Float,
    label: String,
) {
    val imageSize = 90.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val painter = rememberAsyncImagePainter(model = imageId)

        if(painter.state is AsyncImagePainter.State.Loading || imageId == null){
            PulsePlaceholder(
                modifier = Modifier.size(imageSize),
                roundedCornerShape = CircleShape
            )
        }else{
            Image(
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape)
                ,
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 8.dp)
            ,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row {
                    /*** Loop 5 rating star and paint them according as given rating
                     * For eg: If rating is 4.3 then 4 star should be filled form left
                     * */
                    for( i in 1..5){
                        if(i <= rating.roundToInt()){
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_star_filled),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }else{
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_star_empty_green),
                                contentDescription = ""
                            )
                        }
                        Spacer(Modifier.width(4.dp))
                    }
                }

                Text(
                    modifier = Modifier.alpha(.75f),
                    text = "($rating)",
                    style = MaterialTheme.typography.titleMedium,
                    color = grayTextColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.alpha(.75f),
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = gray30
            )
        }
    }
}

@Preview
@Composable
fun PreviewPreviewUserItem(){
    ReviewUserItem(
        imageId = R.drawable.model_profile,
        rating = 4.2f,
        label = "Prompt payment, polite and respectful"
    )
}