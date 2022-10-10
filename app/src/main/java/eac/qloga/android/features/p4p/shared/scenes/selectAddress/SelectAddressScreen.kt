package eac.qloga.android.features.p4p.shared.scenes.selectAddress

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.AddNewButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.shared.viewmodels.AddressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAddressScreen(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val addressList = viewModel.listOfAddress.value
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val iconSize = 18.dp
    val selectedAddress = viewModel.selectedAddress.value

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.SelectAddress.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    AddNewButton(
                        onClick = {
                            navController.navigate(P4pShowroomScreens.AddAddress.route)
//                            navController.navigate(P4pScreens.NegotiationAddressAdd.route){
//                                launchSingleTop = true
//                            }
                        }
                    )
                }
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->

        val titleBarHeight = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = containerHorizontalPadding)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(titleBarHeight))
            Spacer(modifier = Modifier.height(containerTopPadding))

            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp)
            ){
                items(items = addressList, key = {it} ){ address ->
                    val isSelected = address == selectedAddress
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.setAddress(address)
                            }
                            .padding(4.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .animateContentSize()
                            ,
                            text = address,
                            style = MaterialTheme.typography.titleMedium,
                            color = if(isSelected) MaterialTheme.colorScheme.primary else gray30
                        )
                        if(isSelected){
                            Icon(
                                modifier = Modifier
                                    .size(iconSize)
                                    .wrapContentWidth(),
                                painter = painterResource(id = R.drawable.ic_ql_check_mark),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }else{
                            Spacer(modifier = Modifier.width(iconSize))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}