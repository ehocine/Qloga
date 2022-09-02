package eac.qloga.android.core.shared.components

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import eac.qloga.android.core.shared.utils.CustomMarkerState

/**
 *  Google Map View
 * */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MapViewContainer(
    cameraPositionState: CameraPositionState,
    listOfMarkers: List<CustomMarkerState> = emptyList(),
    customMarkerIcon: Int? = null,
    onMapClick: (LatLng) -> Unit = {}
) {

    val uiSettings = remember{
        MapUiSettings(zoomControlsEnabled = false)
    }
    val context = LocalContext.current

    val animateMarkerVisible = remember{ mutableStateOf(false)}

    LaunchedEffect(Unit){
        animateMarkerVisible.value = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            onMapClick = {onMapClick(it)},
        ) {
            if(listOfMarkers.isNotEmpty()){
                listOfMarkers.forEach { marker ->
                    if(customMarkerIcon == null){
                        Marker(
                            state = MarkerState(position = LatLng(marker.latitude, marker.longitude)),
                            title = marker.title,
                            snippet = marker.description,
                        )
                    }else{
                        Marker(
                            state = MarkerState(position = LatLng(marker.latitude, marker.longitude)),
                            title = marker.title,
                            snippet = marker.description,
                            icon = bitmapDescriptorFromVector(context, customMarkerIcon)
                        )
                    }
                }
            }
        }
    }
}

// converts the vector resource type image to bitmap
private fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int,
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}