package eac.qloga.android.features.p4p.shared.scenes.services

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.Items.ServicesListItem
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    navController: NavController,
    //viewModel: CustomerNegotiationViewModel
) {
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val horizontalContentPadding = Dimensions.ScreenHorizontalPadding.dp
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.Services.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                onBackPress = {
                    navController.navigateUp()
                }
            )
        }
    ) { paddingValues ->
        val titleBarHeight = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalContentPadding)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //space for title bar
            Spacer(modifier = Modifier.height(titleBarHeight))
            Spacer(modifier = Modifier.height(containerTopPadding))

            ContainerBorderedCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ServicesListItem(
                        label = "Complete home cleaning",
                        value = "£60.00",
                        onClickArrowForward = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        },
                        onClickItem = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        }
                    )
                    ServicesListItem(
                        label = "Bathroom and toilet cleaning",
                        value = "£60.00",
                        onClickItem = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        },
                        onClickArrowForward = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        }
                    )
                    ServicesListItem(
                        label = "Kitchen cleaning",
                        value = "£60.00",
                        onClickItem = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        },
                        onClickArrowForward = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        }
                    )
                    ServicesListItem(
                        label = "Bedroom or living cleaning",
                        value = "£60.00",
                        onClickItem = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        },
                        onClickArrowForward = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        }
                    )
                    ServicesListItem(
                        label = "Clothes laundry and ironing",
                        value = "£60.00",
                        onClickItem = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        },
                        onClickArrowForward = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        }
                    )
                    ServicesListItem(
                        label = "Garage cleaning",
                        value = "£60.00",
                        onClickItem = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        },
                        onClickArrowForward = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        }
                    )
                    ServicesListItem(
                        label = "Swimming pool cleaning",
                        value = "£60.00",
                        onClickItem = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        },
                        onClickArrowForward = {
//                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
//                            }
                        }
                    )
                    ServicesListItem(
                        label = "Owen cleaning",
                        value = "£60.00",
                        onClickItem = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        },
                        onClickArrowForward = {
                            scope.launch {
//                                navController.navigate(
//                                    Screen.SelectedService.route+"?$PARENT_ROUTE_KEY=${Screen.ServicesList.route}"
//                                ){
//                                    launchSingleTop = true
//                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    ServicesScreen(navController = NavController(Application()))
}

