package eac.qloga.android.features.p4p.shared.scenes.showLocatioMapView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.components.address.AddressMapView
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.viewmodels.AddressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowLocationMapViewScreen(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel(),
    parentRoute: String? = null
) {
    val regues = LatLng(54.9715, -1.6123)
    val mapLatLng = viewModel.selectedMapLatLng.value
    val markerTitle = viewModel.addressState.value.postCode + " " +
            viewModel.addressState.value.street + "," +
            viewModel.addressState.value.town + "-" +
            viewModel.addressState.value.building

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.ShowLocationMapView.titleName,
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
            Spacer(modifier = Modifier.height(titleBarHeight))

            AddressMapView(
                latitude = mapLatLng?.latitude ?: regues.latitude,
                longitude = mapLatLng?.longitude ?: regues.longitude,
                title = markerTitle,
                onClickMap = {
//                    viewModel.onTriggerEvent(AddressEvent.ClickMap(it))
                }
            )
        }
    }
}