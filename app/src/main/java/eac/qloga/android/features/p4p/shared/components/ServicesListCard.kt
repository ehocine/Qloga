package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.PriceConverter
import eac.qloga.android.data.shared.models.ServicesWithConditions
import eac.qloga.android.features.p4p.customer.shared.components.CustomerBottomNavItems
import eac.qloga.android.features.p4p.customer.shared.viewModels.CustomerDashboardViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderServicesViewModel
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.scenes.providerSearch.ProvidersTabItems
import eac.qloga.android.features.p4p.shared.scenes.serviceInfo.ServiceInfoViewModel
import eac.qloga.android.features.p4p.shared.viewmodels.ProviderSearchViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.ServicesListItem
import eac.qloga.p4p.lookups.dto.QService
import eac.qloga.p4p.prv.dto.ProviderService
import eac.qloga.p4p.rq.dto.RqService
import kotlinx.coroutines.launch

@Composable
fun ServicesListCard(
    modifier: Modifier = Modifier,
    navController: NavController,
    listOfServices: List<QService>,
    expandableItem: Boolean = true,
    providerServices: List<ProviderService>? = null,
    catChanged: Boolean
) {
    val coroutineScope = rememberCoroutineScope()
    val paddingHorizontal = Dimensions.ScreenHorizontalPadding.dp

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
                    val providerService = providerServices?.find { it.qServiceId == service.id }
                    val price = PriceConverter.priceToFloat(providerService?.unitCost?.toFloat())

                    ServicesListItem(
                        title = service.name,
                        summery = service.descr,
                        catChanged = catChanged,
                        expandable = expandableItem,
                        isSelected = providerService != null ,
                        onClickItem = {
                            ProviderServicesViewModel.providedQService = QService()
                            ProviderServicesViewModel.providedQService = service
                            ProviderServicesViewModel.providedPrvService = ProviderService()
                            ProviderServicesViewModel.providedPrvService = providerService ?: ProviderService()
                            ServiceInfoViewModel.servicesWithConditions.value = ServicesWithConditions(service,null,null)
                            navController.navigate(P4pProviderScreens.ProvidedService.route)
                        },
                        value = if(!price.isNullOrEmpty()) "£$price" else "",
                        onClick = {
                            ServiceInfoViewModel.servicesWithConditions.value = ServicesWithConditions(service,null,null)
                            coroutineScope.launch {
                                navController.navigate(P4pScreens.ServiceInfo.route)
                            }
                        },
                        onShowProviders = {
                            ProviderSearchViewModel.providersFirstSearch.value = false
                            ProviderSearchViewModel.singleServiceFirstSearch.value = true
                            ProviderSearchViewModel.selectedServiceId.value = service.id
                            ProviderSearchViewModel.singleService.value = service.let {
                                RqService(it.id, it.id, 0)
                            }
                            ProviderSearchViewModel.selectedProvidersTab.value =
                                ProvidersTabItems.SELECT_SERVICES

                            coroutineScope.launch {
                                ProviderSearchViewModel.getFirstRqsState.emit(LoadingState.LOADED)
                                CustomerDashboardViewModel.selectedNavItem.value =
                                    CustomerBottomNavItems.PROVIDERS
                                CustomerDashboardViewModel.alreadyShownProfileInfoDialog =
                                    true
                                navController.navigate(P4pCustomerScreens.CustomerDashboard.route) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}