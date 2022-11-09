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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.*
import eac.qloga.android.features.p4p.showroom.shared.components.ExpandableText
import eac.qloga.android.features.p4p.showroom.shared.components.IconStatus

@Composable
fun Provider(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
    title: String,
    rating: String,
    imageId: Any,
    description: String,
    statusCompose: @Composable (() -> Unit)
) {
    ProviderItemOne(
        modifier = modifier,
        onClickItem = { onClickItem() },
        title = title,
        rating = rating,
        imageUrl = imageId,
        description = description,
        statusCompose = statusCompose
    )
}

@Composable
fun Customer(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
    title: String,
    rating: String,
    imageUrl: Any,
    description: String,
    visitsCount: Int?,
    statusCompose: @Composable (() -> Unit)
) {
    CustomerItemOne(
        modifier = modifier,
        onClickItem = { onClickItem() },
        title = title,
        rating = rating,
        imageUrl = imageUrl,
        description = description,
        visitsCount = visitsCount,
        statusCompose = statusCompose
    )
}

@Composable
private fun ProviderItemOne(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
    title: String,
    rating: String,
    imageUrl: Any,
    description: String,
    statusCompose: @Composable (() -> Unit)
) {
    val cornerRadius = 16.dp
    val imageWidth = 100.dp
    val imageHeight = 100.dp
    val imageContainerWidth = imageWidth + 8.dp // to make star icon overflow out of container
    val imageContainerHeight = imageHeight
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
        ) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W800,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier
                        .width(imageContainerWidth)
                        .height(imageContainerHeight),
                    contentAlignment = Alignment.TopCenter
                ) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .size(Size.ORIGINAL) // Set the target size to load the image at.
                            .build()
                    )
                    Image(
                        modifier = Modifier
                            .width(imageWidth)
                            .height(imageHeight)
                            .align(Alignment.CenterStart),
                        painter = painter,
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
                                .background(MaterialTheme.colorScheme.background),
                            text = rating,
                            color = blueTextColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
                Column(
                    modifier = Modifier.height(imageContainerHeight),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        statusCompose()
                    }
                }
            }
            if (description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                ExpandableText(
                    text = description,
                    minimizedMaxLines = 2,
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.W400
                    ),
                    expandButtonTextStyle = MaterialTheme.typography.titleSmall,
                    expandableText = " See more",
                    textColor = Color.Black
                )
            }
        }
    }
}

@Composable
private fun CustomerItemOne(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
    title: String,
    rating: String,
    imageUrl: Any,
    description: String,
    visitsCount: Int?,
    statusCompose: @Composable (() -> Unit)
) {
    val cornerRadius = 16.dp
    val imageWidth = 100.dp
    val imageHeight = 100.dp
    val imageContainerWidth = imageWidth + 8.dp // to make star icon overflow out of container
    val imageContainerHeight = imageHeight + 8.dp
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
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier
                        .width(imageContainerWidth)
                        .height(imageContainerHeight),
                    contentAlignment = Alignment.TopCenter
                ) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
//                            .decoderFactory(SvgDecoder.Factory())
                            .data(imageUrl)
                            .size(Size.ORIGINAL) // Set the target size to load the image at.
                            .build()
                    )
                    Image(
                        modifier = Modifier
                            .width(imageWidth)
                            .height(imageHeight)
                            .align(Alignment.BottomCenter)
                            .clip(CircleShape),
                        painter = painter,
                        contentDescription = "",
                        contentScale = ContentScale.Inside,
                        alignment = Alignment.BottomCenter
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(starContainerSize)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(end = 4.dp, start = 4.dp)
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
                                .background(MaterialTheme.colorScheme.background),
                            text = rating,
                            color = blueTextColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
                Column(
                    modifier = Modifier.height(imageContainerHeight),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.W800,
                            color = Color.Black
                        )
                        if (visitsCount != null) {
                            if (visitsCount > 1) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Visits(
                                    count = visitsCount
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        statusCompose()
                    }
                }
            }
            if (description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                ExpandableText(
                    text = description,
                    minimizedMaxLines = 2,
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.W400
                    ),
                    expandButtonTextStyle = MaterialTheme.typography.titleSmall,
                    expandableText = " See more",
                    textColor = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun prev() {
    ProviderItemOne(
        Modifier,
        onClickItem = {},
        title = "Hocine (Edinburgh)",
        rating = "2.0",
        imageUrl = R.drawable.ql_cst_avtr_acc,
        description = "Desc",
        statusCompose = {
            Column(
                Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconStatus(
                        modifier = Modifier.weight(1f),
                        label = "10.0 miles",
                        iconId = R.drawable.ic_ql_miles
                    )
                    IconStatus(
                        modifier = Modifier.weight(1f),
                        label = "${0} hours",
                        iconId = R.drawable.ic_no_shake_hnd
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Callout\nCharge",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                            color = if (true) green1 else gray30,
                            fontWeight = FontWeight.W600
                        )
                    }
                    IconStatus(
                        modifier = Modifier.weight(1f),
                        label = "10.00",
                        iconId = R.drawable.ic_ql_money_bag
                    )
                }
            }
        }
    )
}

@Composable
private fun Visits(
    modifier: Modifier = Modifier,
    count: Int
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.4.dp, blueTextColor, RoundedCornerShape(16.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$count visits",
            modifier = Modifier.padding(start = 8.dp, top = 3.dp, end = 8.dp, bottom = 3.dp),
            style = MaterialTheme.typography.labelMedium,
            color = blueTextColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W600
        )
    }
}