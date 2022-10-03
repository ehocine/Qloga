package eac.qloga.android.features.p4p.shared.scenes.serviceInfo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
import eac.qloga.android.features.p4p.shared.scenes.P4pSharedScreens
import eac.qloga.android.features.p4p.showroom.shared.components.ExpandableConditionsListItem
import eac.qloga.android.features.p4p.showroom.shared.components.SelectedListItem
import eac.qloga.android.features.p4p.showroom.shared.components.StatusButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceInfoScreen(
    navActions: NavigationActions,
    viewModel: ServiceInfoViewModel = hiltViewModel()
) {
    val selectedService by ServiceInfoViewModel.selectedService

    val imageWidth = 120.dp
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val conditions = emptyList<String>()

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pSharedScreens.ServiceInfo.titleName,
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
                text = selectedService?.name ?: "",
                style = MaterialTheme.typography.titleMedium
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
                            text = "${selectedService?.unit ?: ""}(${selectedService?.unitDescr ?: ""})",
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
                        Text(
                            modifier = Modifier
                                .alpha(.75f)
                                .padding(start = 8.dp),
                            text = "100.0 £",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp
                            ),
                            color = gray30,
                        )
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
                            text = "${selectedService?.timeNorm} min",
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
                        .data(selectedService?.avatarUrl)
                        .crossfade(true)
//                        .error(R.drawable.account)
                        .build(),
                    contentDescription = null
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = green1)
                                PulsePlaceholder(
                                    modifier = Modifier.width(imageWidth),
                                    roundedCornerShape = RoundedCornerShape(8.dp)
                                )
                            }
                        }
                        else -> {
                            SubcomposeAsyncImageContent(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.TopCenter
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            selectedService?.let {
                SelectedListItem(
                    title = "Description:",
                    label = it.descr
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Conditions: ",
                style = MaterialTheme.typography.titleMedium
            )

//            Text(
//                modifier = Modifier.fillMaxWidth(),
//                text = "Conditions: ",
//                style = MaterialTheme.typography.titleMedium
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            conditionsList.forEach {
//                ExpandableConditionsListItem(
//                    label = it.name,
//                    description = it.descr
//                )
//                Spacer(Modifier.height(8.dp))
//            }
            Spacer(Modifier.height(8.dp))
            conditions.forEach { _ ->
                ExpandableConditionsListItem(
                    label = "",
                    description = ""
                )
                Spacer(Modifier.height(8.dp))
            }
            Spacer(Modifier.height(8.dp))
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
