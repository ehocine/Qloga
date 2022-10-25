package eac.qloga.android.features.p4p.shared.scenes.portfolioFullView

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import eac.qloga.android.features.p4p.shared.viewmodels.PortfolioViewModel

@Composable
fun PortfolioFullViewScreen(
    viewModel: PortfolioViewModel = hiltViewModel()
) {
    val imageId = viewModel.currentImageDisplay?.link

    val systemUiController = rememberSystemUiController()

    /** Remove the status and nav bar from system */
    systemUiController.isSystemBarsVisible = false

    Box(modifier = Modifier.fillMaxSize()){
        if(imageId != null){
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
                ,
                painter = painterResource(id = imageId),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
    }
}