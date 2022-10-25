package eac.qloga.android.features.p4p.shared.scenes.prvCstTC

import android.graphics.Bitmap
import android.webkit.WebView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.size.Dimension
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.CustomerTabsType
import eac.qloga.android.features.p4p.shared.utils.ProvidersTabsType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrvCstTCScreen(
    navController: NavController,
) {
    val roundedCornerRadius = 12.dp
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val tabHeight = 60.dp
    val accountType = PrvCstTCViewModel.accountType
    val selectedTabIndex = remember{ mutableStateOf(0) }
    val tsCsUrl = remember{ mutableStateOf(ProvidersTabsType.QLOGA.url) }

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

    val webViewState = rememberWebViewState(tsCsUrl.value)

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.PrvCstTC.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
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
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                TabRow(
                    modifier = Modifier.height(tabHeight),
                    selectedTabIndex = selectedTabIndex.value
                ) {
                    if (accountType == AccountType.CUSTOMER) {
                        CustomerTabsType.listOfItems.forEachIndexed { index, type ->
                            val isSelected = index == selectedTabIndex.value
                            Tab(
                                selected = isSelected,
                                text = {
                                    Text(
                                        text = type.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center,
                                    )
                                },
                                selectedContentColor = MaterialTheme.colorScheme.primary,
                                unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                                onClick = {
                                    selectedTabIndex.value = index
                                    tsCsUrl.value = type.url
                                }
                            )
                        }
                    }

                    if (accountType == AccountType.PROVIDER) {
                        ProvidersTabsType.listOfItems.forEachIndexed { index, type ->
                            val isSelected = index == selectedTabIndex.value
                            Tab(
                                selected = isSelected,
                                text = {
                                    Text(
                                        text = type.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center,
                                    )
                                },
                                selectedContentColor = MaterialTheme.colorScheme.primary,
                                unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                                onClick = {
                                    selectedTabIndex.value = index
                                    tsCsUrl.value = type.url
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(roundedCornerRadius))
                        .border(1.5.dp, gray1.copy(.3f), RoundedCornerShape(roundedCornerRadius)),
                    contentAlignment = Alignment.Center
                ){
                    //terms and conditions webviews
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