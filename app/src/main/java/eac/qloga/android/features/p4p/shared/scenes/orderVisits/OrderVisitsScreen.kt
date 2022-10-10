package eac.qloga.android.features.p4p.shared.scenes.orderVisits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.features.p4p.shared.components.OrderVisitCard
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderVisitsScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val containerTopPadding = 16.dp
    val containerHorizontalPadding = 24.dp
    val dateFrom = "20/06/2022"
    val dateTo = "26/06/2022"

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.OrderVisits.titleName,
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
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = containerHorizontalPadding)
            ) {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "$dateFrom - $dateTo",
                        style = MaterialTheme.typography.titleSmall,
                        color = gray30
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .clip(RoundedCornerShape(6.dp))
                            .background(orange1)
                            .padding(horizontal = 4.dp)
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Today",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                OrderVisitCard(date = "22/06/2022 TUE")
                Spacer(modifier = Modifier.height(24.dp))
                OrderVisitCard(date = "23/06/2022 WED")
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}