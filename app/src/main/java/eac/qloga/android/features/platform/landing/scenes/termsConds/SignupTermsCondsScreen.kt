package eac.qloga.android.features.platform.landing.scenes.termsConds

import android.graphics.Bitmap
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.QLOGA_TERMS_CONDITIONS_LINK
import eac.qloga.android.features.platform.landing.scenes.LandingScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupTermsCondsScreen(
    navController: NavController
) {
    val containerHorizontalPadding = 24.dp
    val scrollState = rememberScrollState()
    val roundedCornerRadius = 16.dp
    val webClient = remember {
        object : AccompanistWebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
            }
        }
    }

    val webViewState = rememberWebViewState(QLOGA_TERMS_CONDITIONS_LINK)

    Scaffold(
        topBar = {
            TitleBar(
                iconColor = MaterialTheme.colorScheme.primary,
                label = LandingScreens.SignupTermsConds.titleName
            ){
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = containerHorizontalPadding)
            ) {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(8.dp))

                Cards.ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        WebView(
                            state = webViewState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(roundedCornerRadius)),
                            onCreated = { webView ->
                                true.also { webView.settings.javaScriptEnabled = it }
                            },
                            client = webClient
                        )
                        if(webViewState.isLoading){
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}