package eac.qloga.android.features.p4p.showroom.scenes.serviceContract

import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.scenes.serviceInfo.ServiceInfoViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceContractScreen(
    navController: NavController,
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pShowroomScreens.ServiceContract.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = containerHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topPadding))
            Spacer(modifier = Modifier.height(containerTopPadding))

            ContainerBorderedCard {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    AndroidView(
                        factory = { context -> TextView(context) },
                        update = {
                            it.text = HtmlCompat.fromHtml(
                                ServiceInfoViewModel.servicesWithConditions.value?.service?.contractBareHTML?: "",
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            )
                            it.setLineSpacing(20.0f, 1.0f)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
