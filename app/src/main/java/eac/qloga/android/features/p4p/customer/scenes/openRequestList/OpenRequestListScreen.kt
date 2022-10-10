package eac.qloga.android.features.p4p.customer.scenes.openRequestList

import P4pCustomerScreens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.UserButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.customer.shared.components.OpenRequestListCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenRequestListScreen(
    navController: NavController,
    //viewModel: CustomerNegotiationViewModel
) {
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val horizontalContentPadding = Dimensions.ScreenHorizontalPadding.dp
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pCustomerScreens.OpenRequestList.titleName,
                showBottomBorder = true,
                iconColor = MaterialTheme.colorScheme.primary,
                showBackPressButton = false,
                actions = {
                    UserButton(
                        onClick = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.Account.route+"?$ACCOUNT_TYPE_KEY=${AccountType.CUSTOMER.label}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        },
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) { paddingValues ->
        val titleBarHeight = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //space for title bar
            Spacer(modifier = Modifier.height(titleBarHeight))

            LazyColumn{
                val itemsData = listOf("1","2","3","4","5")
                items(itemsData, key = {it}) { item ->
                    OpenRequestListCard(
                        cardNumber = 100 + item.toInt(),
                        status = "Live",
                        price = 84.00,
                        placedDate = "22/06/2022",
                        placedTime = "09:00",
                        orderedDate = "22/04/2022",
                        orderedTime = "10:10",
                        validDate = "31/04/2022",
                        validTime = "08:00",
                        searchCount = 1,
                        visibleCount = 10,
                        visitCount = 1,
                        showBottomLine = item.toInt() != itemsData.size ,
                        tags = listOf("Cleaning", "Pets", "Care"),
                        onClick = {
//                            navController.navigate(Screen.RequestDetails.route){
//                                launchSingleTop = true
//                            }
                        },
                        onclickArrow = {
//                            navController.navigate(Screen.RequestDetails.route){
//                                launchSingleTop = true
//                            }
                        }
                    )
                }
            }
        }
    }
}