package eac.qloga.android.features.p4p.provider.shared.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import eac.qloga.android.core.shared.components.CustomersMapViewContainer
import eac.qloga.android.core.shared.utils.CustomMarkerState
import eac.qloga.android.core.shared.viewmodels.ApiViewModel

const val InitialZoom = 13f

/**
 *  Google map which show the one marker according to the
 *  location. Only one address marker
 * */
@Composable
fun CustomersMapView(
    listOfCustomers: List<CustomMarkerState>,
) {
    val addressLocations = remember(listOfCustomers) { listOfCustomers }
    val cameraPositionState = rememberCameraPositionState(addressLocations.toString()) {
        try {
            position = CameraPosition.fromLatLngZoom(
                LatLng(
                    ApiViewModel.userProfile.value.contacts.address.lat,
                    ApiViewModel.userProfile.value.contacts.address.lng
                ),
                InitialZoom
            )
        } catch (e: Exception) {
            Log.d("TAG", "ProvidersMapView: ${e.printStackTrace()}")
        }
    }

    CustomersMapViewContainer(
        cameraPositionState = cameraPositionState,
        customMarkerIcon = null,
        showInfo = true,
        listOfMarkers = listOfCustomers
    )
}