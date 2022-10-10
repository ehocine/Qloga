package eac.qloga.android.features.p4p.shared.scenes.orderMapGps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.components.address.AddressMapView
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderMapGpsScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val containerTopPadding = 16.dp
    val containerHorizontalPadding = 24.dp
    val mapLatLng = remember{ mutableStateOf(LatLng(54.9715, -1.6123)) }
    val markerTitle = "30 Cloth Market, Merchant House, Newcastle upon \nTyne, GB"
    val infoText = "You can carry on without leaving " +
            "your GPS cords. However, in this case" +
            " QLOGA complaints team may not be able " +
            "to assist you if the customer refuses to" +
            " pay due your absence on site!  "

    val isChecked = remember{ mutableStateOf(false)}

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.OrderMapGps.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    AddressMapView(
                        latitude = mapLatLng.value.latitude,
                        longitude = mapLatLng.value.longitude,
                        title = markerTitle,
                        onClickMap = {
                            mapLatLng.value = it
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(horizontal = containerHorizontalPadding)
                            .align(Alignment.TopCenter)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 8.dp),
                            text = infoText,
                            style = MaterialTheme.typography.titleMedium,
                            color = orange1
                        )

                        Box(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        isChecked.value = !isChecked.value
                                    }
                                    .background(if (isChecked.value) orange1 else Color.Transparent)
                                    .border(1.dp, orange1, CircleShape)
                                ,
                                contentAlignment = Alignment.Center
                            ) {
                                if(isChecked.value){
                                    Icon(
                                        modifier = Modifier.size(12.dp),
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = "",
                                        tint = info_sky
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    ) {
                        FullRoundedButton(
                            buttonText = "Refresh GPS",
                            backgroundColor = info_sky
                        ) {
//                            navController.navigate(
//                                Screen.ProviderOrder.route+"?$ORDER_CATEGORY_KEY=${OrderCategory.ProviderIsNear.label}"
//                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        FullRoundedButton(
                            buttonText = "Arrived no GPS",
                            backgroundColor = orange1
                        ) {
                            //TODO
                        }
                    }
                }
            }
        }
    }
}