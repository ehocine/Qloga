package eac.qloga.android.features.p4p.shared.scenes.faqDashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.features.p4p.shared.components.FAQItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.faQuestions.FaqViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQDashboardScreen(
    navController: NavController,
    viewModel: FaqViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp

    val faqState = viewModel.faqState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.FAQDashboard.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) { navController.navigateUp() }
        }
    ) { paddingValues ->

        val topPadding = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = containerHorizontalPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                if(faqState.value == LoadingState.LOADING){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }else{
                    Cards.ContainerBorderedCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            viewModel.faqGroups.value.forEach { faqGroup ->
                                FAQItem(
                                    modifier = Modifier
                                        .clickable {
                                            coroutineScope.launch {
                                                FaqViewModel.faQuestions = faqGroup
                                                navController.navigate(P4pScreens.FaQuestions.route)
                                            }
                                        }
                                    ,
                                    image = faqGroup.icon,
                                    question = faqGroup.title
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}