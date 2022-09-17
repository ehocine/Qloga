package eac.qloga.android.features.p4p.shared.scenes.selectLocationMap

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
import eac.qloga.android.core.shared.components.DoneButton
import eac.qloga.android.core.shared.components.SelectLocationMapView
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.EnrollmentEvent
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLocationScreen(
    navController: NavController,
    viewModel: EnrollmentViewModel = hiltViewModel()
) {
    val regues = LatLng(54.9715, -1.6123)
    val userLocation = viewModel.userLocation.value
    val markerTitle = viewModel.userAddress.value?.line1 + " " +
            viewModel.userAddress.value?.postcode + "," +
            viewModel.userAddress.value?.country +" "+
            viewModel.userAddress.value?.town

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.SelectLocationMap.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions =  {
                    DoneButton(onClick = {
                        scope.launch {
                            navController.navigateUp()
                        }
                    })
                }
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topPadding))

            SelectLocationMapView(
                latitude = userLocation?.latitude ?: regues.latitude,
                longitude = userLocation?.longitude ?: regues.longitude,
                title = markerTitle,
                onMapClick = {
                    scope.launch {
                        viewModel.parseAddressFromLatLng(latLng = it)
                        viewModel.onTriggerEvent(EnrollmentEvent.ClickMap(it))
                    }
                }
            )
        }
    }
}