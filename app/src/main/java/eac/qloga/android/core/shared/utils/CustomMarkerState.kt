package eac.qloga.android.core.shared.utils

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import eac.qloga.p4p.rq.dto.RqService

data class CustomMarkerState(
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val name: String? = null,
    val email: String? = null,
    val budget: Long? = null,
    val proximity: Double? = null,
    val orderDate: String? = null,
    val servicesList: List<RqService>? = null,
    val imageUrl: Any? = null,
    val description: String? = null,
    val languages: Array<String>? = null,
    val calloutCharge: Boolean? = null,
    val rating: Int? = null,
    val freeCancellation: Int? = null,
    val categories: List<Long>? = null,
    val markerIconUrl: String? = null
) : ClusterItem {
    override fun getPosition(): LatLng =
        LatLng(latitude, longitude)


    override fun getTitle(): String =
        name ?: ""


    override fun getSnippet(): String =
        address ?: ""

}