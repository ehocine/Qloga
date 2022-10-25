package eac.qloga.android.features.p4p.shared.scenes.faQuestions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.components.FAQItem
import eac.qloga.android.features.p4p.shared.components.QuestionItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaQuestionsScreen(
    navController: NavController,
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val scrollState = rememberScrollState()
    val faqQuestions = FaqViewModel.faQuestions

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.FaQuestions.titleName,
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

                Cards.ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        FAQItem(
                            question = faqQuestions?.title ?: "",
                            image = faqQuestions?.icon,
                            expandable = false
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Cards.ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        faqQuestions?.questions?.forEach { question ->
                            question.q?.let {
                                QuestionItem(question = question.q, answer = question.a ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}