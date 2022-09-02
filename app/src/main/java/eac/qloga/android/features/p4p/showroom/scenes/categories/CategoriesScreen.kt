package eac.qloga.android.features.intro.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.scenes.categories.CategoriesEvent
import eac.qloga.android.features.p4p.showroom.scenes.categories.CategoriesViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.CategoryList
import eac.qloga.android.features.p4p.showroom.shared.components.DescriptionText
import eac.qloga.android.features.p4p.showroom.shared.components.TopNavBar
import eac.qloga.android.features.p4p.showroom.shared.utils.ServiceCategory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    parentRoute: String? = null,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val horizontalContentPadding = SCREEN_HORIZONTAL_PADDING.dp
    val detailText = "Residential cleaning services for all parts of your home. With " +
        "tasks covering dishwashing, cleaning bathrooms, waste removal," +
        "furniture cleaning, window cleaning ".trimMargin().repeat(2)
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        viewModel.onNavClick(ServiceCategory.Cleaning)
    }

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
                onClickItem = { viewModel.onTriggerEvent(CategoriesEvent.NavItemClick(it)) },
                selectedNav = viewModel.selectedNav.value,
                navList = ServiceCategory.listOfItems
            )

            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.padding(start = horizontalContentPadding, top = 24.dp, end = horizontalContentPadding),
                    verticalArrangement = Arrangement.Center
                ) {
                    //expandable description text
                    DescriptionText(text = detailText)
                }

                //list card
                ServicesListCard(
                    navController = navController,
                    parentRoute = parentRoute ?: ""
                )
            }
        }
    }
}

@Composable
private fun ServicesListCard(
    modifier: Modifier = Modifier,
    navController: NavController,
    parentRoute: String?
) {
    val categoryList = listOf(
        "Complete home cleaning",
        "Bathroom and toilet cleaning",
        "Kitchen cleaning",
        "Bedroom or living room cleaning",
        "Clothes laundry and ironing",
        "Garrage cleaning",
        "Swimming pool cleaning",
        "Owen cleaning"
    )
    val summery = "Cleaning and disinfecting surfaces and floors, dusting, vacuuming," +
            " mopping including cupboards, skirting boards , " +
            "mirrors, pictures and ornaments, cleaning and tidying every"
    val coroutineScope = rememberCoroutineScope()
    val paddingHorizontal  = 24.dp

    Box(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = paddingHorizontal, vertical = 28.dp)
    ) {
        ContainerBorderedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CategoryList(
                    title = "Complete home cleaning",
                    summery = summery,
                    onClickI = { coroutineScope.launch {
                        navController.navigate(
                            P4pShowroomScreens.ServiceInfo.route +"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.Categories.route}"
                        )
                    }},
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

                CategoryList(
                    title = "Bathroom and toilet cleaning",
                    summery = summery,
                    onClickI = { coroutineScope.launch {
                        navController.navigate(
                            P4pShowroomScreens.ServiceInfo.route +"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.Categories.route}"
                        )
                    }},
                    onShowProviders = {
                        coroutineScope.launch {
                            /*
                            if(parentRoute == Screen.CustomerNavContainer.route){
                                navController.navigateUp()
                            }else{
                                coroutineScope.launch {
                                //    navController.navigate(Screen.Providers.route+"?$PARENT_ROUTE_KEY=$parentRoute")
                                }
                            }

                             */
                        }
                    }
                )

                CategoryList(
                    title = "Kitchen cleaning",
                    summery = summery,
                    onClickI = { coroutineScope.launch {
                        navController.navigate(
                            P4pShowroomScreens.ServiceInfo.route +"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.Categories.route}"
                        )
                    }},
                    onShowProviders = {
                        coroutineScope.launch {
                            /*
                            if(parentRoute == Screen.CustomerNavContainer.route){
                                navController.navigateUp()
                            }else{
                                coroutineScope.launch {
                                //    navController.navigate(Screen.Providers.route+"?$PARENT_ROUTE_KEY=$parentRoute")
                                }
                            }

                             */
                        }
                    }
                )

                CategoryList(
                    title = "Bedroom or living room cleaning",
                    summery = summery ,
                    onClickI = { coroutineScope.launch {
                        navController.navigate(
                            P4pShowroomScreens.ServiceInfo.route +"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.Categories.route}"
                        )
                    }},
                    onShowProviders = {
                        coroutineScope.launch {
                            /*
                            if(parentRoute == Screen.CustomerNavContainer.route){
                                navController.navigateUp()
                            }else{
                                coroutineScope.launch {
                                    navController.navigate(Screen.Providers.route+"?$PARENT_ROUTE_KEY=$parentRoute")
                                }
                            }
                             */
                        }
                    }
                )

                CategoryList(
                    title = "Clothes laundry and ironing",
                    summery = summery ,
                    onClickI = { coroutineScope.launch {
                        navController.navigate(
                            P4pShowroomScreens.ServiceInfo.route +"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.Categories.route}"
                        )
                    }},
                    onShowProviders = {
                        coroutineScope.launch {
                            /*
                            if(parentRoute == Screen.CustomerNavContainer.route){
                                navController.navigateUp()
                            }else{
                                coroutineScope.launch {
                                    navController.navigate(Screen.Providers.route+"?$PARENT_ROUTE_KEY=$parentRoute")
                                }
                            }

                             */
                        }
                    }
                )

                CategoryList(
                    title = "Garrage cleaning",
                    summery = summery ,
                    onClickI = { coroutineScope.launch {
                        navController.navigate(
                            P4pShowroomScreens.ServiceInfo.route +"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.Categories.route}"
                        )
                    }},
                    onShowProviders = {
                        coroutineScope.launch {
                            /*
                            if(parentRoute == Screen.CustomerNavContainer.route){
                                navController.navigateUp()
                            }else{
                                coroutineScope.launch {
                                    navController.navigate(Screen.Providers.route+"?$PARENT_ROUTE_KEY=$parentRoute")
                                }
                            }
                             */
                        }
                    }
                )

                CategoryList(
                    title = "Swimming pool cleaning",
                    summery = summery ,
                    onClickI = { coroutineScope.launch {
                        navController.navigate(
                            P4pShowroomScreens.ServiceInfo.route +"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.Categories.route}"
                        )
                    }},
                    onShowProviders = {
                        coroutineScope.launch {
                            /*
                            if(parentRoute == Screen.CustomerNavContainer.route){
                                navController.navigateUp()
                            }else{
                                coroutineScope.launch {
                                    navController.navigate(Screen.Providers.route+"?$PARENT_ROUTE_KEY=$parentRoute")
                                }
                            }
                             */
                        }
                    }
                )

                CategoryList(
                    title = "Owen cleaning",
                    showDivider = false,
                    summery = summery ,
                    onClickI = { coroutineScope.launch {
                        navController.navigate(
                            P4pShowroomScreens.ServiceInfo.route +"?$PARENT_ROUTE_KEY=${P4pShowroomScreens.Categories.route}"
                        )
                    }},
                    onShowProviders = {
                        coroutineScope.launch {
                            /*
                            if(parentRoute == Screen.CustomerNavContainer.route){
                                navController.navigateUp()
                            }else{
                                coroutineScope.launch {
                                    navController.navigate(Screen.Providers.route+"?$PARENT_ROUTE_KEY=$parentRoute")
                                }
                            }
                             */
                        }
                    }
                )
            }
        }
    }
}