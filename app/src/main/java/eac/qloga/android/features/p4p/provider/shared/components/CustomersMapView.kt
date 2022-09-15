package eac.qloga.android.features.p4p.provider.shared.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.MapViewContainer
import eac.qloga.android.core.shared.utils.CustomMarkerState

const val InitialZoom = 15f
/**
 *  Google map which show the one marker according to the
 *  location. Only one address marker
 * */
@Composable
fun CustomersMapView(
    coordinates: List<CustomMarkerState>,
) {
    val addressLocations = remember(coordinates){ coordinates }
    val cameraPositionState = rememberCameraPositionState(addressLocations.toString()) {
        try {
            position = CameraPosition.fromLatLngZoom(
                LatLng(coordinates[0].latitude, coordinates[0].longitude),
                InitialZoom
            )
        }catch (e: Exception){
            Log.d("TAG", "ProvidersMapView: ${e.printStackTrace()}")
        }
    }

    MapViewContainer(
        cameraPositionState = cameraPositionState,
        customMarkerIcon = R.drawable.ic_tap_marker,
        listOfMarkers = coordinates
    )
}