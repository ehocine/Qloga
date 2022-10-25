package eac.qloga.android.features.p4p.shared.scenes.portfolio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.viewmodels.PortfolioViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(
    navController: NavController,
    viewModel: PortfolioViewModel = hiltViewModel()
) {
    val listImageSize = 62.dp
    val images = viewModel.images
    val isFullTextDetails = viewModel.showFullTextDetails

    val scope = rememberCoroutineScope()
    val lazyScrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = !isFullTextDetails,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                TitleBar(
                    label = P4pScreens.Portfolio.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    showBottomBorder = true
                ) {
                    navController.navigateUp()
                }
            }
        }
    ) { paddingValues ->

        val topPadding = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //space for title bar
            if(!isFullTextDetails){
                Spacer(modifier = Modifier.height(topPadding))
            }

            Box(modifier = Modifier.fillMaxSize()) {
                viewModel.currentImageDisplay?.let {
                    ImageContainer(
                        modifier = Modifier.fillMaxHeight(.80f),
                        imageId = it.link,
                        fullText = viewModel.showFullTextDetails,
                        imageDescription = it.description,
                        onClickImage = {
                            navController.navigate(P4pScreens.PortfolioFullView.route)
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.20f)
                        .align(Alignment.BottomCenter)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = lazyScrollState,
                        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)
                    ){
                        items(images, key = {it.id}){ image ->
                            Image(
                                modifier = Modifier
                                    .size(listImageSize)
                                    .clickable {
                                        viewModel.onChangeImage(image)
                                        scope.launch {
                                            lazyScrollState.animateScrollToItem(
                                                images.indexOf(image)
                                            )
                                        }
                                    }
                                ,
                                painter = painterResource(id = image.link),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(44.dp)
                                .clickable { viewModel.onClickInfo() }
                                .clip(CircleShape)
                                .clickable { viewModel.onClickInfo() }
                            ,
                            imageVector = Icons.Rounded.Info,
                            contentDescription = "",
                            tint = if(isFullTextDetails) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ImageContainer(
    modifier: Modifier = Modifier ,
    imageId: Int ,
    fullText: Boolean = false,
    onClickImage: () -> Unit,
    imageDescription: String? = null
){
    val interactionSource = remember{ MutableInteractionSource() }

    Column(modifier = modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .animateContentSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onClickImage()
                }
            ,
            painter = painterResource(id = imageId),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        
        imageDescription?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .animateContentSize()
            ) {
                Text(
                    modifier = Modifier.alpha(.75f),
                    text = it,
                    overflow = if(fullText) TextOverflow.Visible else TextOverflow.Ellipsis  ,
                    maxLines = if(!fullText) 4 else 100,
                    style = MaterialTheme.typography.bodySmall,
                    color = gray30
                )
            }
        }
    }
}