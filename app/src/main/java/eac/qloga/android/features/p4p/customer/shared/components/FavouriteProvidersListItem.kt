package eac.qloga.android.features.p4p.customer.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.flowlayout.FlowRow
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Chips.FullRoundedChip
import eac.qloga.android.core.shared.components.DividerLines.LightDividerLine

@Composable
fun FavouriteProvidersListItem(
    modifier: Modifier = Modifier,
    imageId: Int,
    name: String,
    location: String,
    showBottomLine: Boolean = true,
    tags: List<String>,
    onClickDirectInquiry: () -> Unit
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
            Image(
                modifier = Modifier.size(imageWidth),
                painter = rememberAsyncImagePainter(model = imageId),
                contentDescription = null,
            )
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
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
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
                FlowRow {
                    tags.forEach {
                        FullRoundedChip(label = it)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                FullRoundedButton(
                    modifier = Modifier.height(32.dp),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    buttonText = "Direct Inquiry",
                    onClick = { onClickDirectInquiry() }
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
    FavouriteProvidersListItem(
        imageId = R.drawable.pvr_profile_ava,
        name = "Kai's Cleaning agency",
        location = "Edinburgh",
        tags = listOf("Cleaning", "Pets", "Care"),
        onClickDirectInquiry = {}
    )
}