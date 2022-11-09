package eac.qloga.android.core.shared.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Size
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.utils.CustomMarkerState
import eac.qloga.android.core.shared.utils.Extensions.color
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.convertPrice
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.shared.viewmodels.ProviderSearchViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.NavItem
import eac.qloga.p4p.rq.dto.RqService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 *  Google Map View
 * */

@Composable
fun MapViewContainer(
    cameraPositionState: CameraPositionState,
    listOfMarkers: List<CustomMarkerState> = emptyList(),
    customMarkerIcon: Int? = null,
    onMapClick: (LatLng) -> Unit = {}
) {

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    val context = LocalContext.current

    val animateMarkerVisible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animateMarkerVisible.value = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            onMapClick = { onMapClick(it) },
        ) {
            if (listOfMarkers.isNotEmpty()) {
                listOfMarkers.forEach { marker ->
                    if (customMarkerIcon == null) {
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    marker.latitude,
                                    marker.longitude
                                )
                            ),
                            title = marker.title,
                            snippet = marker.description,
                        )
                    } else {
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    marker.latitude,
                                    marker.longitude
                                )
                            ),
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

@OptIn(MapsComposeExperimentalApi::class)
@SuppressLint("PotentialBehaviorOverride")
@Composable
fun CustomersMapViewContainer(
    cameraPositionState: CameraPositionState,
    listOfMarkers: List<CustomMarkerState> = emptyList(),
    customMarkerIcon: Int? = null,
    showInfo: Boolean? = null,
    onMapClick: (LatLng) -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isShown by remember { mutableStateOf(false) }
    var selectedMarker by remember {
        mutableStateOf(
            CustomMarkerState(
                cameraPositionState.position.target.latitude,
                cameraPositionState.position.target.longitude
            )
        )
    }

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

//    val mapView = rememberMapViewWithLifecycle()
//
//    lateinit var clusterManager: ClusterManager<CustomMarkerState>

    val animateMarkerVisible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animateMarkerVisible.value = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (mapMarker, userCard) = createRefs()

            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(mapMarker) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                cameraPositionState = cameraPositionState,
                uiSettings = uiSettings,
                onMapClick = {
                    onMapClick(it)
                }
            ) {
//                var clusterManager by remember {
//                    mutableStateOf<ClusterManager<CustomMarkerState>?>(
//                        null
//                    )
//                }
//                MapEffect(listOfMarkers) { map ->
//                    if (clusterManager == null) {
//                        clusterManager = ClusterManager<CustomMarkerState>(context, map)
//                    }
//                    clusterManager?.addItems(listOfMarkers)
//                }
//                LaunchedEffect(key1 = cameraPositionState.isMoving) {
//                    if (!cameraPositionState.isMoving) {
//                        clusterManager?.onCameraIdle()
//                    }
//                }
                if (listOfMarkers.isNotEmpty()) {
                    listOfMarkers.forEach { marker ->
                        if (customMarkerIcon == null) {
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        marker.latitude,
                                        marker.longitude
                                    )
                                ),
                                title = marker.name,
                                snippet = marker.description,
                                onClick = {
                                    if (showInfo == true) {
                                        isShown = true
                                        selectedMarker = marker
                                        scope.launch {
                                            cameraPositionState.animate(
                                                CameraUpdateFactory.newLatLng(
                                                    LatLng(
                                                        it.position.latitude,
                                                        it.position.longitude
                                                    )
                                                )
                                            )
                                        }
                                        return@Marker true
                                    } else {
                                        return@Marker false
                                    }
                                }
                            )
                        } else {
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        marker.latitude,
                                        marker.longitude
                                    )
                                ),
                                title = marker.name,
                                snippet = marker.description,
                                icon = bitmapDescriptorFromVector(context, customMarkerIcon),
                                onClick = {
                                    if (showInfo == true) {
                                        isShown = true
                                        selectedMarker = marker
                                        scope.launch {
                                            cameraPositionState.animate(
                                                CameraUpdateFactory.newLatLng(
                                                    LatLng(
                                                        it.position.latitude,
                                                        it.position.longitude
                                                    )
                                                )
                                            )
                                        }
                                        return@Marker true
                                    } else {
                                        return@Marker false
                                    }
                                }
                            )
                        }
                    }
                }
            }
            selectedMarker.let {
                CustomerCard(
                    modifier = Modifier
                        .height(330.dp)
                        .width(300.dp)
                        .constrainAs(userCard) {
                            bottom.linkTo(parent.bottom)
                            centerHorizontallyTo(parent)
                        },
                    isShown = isShown,
                    onClose = { isShown = false },
                    title = it.name ?: "",
                    address = it.address ?: "",
                    email = it.email ?: "",
                    imageUrl = it.imageUrl ?: R.drawable.ql_cst_avtr_acc,
                    budget = it.budget ?: 0,
                    proximity = it.proximity ?: 0.0,
                    date = it.orderDate ?: "",
                    services = it.servicesList ?: listOf()
                )
            }
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@SuppressLint("PotentialBehaviorOverride")
@Composable
fun ProvidersMapViewContainer(
    cameraPositionState: CameraPositionState,
    showInfo: Boolean? = null,
    providerSearchViewModel: ProviderSearchViewModel = hiltViewModel(),
    onMapClick: (LatLng) -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isShown by remember { mutableStateOf(false) }
    var selectedMarker by remember {
        mutableStateOf(
            CustomMarkerState(
                cameraPositionState.position.target.latitude,
                cameraPositionState.position.target.longitude
            )
        )
    }
    val getProvidersForMapState by providerSearchViewModel.getProvidersForMapState.collectAsState()
    val svgState by providerSearchViewModel.svgSate.collectAsState()
    val listOfMarkers = remember {
        providerSearchViewModel.providersCoordinates.value
    }

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

//    val mapView = rememberMapViewWithLifecycle()
//
//    lateinit var clusterManager: ClusterManager<CustomMarkerState>

    val animateMarkerVisible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animateMarkerVisible.value = true
    }

    var markIcon by remember {
        providerSearchViewModel.svg
    }
    Log.d("Tag", "Marker $markIcon")
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (mapMarker, userCard) = createRefs()
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(mapMarker) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                cameraPositionState = cameraPositionState,
                uiSettings = uiSettings,
                onMapClick = {
                    onMapClick(it)
                }
            ) {
//                var clusterManager by remember {
//                    mutableStateOf<ClusterManager<CustomMarkerState>?>(
//                        null
//                    )
//                }
//                MapEffect(listOfMarkers) { map ->
//                    if (clusterManager == null) {
//                        clusterManager = ClusterManager<CustomMarkerState>(context, map)
//                    }
//                    clusterManager?.addItems(listOfMarkers)
//                }
//                LaunchedEffect(key1 = cameraPositionState.isMoving) {
//                    if (!cameraPositionState.isMoving) {
//                        clusterManager?.onCameraIdle()
//                    }
//                }
                when (getProvidersForMapState) {
                    LoadingState.LOADED -> {
//                        when(svgState){
//                            LoadingState.LOADED -> {
//                                markIcon =  providerSearchViewModel.svg.value
//                            }
//                        }

                        if (listOfMarkers.isNotEmpty()) {
                            listOfMarkers.forEach { marker ->
                                if (marker.markerIconUrl == null) {
                                    Marker(
                                        state = MarkerState(
                                            position = LatLng(
                                                marker.latitude,
                                                marker.longitude
                                            )
                                        ),
                                        title = marker.name,
                                        snippet = marker.description,
                                        onClick = {
                                            if (showInfo == true) {
                                                isShown = true
                                                selectedMarker = marker
                                                scope.launch {
                                                    cameraPositionState.animate(
                                                        CameraUpdateFactory.newLatLng(
                                                            LatLng(
                                                                it.position.latitude,
                                                                it.position.longitude
                                                            )
                                                        )
                                                    )
                                                }
                                                return@Marker true
                                            } else {
                                                return@Marker false
                                            }
                                        }
                                    )
                                } else {
                                    Marker(
                                        state = MarkerState(
                                            position = LatLng(
                                                marker.latitude,
                                                marker.longitude
                                            )
                                        ),
                                        title = marker.name,
                                        snippet = marker.description,
                                        icon = null,
                                        onClick = {
                                            if (showInfo == true) {
                                                isShown = true
                                                selectedMarker = marker
                                                scope.launch {
                                                    cameraPositionState.animate(
                                                        CameraUpdateFactory.newLatLng(
                                                            LatLng(
                                                                it.position.latitude,
                                                                it.position.longitude
                                                            )
                                                        )
                                                    )
                                                }
                                                return@Marker true
                                            } else {
                                                return@Marker false
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            selectedMarker.let {
                ProviderCard(
                    modifier = Modifier
                        .height(350.dp)
                        .width(300.dp)
                        .constrainAs(userCard) {
                            bottom.linkTo(parent.bottom)
                            centerHorizontallyTo(parent)
                        },
                    isShown = isShown,
                    onClose = { isShown = false },
                    title = it.title ?: "",
                    address = it.address ?: "",
                    coverage = it.proximity ?: 0.0,
                    freeCancellation = it.freeCancellation ?: 0,
                    calloutCharge = it.calloutCharge ?: false,
                    languages = it.languages ?: arrayOf(),
                    rating = it.rating ?: 0,
                    imageUrl = R.drawable.pvr_profile_ava,
                    categories = it.categories ?: listOf(),
                )
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

private fun urlToBitmap(
    scope: CoroutineScope,
    imageURL: String,
    context: Context
): BitmapDescriptor? {
    var bitmap: Bitmap? = null
    val loadBitmap = scope.launch(Dispatchers.IO) {

//            val loader = ImageLoader(context)
//            val request = ImageRequest.Builder(context)
//                .data("https://pub.qloga.com/p4p/cats/cleaning.svg")
//                .allowHardware(false) // Disable hardware bitmaps.
//                .build()
//
//            val result = (loader.execute(request) as SuccessResult).drawable
//            bitmap = (result as BitmapDrawable).bitmap

        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data("https://pub.qloga.com/p4p/cats/cleaning.svg")
            .decoderFactory(SvgDecoder.Factory())
            .build()
        val result = loader.execute(request)
        if (result is SuccessResult) {
            Log.d("Tag", "Image success")
            bitmap = (result.drawable as BitmapDrawable).bitmap
        } else if (result is ErrorResult) {
            Log.d("Tag", "Image Failed")
            cancel(result.throwable.localizedMessage ?: "ErrorResult", result.throwable)
        }
    }
    var bitmapDescriptor: BitmapDescriptor? = null
    loadBitmap.invokeOnCompletion { throwable ->
        bitmap?.let {
            bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(it)
        } ?: throwable?.let {
            bitmapDescriptor = null
        }
    }
    return bitmapDescriptor
}

@Composable
fun CustomerCard(
    modifier: Modifier = Modifier,
    isShown: Boolean,
    onClose: () -> Unit,
    title: String,
    address: String,
    email: String,
    imageUrl: Any,
    budget: Long,
    proximity: Double,
    date: String,
    services: List<RqService>
) {
    val imageWidth = 100.dp
    val imageHeight = 100.dp
    val imageContainerHeight = imageHeight + 8.dp

    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.DOWN

    if (isShown) {
        Cards.ContainerBorderedCard(
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f)
                    ) {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .decoderFactory(SvgDecoder.Factory())
                                .data(imageUrl)
                                .size(Size.ORIGINAL) // Set the target size to load the image at.
                                .build()
                        )
                        Image(
                            modifier = Modifier
                                .width(imageWidth)
                                .height(imageHeight),
                            painter = painter,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopCenter
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            modifier = Modifier.height(imageContainerHeight)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(9f),
                                text = title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.W600,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Address:\n$address",
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Email:\n$email",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                        }
                    }
                    Box(Modifier.weight(5f)) {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Open Request details",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W600
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Budget: £${convertPrice(budget)}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Proximity: ${df.format(proximity)} miles",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Ordered date: $date",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Services",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W600
                            )
                            services.forEach {
                                val service = ApiViewModel.qServices.value.first { qService ->
                                    qService.id == it.qServiceId
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Canvas(modifier = Modifier
                                        .size(30.dp), onDraw = {
                                        drawCircle(
                                            color = green1,
                                            radius = 10f
                                        )
                                    })
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = service.name,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.W500
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1.5f)
                    ) {
                        Button(
                            modifier = Modifier.align(Alignment.BottomEnd)
                                .height(35.dp),
                            onClick = {

                            }) {
                            Text(
                                text = "Quote",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                }
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = { onClose() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = gray1
                    )
                }
            }
        }
    }
}

@Composable
fun ProviderCard(
    modifier: Modifier = Modifier,
    isShown: Boolean,
    onClose: () -> Unit,
    title: String,
    address: String,
    coverage: Double,
    freeCancellation: Int,
    calloutCharge: Boolean,
    languages: Array<String>,
    rating: Int,
    imageUrl: Any,
    categories: List<Long>
) {
    val imageWidth = 100.dp
    val imageHeight = 100.dp
    val imageContainerHeight = imageHeight + 8.dp

    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.DOWN

    val dfRating = DecimalFormat("#.0")
    dfRating.roundingMode = RoundingMode.DOWN
    val separator = ", "

    if (isShown) {
        Cards.ContainerBorderedCard(
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 30.dp, bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f)
                    ) {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .decoderFactory(SvgDecoder.Factory())
                                .data(imageUrl)
                                .size(Size.ORIGINAL) // Set the target size to load the image at.
                                .build()
                        )
                        Image(
                            modifier = Modifier
                                .width(imageWidth)
                                .height(imageHeight),
                            painter = painter,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopCenter
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            modifier = Modifier.height(imageContainerHeight)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(9f),
                                text = title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.W600,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Coverage: ${df.format(coverage)} miles",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Free cancelation: $freeCancellation Hrs",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Callout charge: $calloutCharge",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(Modifier.weight(5f)) {
                        Column {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Languages: " + languages.joinToString(separator),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Rating: ${dfRating.format(rating.toDouble() / 1000)}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Address: $address",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.W500
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(
                                    start = 0.dp,
                                    end = 16.dp,
                                    bottom = 8.dp
                                )
                            ) {
                                items(categories) { categoryId ->

                                    val category = ApiViewModel.categories.value.find {
                                        it.id == categoryId
                                    }
                                    if (category != null) {
                                        NavItem(
                                            iconUrl = category.avatarUrl,
                                            label = category.name,
                                            isSelected = false,
                                            strokeColor = category.catGroupColour.color,
                                            BGColor = category.catGroupBgColour.color,
                                            size = 50.dp,
                                            iconSize = 30.dp,
                                            labelFontSize = 10.sp
                                        ) {
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1.5f)
                    ) {
                        FullRoundedButton(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            buttonHeight = 35.dp,
                            buttonText = "Direct Inquiry",
                            buttonTextStyle = MaterialTheme.typography.bodySmall
                        ) {

                        }
                    }

                }
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = { onClose() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = gray1
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ProvView() {
    ProviderCard(
        modifier = Modifier
            .height(330.dp)
            .width(300.dp),
        isShown = true,
        title = "Elhadj Hocine Elhadjhhhh Hocine Elhadj Hocine",
        address = "This is my addressThis is my address This is my addressThis is my address",
        coverage = 10.0,
        freeCancellation = 0,
        calloutCharge = true,
        languages = arrayOf("en", "fr"),
        rating = 1000,
        imageUrl = R.drawable.pvr_profile_ava,
        categories = listOf(),
        onClose = {}
    )
}
