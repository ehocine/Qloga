package eac.qloga.android.features.p4p.shared.scenes.tc

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.DotCircleArcCanvas
import eac.qloga.android.core.shared.components.DottedLine
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.grayTextColor
import eac.qloga.android.core.shared.utils.CUSTOMER_TERMS_CONDITIONS_LINK
import eac.qloga.android.core.shared.utils.PROVIDER_TERMS_CONDITIONS_LINK
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.EnrollmentEvent
import eac.qloga.android.features.p4p.shared.utils.EnrollmentType
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnrollmentTcScreen(
    navController: NavController,
    viewModel: EnrollmentViewModel = hiltViewModel()
) {
    val roundedCornerRadius = 16.dp
    val containerHorizontalPadding = 24.dp
    val checkTermsConditions = viewModel.isCheckTermsConditions.value
    val providerTermsConditionsLink = PROVIDER_TERMS_CONDITIONS_LINK
    val customerTermsConditionsLink = CUSTOMER_TERMS_CONDITIONS_LINK
    val enrollmentType = viewModel.enrollmentType.value
    val webClient = remember {
        object : AccompanistWebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                Log.d("Accompanist WebView", "Page started loading for $url")
            }
        }
    }

    val webViewState = rememberWebViewState(
        url = if (enrollmentType == EnrollmentType.CUSTOMER) {
            customerTermsConditionsLink
        } else providerTermsConditionsLink
    )

    val enableCheckBox = remember{
        derivedStateOf {
            !(webViewState.isLoading || webViewState.errorsForCurrentRequest.isNotEmpty())
        }
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.EnrollmentTermsConditions.titleName,
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
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(topPadding + 4.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .height(20.dp)
                            .align(Alignment.CenterStart)
                            .padding(end = 20.dp)
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        DottedLine(
                            arcStrokeColor = gray1,
                            vertical = false
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    ){
                        DotCircleArcCanvas(
                            arcStrokeColor = gray1,
                            circleColor = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clip(RoundedCornerShape(roundedCornerRadius))
                        .padding(horizontal = containerHorizontalPadding)
                        .border(1.5.dp, gray1, RoundedCornerShape(roundedCornerRadius)),
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

                Column(
                    modifier= Modifier
                        .padding(horizontal = containerHorizontalPadding)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkTermsConditions,
                            enabled = enableCheckBox.value,
                            onCheckedChange = { viewModel.onTriggerEvent(EnrollmentEvent.ToggleCheckTermsConditions) },
                        )
                        Text(
                            modifier = Modifier.alpha(.65f),
                            text = "Agree with the terms and conditions",
                            style = MaterialTheme.typography.titleMedium,
                            color = grayTextColor
                        )
                    }
                    //accept button
                    FullRoundedButton(
                        modifier = Modifier
                            .padding( vertical = 16.dp),
                        buttonText = "Accept",
                        enabled = viewModel.isCheckTermsConditions.value,
                        textColor = MaterialTheme.colorScheme.background,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                    ) {
                        coroutineScope.launch {
                            /*
                            navController.navigate(Screen.GotoProfile.route +
                                    "?accountType=${if(enrollmentType == EnrollmentType.CUSTOMER) 
                                        AccountType.CUSTOMER.label else AccountType.PROVIDER.label}"
                            )
                             */
                        }
                    }
                }
            }
        }
    }
}