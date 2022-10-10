package eac.qloga.android.features.p4p.shared.scenes.orderAddrMapView

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import eac.qloga.android.R
import eac.qloga.android.business.util.Extensions.advancedShadow
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.components.address.AddressMapView
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.grayTextColor
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.RouteTypeInfoPopup
import eac.qloga.android.features.p4p.showroom.shared.utils.RouteType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderAddrMapViewScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val containerTopPadding = 16.dp
    val mapLatLng = remember{ mutableStateOf(LatLng(54.9715, -1.6123)) }
    val selectedRouteType = remember{ mutableStateOf<RouteType?>(RouteType.WALK)}
    val showRouteInfoPopup = remember{ mutableStateOf(false) }
    val routeInfoPopupCoordinates = remember{ mutableStateOf(Offset(0f,0f)) }
    val markerTitle = "30 Cloth Market, Merchant House, Newcastle upon \nTyne, GB"

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.OrderAddrMapView.titleName,
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

                    Column(
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        Row(
                            modifier = Modifier
                                .onGloballyPositioned { layoutCoordinates ->
                                    // info popup needs the exact coordinates to position it
                                    // giving info popup info Popup coordinate right below this component
                                    routeInfoPopupCoordinates.value = Offset(
                                        x = 0f,
                                        y = layoutCoordinates.positionInRoot().y + 180
                                    )
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp, vertical = 16.dp)
                            ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            RouteTypeItem(
                                iconId = R.drawable.ic_ql_bus,
                                onClick = {
                                    if(selectedRouteType.value == RouteType.BUS){
                                        selectedRouteType.value = null
                                    }else{
                                        selectedRouteType.value = RouteType.BUS
                                    }
                                },
                                isSelected = selectedRouteType.value == RouteType.BUS
                            )

                            RouteTypeItem(
                                iconId = R.drawable.ic_ql_car,
                                onClick = {
                                    if(selectedRouteType.value == RouteType.CAR){
                                        selectedRouteType.value = null
                                    }else{
                                        selectedRouteType.value = RouteType.CAR
                                    }
                                },
                                isSelected = selectedRouteType.value == RouteType.CAR
                            )

                            RouteTypeItem(
                                iconId = R.drawable.ic_ql_cycle,
                                onClick = {
                                    if(selectedRouteType.value == RouteType.CYCLE){
                                        selectedRouteType.value = null
                                    }else{
                                        selectedRouteType.value = RouteType.CYCLE
                                    }
                                },
                                isSelected = selectedRouteType.value == RouteType.CYCLE
                            )

                            RouteTypeItem(
                                iconId = R.drawable.ic_ql_walk,
                                onClick = {
                                    if(selectedRouteType.value == RouteType.WALK){
                                        selectedRouteType.value = null
                                    }else{
                                        selectedRouteType.value = RouteType.WALK
                                    }
                                },
                                isSelected = selectedRouteType.value == RouteType.WALK
                            )

                            RouteTypeItem(
                                iconId = R.drawable.ic_ql_question_mark,
                                iconColor = orange1,
                                borderColor = orange1,
                                onClick = {
                                    showRouteInfoPopup.value = !showRouteInfoPopup.value
                                    Log.d("TAG", "OrderAddressMapViewScreen: ${showRouteInfoPopup.value}")
                                }
                            )
                        }
                        if(showRouteInfoPopup.value){
                            Popup(
                                alignment = Alignment.CenterStart,
                                properties = PopupProperties(),
                                offset = IntOffset(y = routeInfoPopupCoordinates.value.y.toInt(),x = 0),
                                onDismissRequest = {
                                    showRouteInfoPopup.value = false
                                }
                            ) {
                                RouteTypeInfoPopup()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RouteTypeItem(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    iconId: Int,
    borderColor: Color = Color.Transparent,
    isSelected: Boolean = false,
    iconColor: Color = gray30,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    val tintColor = remember{ mutableStateOf(gray30) }
    val tintColorAnimated = animateColorAsState(targetValue = tintColor.value)

    LaunchedEffect(isSelected){
        if(isSelected){
            tintColor.value = selectedColor
        }else{
            tintColor.value = iconColor
        }
    }

    Box {
        Box(
            modifier = Modifier
                .size(size)
                .advancedShadow(color = grayTextColor, alpha = .8f, 24.dp, 12.dp)
                .clip(shape = CircleShape)
                .background(MaterialTheme.colorScheme.background)
        )
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .shadow(6.dp, shape = CircleShape, ambientColor = Color.Red, spotColor = Color.Red)
                .background(backgroundColor)
                .border(2.dp, borderColor, CircleShape)
                .clickable { onClick() }
                .padding(8.dp)
            ,
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = tintColorAnimated.value
            )
        }
    }
}