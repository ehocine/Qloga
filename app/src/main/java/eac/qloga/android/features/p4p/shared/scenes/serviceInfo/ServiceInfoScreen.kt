package eac.qloga.android.features.p4p.shared.scenes.serviceInfo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import eac.qloga.android.NavigationActions
import eac.qloga.android.core.shared.components.PulsePlaceholder
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.PriceConverter
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.shared.components.ExpandableConditionsListItem
import eac.qloga.android.features.p4p.showroom.shared.components.SelectedListItem
import eac.qloga.android.features.p4p.showroom.shared.components.StatusButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceInfoScreen(
    navActions: NavigationActions,
) {
    val services by ServiceInfoViewModel.servicesWithConditions

    val imageWidth = 120.dp
    val containerTopPadding = CONTAINER_TOP_PADDING.dp

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.ServiceInfo.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) { navActions.upPress() }
        }
    ) { paddingValues ->

        val topPadding = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topPadding))
            Spacer(modifier = Modifier.height(containerTopPadding))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = services?.service?.name ?: "",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W600
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Unit:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            text = "${services?.service?.unit ?: ""}(${services?.service?.unitDescr ?: ""})",
                            style = MaterialTheme.typography.titleMedium,
                            color = gray30,
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Price:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if(services?.unitPrice != null){
                            Text(
                                modifier = Modifier
                                    .alpha(.75f)
                                    .padding(start = 8.dp),
                                text = "£${PriceConverter.priceToFloat(services?.unitPrice?.toFloat())}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp
                                ),
                                color = gray30,
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Time norm:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier
                                .alpha(.75f)
                                .padding(start = 8.dp),
                            text = "${services?.service?.timeNorm} min",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp
                            ),
                            color = gray30,
                        )
                    }
                }

                //image
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(imageWidth)
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(services?.service?.avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = green1)
                                PulsePlaceholder(
                                    modifier = Modifier.size(imageWidth),
                                )
                            }
                        }
                        else -> {
                            SubcomposeAsyncImageContent(
                                modifier = Modifier,
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.TopCenter
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            services?.service?.let {
                SelectedListItem(
                    title = "Description:",
                    label = it.descr
                )
            }

            if(services?.conditions != null && services!!.conditions?.isNotEmpty() == true){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Conditions: ",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
            }

            services?.conditions?.forEach { condition ->
                ExpandableConditionsListItem(
                    label = condition.name,
                    description = condition.descr
                )
                Spacer(Modifier.height(4.dp))
            }
            Spacer(Modifier.height(32.dp))
            StatusButton(
                label = "Full service contract",
                count = "",
                trailingIcon = Icons.Rounded.ArrowForwardIos,
                clickable = true,
                onClick = {
                    navActions.goToServiceContract.invoke()
                }
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}
