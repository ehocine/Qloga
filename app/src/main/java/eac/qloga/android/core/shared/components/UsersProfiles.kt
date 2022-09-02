package eac.qloga.android.core.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.features.p4p.showroom.shared.components.ExpandableText
import eac.qloga.android.core.shared.theme.blueTextColor
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.orange1

@Composable
fun Provider(
    modifier : Modifier = Modifier,
    onClickItem: () -> Unit,
    title: String,
    rating: Double,
    imageId: Int,
    description: String,
    statusCompose: @Composable (() -> Unit)
) {
    UserItemOne(
        modifier = modifier,
        onClickItem = { onClickItem() },
        title = title,
        rating = rating,
        imageId = imageId,
        description =description,
        statusCompose = statusCompose
    )
}

@Composable
fun Customer(
    modifier : Modifier = Modifier,
    onClickItem: () -> Unit,
    title: String,
    rating: Double,
    imageId: Int,
    description: String,
    statusCompose: @Composable (() -> Unit)
) {
    UserItemOne(
        modifier = modifier,
        onClickItem = { onClickItem() },
        title = title,
        rating = rating,
        imageId = imageId,
        description =description,
        statusCompose = statusCompose
    )
}

@Composable
private fun UserItemOne(
    modifier : Modifier = Modifier,
    onClickItem: () -> Unit,
    title: String,
    rating: Double,
    imageId: Int,
    description: String,
    statusCompose: @Composable (() -> Unit)
) {
    val cornerRadius = 16.dp
    val imageWidth = 120.dp
    val imageHeight = 120.dp
    val imageContainerWidth= imageWidth + 8.dp // to make star icon overflow out of container
    val imageContainerHeight= imageHeight + 8.dp
    val startIconSize = 70.dp
    val starContainerSize = 36.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadius))
                .clickable { onClickItem() }
                .background(MaterialTheme.colorScheme.background)
                .border(1.4.dp, gray1.copy(alpha = .3f), RoundedCornerShape(cornerRadius))
                .padding(16.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(imageContainerWidth)
                        .height(imageContainerHeight),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        modifier = Modifier
                            .width(imageWidth)
                            .height(imageHeight)
                            .align(Alignment.CenterStart)
                        ,
                        painter = painterResource(id = imageId),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopCenter
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(starContainerSize)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(end = 4.dp, start = 4.dp)
                        ,
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(startIconSize)
                                .align(Alignment.Center),
                            painter = painterResource(id = R.drawable.ic_star_3),
                            contentDescription = "",
                            tint = orange1
                        )

                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.background)
                            ,
                            text = "$rating",
                            color = blueTextColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                    ,
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    statusCompose()
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            ExpandableText(
                text = description,
                minimizedMaxLines = 2,
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.W400
                ),
                expandButtonTextStyle = MaterialTheme.typography.titleSmall
            )
        }
    }
}