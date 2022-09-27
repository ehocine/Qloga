package eac.qloga.android.features.p4p.showroom.scenes.providerServices

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.ContractButton
import eac.qloga.android.features.p4p.showroom.shared.components.ListInfo
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderServicesScreen(
    navController: NavController,
    viewModel: ProviderServicesViewModel = hiltViewModel()
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val containerHorizontalPadding = 24.dp
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val services = viewModel.serviceWithCategories.value

    LaunchedEffect(key1 = Unit, key2 = ProviderServicesViewModel.providerServices.value){
        viewModel.loadServices()
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pShowroomScreens.ProviderServices.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(horizontal = containerHorizontalPadding)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                services.forEach{ (serviceCategory, servicesList) ->
                    ContainerBorderedCard {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .data(serviceCategory.avatarUrl)
                                        .size(Size.ORIGINAL)
                                        .build()
                                )

                                Image(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(32.dp)
                                    ,
                                    painter = painter,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = serviceCategory.name, style = MaterialTheme.typography.titleLarge)
                            }
                            DividerLines.LightDividerLine()
                            servicesList.forEachIndexed { index, service ->
                                val convertedPrice = String.format("%.2f", service?.unitPrice?.toFloat()?.div(100))

                                Column(
                                    modifier = Modifier
                                        .clickable {  }
                                        .padding(start = 16.dp, top = 12.dp, end = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                        ,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Text(
                                            text = service?.service?.name ?: "",
                                            style = MaterialTheme.typography.titleMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(start = 8.dp),
                                                text = "£$convertedPrice",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.W600
                                            )
                                            Buttons.IOSArrowForwardButton(iconSize = 18.dp, isClickable = false)
                                        }
                                    }
                                    service?.conditions?.forEach {
                                        ListInfo(label = it)
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    if(index != servicesList.size - 1){
                                        DividerLines.LightDividerLine(Modifier.padding(start = 68.dp))
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
