package eac.qloga.android.features.p4p.shared.scenes.faQuestions

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.components.FAQItem
import eac.qloga.android.features.p4p.shared.components.QuestionItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.account.ProfilesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaQuestionsScreen(
    navController: NavController,
    viewModel: ProfilesViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val cornerRadius = 16.dp
    val borderWidth = 1.2.dp
    val borderColor = gray1
    val scrollState = rememberScrollState()
    val questionTitle = "General questions"
    val iconId = R.drawable.ic_ql_general_qst
    val faqQuestions = viewModel.faQuestions

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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(cornerRadius))
                        .border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))
                ) {
                    FAQItem(
                        iconId = R.drawable.ic_ql_general_qst,
                        question = questionTitle,
                        expandable = false
                    )
                }

                Spacer(Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(cornerRadius))
                        .border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))
                ) {
                    faqQuestions.forEach { question ->
                        question.question?.let {
                            QuestionItem(question = question.question, answer = question.answer ?: "")
                        }
                    }
                }
            }
        }
    }
}