package eac.qloga.android.features.p4p.shared.scenes.searchedAddrResult

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.SaveButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.scenes.addAddress.AddressEvent
import eac.qloga.android.features.p4p.shared.viewmodels.AddressViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchedAddrResultScreen(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val addressList = viewModel.listOfAddress.value
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.SearchedAddrResult.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    SaveButton(onClick = {/** TOTO*/}) }
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ){
                Text(
                    modifier = Modifier.align(Alignment.Top),
                    text = viewModel.addressOrPostcode.value,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Image(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .clickable {
                            scope.launch {
                                //navController.navigate(Screen.MapView.route)
                            }
                        }
                    ,
                    painter = painterResource(id = R.drawable.ic_location_point),
                    contentDescription = ""
                )
            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    addressList.forEachIndexed { index, value ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onTriggerEvent(AddressEvent.EnterText(value))
                                    //viewModel.setAddressState()
                                    scope.launch {
                                        navController.popBackStack()
                                        navController.navigate(P4pShowroomScreens.AddAddress.route)
                                    }
                                }
                                .padding(
                                    top = if (index == 0) 16.dp else 8.dp,
                                    bottom = if (index == addressList.size - 1) 16.dp else 8.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                )
                        ) {
                            Text(
                                modifier = Modifier.alpha(.75f),
                                text = value,
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                        }
                    }
                }
            }
        }
    }
}