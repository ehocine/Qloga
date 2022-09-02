package eac.qloga.android.features.p4p.showroom.scenes.serviceContract

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceContractScreen(
    navController: NavController,
) {
    val containerHorizontalPadding = 24.dp
    val containerTopPadding = 16.dp

    val residentialCSText = "Residential cleaning is the ideal" +
            " regular or one-off cleaning service" +
            " for homes. It covers professional " +
            "cleaning all common areas of a home, " +
            "including regular cleaning activities like" +
            " dusting, vacuuming, disinfecting " +
            "surfaces, removing rubbish, tidying, etc."

    val definingCSText = "The Residential Cleaning service is for families or individuals," +
            " those who live on the property that requires cleaning or when someone hires a " +
            "cleaner for a friend/relative’s property.\n" +
            "\n" +
            "This Service covers standard cleaning of all areas of a home that a Provider " +
            "can reach while standing on the floor and that do not require heavy lifting or" +
            " industrial cleaning chemicals.\n" +
            "\n" +
            "As an hourly service, the Provider will complete as much work as they can" +
            " within the amount of time agreed in the Order. For best results, Customers " +
            "should explain the activities they want the Provider to complete first, which" +
            " areas should not be missed and which activities that should be completed last" +
            " if the Provider has time."

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
                .padding(horizontal = containerHorizontalPadding)
            ,
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
                    Text(
                        text = "\"Residential Cleaning\" service",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = residentialCSText,
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Defining Residential Cleaning",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = definingCSText,
                        style = MaterialTheme.typography.titleMedium,
                        color = gray30
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
