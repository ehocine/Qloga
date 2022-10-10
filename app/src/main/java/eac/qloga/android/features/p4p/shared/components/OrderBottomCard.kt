package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.ComposeFun
import eac.qloga.android.core.shared.components.Items
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountType
import kotlinx.coroutines.launch

@Composable
fun OrderBottomCard(
    navController: NavController,
    accountType: AccountType,
    name: String,
) {
    val iconSize = 24.dp
    val scope = rememberCoroutineScope()

    Cards.ContainerBorderedCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Item(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_ql_setting),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                },
                label = "Services",
                value = "2",
                isItemClickable = true,
                showForwardArrow = true,
                onClickItem = {
                    scope.launch {
                        navController.navigate(P4pScreens.Services.route){
                            launchSingleTop = true
                        }
                    }
                },
            )

            Item(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_info),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                },
                label = if(accountType == AccountType.CUSTOMER) "Provider" else "Customer",
                value = name,
                isItemClickable = true,
                showForwardArrow = true,
                onClickItem = {
                    scope.launch {
                        when(accountType){
                            AccountType.PROVIDER -> {
//                                navController.navigate(Screen.OrderCustomerProfile.route){
//                                    launchSingleTop = true
//                                }
                            }
                            AccountType.CUSTOMER -> {
//                                navController.navigate(Screen.OrderProviderProfile.route){
//                                    launchSingleTop = true
//                                }
                            }
                        }
                    }
                },
            )
            Item(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_ql_flag),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                },
                label = "Visits",
                value = "5",
                isItemClickable = true,
                showForwardArrow = true,
                onClickItem = {
//                    navController.navigate(Screen.OrderVisits.route){
//                        launchSingleTop = true
//                    }
                },
            )
            Item(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_ql_photo),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                },
                label = "Photos",
                value = "8",
                isItemClickable = true,
                showForwardArrow = true,
                onClickItem = {
//                    navController.navigate(
//                        Screen.Albums.route+"?$PARENT_ROUTE_KEY=${Screen.CustomerOrder.route}"
//                    ){
//                        launchSingleTop = true
//                    }
                },
            )
            Item(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_ql_note),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                },
                label = "Notes",
                isItemClickable = true,
                showForwardArrow = true,
                onClickItem = {
//                    navController.navigate(
//                        Screen.OrderNotes.route+"?$ACCOUNT_TYPE_KEY=${accountType.label}"
//                    ) {
//                        launchSingleTop = true
//                    }
                }
            )
            Item(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_ql_pound),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                },
                label = "Payments",
                isItemClickable = true,
                showForwardArrow = true,
                onClickItem = {
//                    navController.navigate(Screen.OrderPayment.route){
//                        launchSingleTop = true
//                    }
                },
            )
        }
    }
}

@Composable
private fun Item(
    modifier: Modifier = Modifier,
    label: String,
    value: String = "",
    isIconClickable: Boolean = true,
    showForwardArrow: Boolean = false,
    showBottomLine: Boolean = true,
    isItemClickable: Boolean,
    leadingIcon: ComposeFun? = null,
    extraTexts : ComposeFun = {},
    onClickItem: () -> Unit = {}
){
    Items.RequestItem(
        modifier = modifier,
        leadingIcon = leadingIcon,
        label = label,
        value = value,
        isIconClickable = isIconClickable,
        isItemClickable = isItemClickable,
        showForwardArrow = showForwardArrow,
        onClickItem = { onClickItem() },
        onClickArrowForward = { onClickItem() },
        extraTexts = extraTexts,
        showBottomLine = showBottomLine
    )
}