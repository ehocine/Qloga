package eac.qloga.android.features.p4p.showroom.scenes.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.PARENT_ROUTE_KEY
import eac.qloga.android.core.shared.utils.SCREEN_HORIZONTAL_PADDING
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.shared.scenes.P4pSharedScreens
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.shared.scenes.serviceInfo.ServiceInfoViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.CategoryList
import eac.qloga.android.features.p4p.showroom.shared.components.DescriptionText
import eac.qloga.android.features.p4p.showroom.shared.components.TopNavBar
import eac.qloga.p4p.lookups.dto.QService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    parentRoute: String? = null,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {

    val selectedCategory by CategoriesViewModel.selectedNav

    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val horizontalContentPadding = SCREEN_HORIZONTAL_PADDING.dp
    val detailText = selectedCategory?.descr?.trimMargin()?.repeat(2)
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val categoriesList = ApiViewModel.categories.value.sortedBy {
        it.sortOrder
    }.sortedBy {
        it.catGroupOrder
    }

    var catChanged by remember { mutableStateOf(false) }
//    LaunchedEffect(Unit) {
//        viewModel.onNavClick(ServiceCategory.Cleaning)
//    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pShowroomScreens.Categories.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->

        val titleBarHeight = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //space for title bar
            Spacer(modifier = Modifier.height(titleBarHeight))
            // Top screen Space
            Spacer(modifier = Modifier.height(containerTopPadding))

            TopNavBar(
                onClickItem = {
                    catChanged = !catChanged
                    viewModel.onTriggerEvent(CategoriesEvent.NavItemClick(it))
                    scope.launch {
                        delay(200)
                        catChanged = !catChanged
                    }
                },
                selectedNav = CategoriesViewModel.selectedNav.value,
                navList = categoriesList
            )

            LaunchedEffect(key1 = catChanged) {

            }
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = horizontalContentPadding,
                        top = 24.dp,
                        end = horizontalContentPadding
                    ),
                    verticalArrangement = Arrangement.Center
                ) {
                    //expandable description text
                    if (detailText != null) {
                        DescriptionText(text = detailText)
                    }
                }
                //list card
                selectedCategory?.let {
                    ServicesListCard(
                        navController = navController,
                        listOfServices = it.services,
                        parentRoute = parentRoute ?: "",
                        catChanged = catChanged
                    )
                }
            }
        }
    }
}

@Composable
private fun ServicesListCard(
    modifier: Modifier = Modifier,
    navController: NavController,
    listOfServices: List<QService>,
    parentRoute: String?,
    catChanged: Boolean
) {
    val coroutineScope = rememberCoroutineScope()
    val paddingHorizontal = 24.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = paddingHorizontal, vertical = 28.dp)
    ) {
        ContainerBorderedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                listOfServices.forEach { service ->
                    CategoryList(
                        title = service.name,
                        summery = service.descr,
                        catChanged = catChanged,
                        onClick = {
                            ServiceInfoViewModel.selectedService.value = service
                            coroutineScope.launch {
                                navController.navigate(P4pSharedScreens.ServiceInfo.route)
                            }
                        },
                        onShowProviders = {
                            /*
                            if(parentRoute == Screen.CustomerNavContainer.route){
                                navController.navigateUp()
                            }else{
                                coroutineScope.launch {
                                    // navController.navigate(Screen.Providers.route+"?$PARENT_ROUTE_KEY=$parentRoute")
                                }
                            }

                             */
                        }
                    )
                }
            }
        }
    }
}