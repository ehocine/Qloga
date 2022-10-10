package eac.qloga.android.features.p4p.shared.scenes.faqDashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.components.FAQItem
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQDashboardScreen(
    navController: NavController,
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val coroutineScope = rememberCoroutineScope()
    val cornerRadius = 16.dp
    val borderWidth = 1.2.dp
    val borderColor = gray1
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
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(cornerRadius))
                        .border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))
                ) {
                    FAQItem(
                        modifier = Modifier
                            .clickable {
                                 coroutineScope.launch {
                                     navController.navigate(P4pScreens.FaQuestions.route)
                                 }
                            }
                        ,
                        iconId = R.drawable.ic_ql_general_qst,
                        question = "General question"
                    )

                    FAQItem(
                        modifier = Modifier
                            .clickable {  }
                        ,
                        iconId = R.drawable.ic_ql_p4p,
                        question = "General services questions"
                    )

                    FAQItem(
                        modifier = Modifier
                            .clickable {  }
                        ,
                        iconId = R.drawable.ic_ql_cst_quick_start,
                        question = "Customer Quickstart"
                    )

                    FAQItem(
                        modifier = Modifier
                            .clickable {  }
                        ,
                        iconId = R.drawable.ic_ql_cst_faq,
                        question = "Customer F.Q.A."
                    )

                    FAQItem(
                        modifier = Modifier
                            .clickable {  }
                        ,
                        iconId = R.drawable.ic_ql_quick_start,
                        question = "Provider Quickstart"
                    )

                    FAQItem(
                        modifier = Modifier
                            .clickable {  }
                        ,
                        iconId = R.drawable.ic_ql_prv_faq,
                        question = "Provider F.A.Q."
                    )
                }
            }
        }
    }
}