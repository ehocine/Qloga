package eac.qloga.android.features.p4p.provider.scenes.servicesConditions

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import eac.qloga.android.core.shared.components.Chips.SelectedServiceChip
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.ServiceConditionItem
import eac.qloga.android.features.p4p.shared.scenes.account.ProfilesEvent
import eac.qloga.android.features.p4p.shared.scenes.account.ProfilesViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.DescriptionText
import eac.qloga.android.features.p4p.showroom.shared.components.TopNavBar
import eac.qloga.p4p.lookups.dto.ServiceCategory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesConditionsScreen(
    navController: NavController,
    viewModel: ProfilesViewModel = hiltViewModel()
) {
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val horizontalContentPadding = Dimensions.ScreenHorizontalPadding.dp
    val detailText = "Residential cleaning services for all parts of your home. " +
            "With tasks covering dishwashing, cleaning bathrooms, waste removal," +
            "furniture cleaning, window cleaning".trimMargin().repeat(2)

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val categoriesList = ApiViewModel.categories.value.sortedBy {
        it.sortOrder
    }.sortedBy {
        it.catGroupOrder
    }

    LaunchedEffect(Unit){
//        viewModel.onSelectedNav(ServiceCategory.Cleaning)
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.ServicesConditions.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
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
            // Top screen Space
            Spacer(modifier = Modifier.height(containerTopPadding))

            TopNavBar(
                onClickItem = { viewModel.onTriggerEvent(ProfilesEvent.SelectTopNavItem(it)) },
                selectedNav =ServiceCategory(),
                scrollable = true,
                navList = categoriesList
            )

            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.padding(start = horizontalContentPadding, top = 16.dp, end = horizontalContentPadding),
                    verticalArrangement = Arrangement.Center
                ) {
                    DescriptionText(text = detailText)
                }

                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalContentPadding)){
                    Text(
                        text = "Selected",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    FlowRow(modifier = Modifier.fillMaxWidth()) {
                        SelectedServiceChip(label = "Cleaning", count = 3)
                        SelectedServiceChip(label = "Pets", count = 3)
                        SelectedServiceChip(label = "Gas", count = 3)
                        SelectedServiceChip(label = "Handyman", count = 5)
                        SelectedServiceChip(label = "Care", count = 2)
                    }
                }

                Spacer(Modifier.height(14.dp))

                //list card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding( start = horizontalContentPadding, end = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, gray1, shape = RoundedCornerShape(16.dp))
                ) {
                    ServiceConditionItem(
                        title = "Complete home cleaning",
                        value = "$160.00"
                    ){
                        coroutineScope.launch {
//                            navController.navigate(Screen.ProvidedService.route){
//                                launchSingleTop = true
//                            }
                        }
                    }

                    ServiceConditionItem(
                        title = "Bathroom and toilet cleaning",
                    ){}

                    ServiceConditionItem(
                        title = "Kitchen cleaning",
                        value = "$40.00"
                    ){}

                    ServiceConditionItem(
                        title = "Bedroom or living room cleaning",
                    ){}

                    ServiceConditionItem(
                        title = "Clothes laundry and ironing",
                    ){}

                    ServiceConditionItem(
                        title = "Garrage cleaning",
                    ){}

                    ServiceConditionItem(
                        title = "Swimming pool cleaning",
                    ){}

                    ServiceConditionItem(
                        title = "Owen cleaning",
                        showDivider = false,
                    ){}
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}