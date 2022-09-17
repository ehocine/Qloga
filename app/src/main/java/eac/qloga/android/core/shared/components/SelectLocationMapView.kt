package eac.qloga.android.core.shared.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import eac.qloga.android.core.shared.utils.CustomMarkerState

const val InitialZoom = 15f
/**
 *  Google map which show the one marker according to the
 *  location. Only one address marker, can select location
 *  by long press on map
 * */
@Composable
fun SelectLocationMapView(
    latitude: Double,
    longitude: Double,
    title: String? = null,
    description: String? = null,
    onMapClick: (LatLng) -> Unit
) {
    val addressLocation = remember(latitude, longitude){
        LatLng(latitude, longitude)
    }

    val cameraPositionState = rememberCameraPositionState(addressLocation.toString()) {
        position = CameraPosition.fromLatLngZoom(
            addressLocation,
            InitialZoom
        )
    }

    MapViewContainer(
        cameraPositionState = cameraPositionState,
        listOfMarkers = listOf(
            CustomMarkerState(latitude, longitude, title = title, description = description),
        ),
        onMapClick = { onMapClick(it) }
    )
}