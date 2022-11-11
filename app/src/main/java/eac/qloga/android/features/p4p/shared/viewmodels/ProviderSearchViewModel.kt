package eac.qloga.android.features.p4p.shared.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.R
import eac.qloga.android.core.shared.utils.CustomMarkerState
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.QTAG
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.qbe.MediaRepository
import eac.qloga.android.data.shared.models.MediaSize
import eac.qloga.android.data.shared.models.Page
import eac.qloga.android.data.shared.utils.NetworkResult
import eac.qloga.android.features.p4p.shared.scenes.providerSearch.ProviderSearchEvent
import eac.qloga.android.features.p4p.shared.scenes.providerSearch.ProvidersScreenType
import eac.qloga.android.features.p4p.shared.scenes.providerSearch.ProvidersTabItems
import eac.qloga.android.features.p4p.shared.utils.*
import eac.qloga.bare.enums.VerificationType
import eac.qloga.p4p.cst.dto.PrvSearchMatchRqFirstPage
import eac.qloga.p4p.cst.dto.PrvSearchResult
import eac.qloga.p4p.lookups.dto.ServiceCategory
import eac.qloga.p4p.rq.dto.RqService
import eac.qloga.p4p.search_filters.PrvSearchFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProviderSearchViewModel @Inject constructor(
    application: Application,
    private val p4pCustomerRepository: P4pCustomerRepository,
    private val mediaRepository: MediaRepository
) : AndroidViewModel(application) {

    companion object {
        const val TAG = "${QTAG}-ProviderSearchViewModel"

        @SuppressLint("MutableCollectionMutableState")
        val servicesList: MutableState<MutableList<Long>> = mutableStateOf(mutableListOf())
        val singleService: MutableState<RqService?> = mutableStateOf(null)
        val selectedServiceId: MutableState<Long?> =
            mutableStateOf(null)
        val getFirstRqsState = MutableStateFlow(LoadingState.IDLE)
        var selectedProvidersTab: MutableState<ProvidersTabItems> =
            mutableStateOf(ProvidersTabItems.MATCH_REQUEST)
        val providersFirstSearch: MutableState<Boolean> = mutableStateOf(true)
        val singleServiceFirstSearch: MutableState<Boolean> = mutableStateOf(false)

        @SuppressLint("MutableCollectionMutableState")
        val providersList: MutableState<MutableList<PrvSearchResult>> =
            mutableStateOf(mutableListOf())
        val loading: MutableState<Boolean> = mutableStateOf(true)
    }

    val providersLastPage: MutableState<Boolean> = mutableStateOf(false)
    private val _inputFieldState = mutableStateOf(InputFieldState(hint = "Enter new address"))
    val inputFieldState: State<InputFieldState> = _inputFieldState

    private val _selectedNav = mutableStateOf<ServiceCategory?>(null)
    val selectedNav: State<ServiceCategory?> = _selectedNav

    private val _isShownMap = mutableStateOf(false)
    val isShownMap: State<Boolean> = _isShownMap

    @SuppressLint("MutableCollectionMutableState")
    private val _providersCoordinates =
        mutableStateOf<MutableList<CustomMarkerState>>(mutableListOf())
    val providersCoordinates: State<MutableList<CustomMarkerState>> = _providersCoordinates

    private val _searchFilterState = mutableStateOf(SearchFilterState())
    val searchFilterState: State<SearchFilterState> = _searchFilterState

    private val _providersScreenType = mutableStateOf(ProvidersScreenType.PROVIDERS)
    val providersScreenType: State<ProvidersScreenType> = _providersScreenType

    val getProvidersLoadingState = MutableStateFlow(LoadingState.IDLE)
    val getProvidersResponse: MutableState<Page<PrvSearchResult>> = mutableStateOf(Page())

    val proximity: MutableState<Long> = mutableStateOf(200L)
    val rating: MutableState<Int> = mutableStateOf(0)

    @SuppressLint("MutableCollectionMutableState")
    val listOfVrfs: MutableState<MutableList<VerificationType>> = mutableStateOf(mutableListOf())
    val individual: MutableState<Boolean?> = mutableStateOf(null)
    val clearanceTypeId: MutableState<Long?> = mutableStateOf(null)
    val ordersQty: MutableState<Long> = mutableStateOf(0)
    val repeatedCustomerRate: MutableState<Long> = mutableStateOf(0)

    var pageNumber: MutableState<Long> = mutableStateOf(1)

    val getFirstRqPageResponse: MutableState<PrvSearchMatchRqFirstPage> =
        mutableStateOf(PrvSearchMatchRqFirstPage())


    fun getFirstRqPage() {
        viewModelScope.launch {
            try {
                loading.value = true
                getFirstRqsState.emit(LoadingState.LOADING)
                val response = p4pCustomerRepository.getRequestsFirstPage(
                    psize = 15,
                    filter = PrvSearchFilter(),
                    fields = null
                )
                if (response.isSuccessful) {
                    getFirstRqPageResponse.value = response.body()!!
                    if (getFirstRqPageResponse.value.rqServiceIds.isNotEmpty()) {
                        servicesList.value.addAll(getFirstRqPageResponse.value.rqServiceIds)
                        selectedServiceId.value = getFirstRqPageResponse.value.firstServiceId
                    }
                    if (getFirstRqPageResponse.value.firstPage.content.isNotEmpty()) {
                        getFirstRqPageResponse.value.firstPage.content?.let {
                            providersList.value.addAll(it)
                        }
                    } else {
                        providersLastPage.value = true
                    }
                    getFirstRqsState.emit(LoadingState.LOADED)
                    getProvidersLoadingState.emit(LoadingState.LOADED)
                    providersFirstSearch.value = false
                    loading.value = false
                } else {
                    getFirstRqsState.emit(LoadingState.ERROR)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                getFirstRqsState.emit(LoadingState.ERROR)
            }
        }
    }

    fun getProviders() {
        viewModelScope.launch {
            try {
                loading.value = true
                getProvidersLoadingState.emit(LoadingState.LOADING)
                val response = p4pCustomerRepository.getProviders(
                    page = pageNumber.value,
                    psize = 15,
                    filter = PrvSearchFilter().apply {
                        proximity = this@ProviderSearchViewModel.proximity.value
                        rating = this@ProviderSearchViewModel.rating.value
                        vrfTypes = this@ProviderSearchViewModel.listOfVrfs.value
                        clearanceTypeId = this@ProviderSearchViewModel.clearanceTypeId.value
                        individual = this@ProviderSearchViewModel.individual.value
                        ordersQty = this@ProviderSearchViewModel.ordersQty.value
                        repeatedCustomerRate =
                            this@ProviderSearchViewModel.repeatedCustomerRate.value
                        serviceIds = arrayOf(selectedServiceId.value)
                    },
                    fields = null
                )
                if (response.isSuccessful) {
                    getProvidersResponse.value = response.body()!!
                    if (getProvidersResponse.value.content!!.isNotEmpty()) {
                        getProvidersResponse.value.content?.let { responseItem ->
                            providersList.value.addAll(responseItem)
                        }
                    } else {
                        providersLastPage.value = true
                    }
                    getProvidersLoadingState.emit(LoadingState.LOADED)
                    loading.value = false
                } else {
                    getProvidersLoadingState.emit(LoadingState.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                getProvidersLoadingState.emit(LoadingState.ERROR)
            }
        }
    }

    @SuppressLint("MutableCollectionMutableState")
    val providersForMapList: MutableState<MutableList<PrvSearchResult>> =
        mutableStateOf(mutableListOf())

    val getProvidersForMapState = MutableStateFlow(LoadingState.IDLE)
    val svgSate = MutableStateFlow(LoadingState.IDLE)

    val svg: MutableState<BitmapDescriptor?> = mutableStateOf(null)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getProvidersForMap() {
        viewModelScope.launch {
            try {
                getProvidersForMapState.emit(LoadingState.LOADING)
                val response = p4pCustomerRepository.getProviders(
                    page = 1,
                    psize = 100,
                    filter = PrvSearchFilter().apply {
                        proximity = 200
                        rating = 0
                        ordersQty = 0
                        repeatedCustomerRate = 0
                        mapCentreLat = ApiViewModel.userProfile.value.contacts.address.lat
                        mapCentreLng = ApiViewModel.userProfile.value.contacts.address.lng
                    },
                    fields = null
                )
                if (response.isSuccessful) {
                    response.body()!!.content?.let {
                        providersForMapList.value.addAll(it)
                    }
                    providersForMapList.value.forEach {
                        if (it.prv.org.avatarId != null) {
                            getProviderAvatar(it.prv.org.avatarId)
                        }
                    }
                    getProvidersCoordinates()
                    svg.value = urlToBitmap(getApplication())
                    getProvidersForMapState.emit(LoadingState.LOADED)
                } else {
                    getProvidersForMapState.emit(LoadingState.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                getProvidersForMapState.emit(LoadingState.ERROR)
            }
        }
    }

    private val _avatarImageState = MutableStateFlow(LoadingState.IDLE)
    val avatarImageState = _avatarImageState.asStateFlow()

    @SuppressLint("MutableCollectionMutableState")
    val listOfAvatars: MutableState<MutableList<Map<Long, Bitmap>>> = mutableStateOf(
        mutableListOf()
    )

    fun getProviderAvatar(id: Long) {
        viewModelScope.launch {
            try {
                _avatarImageState.emit(LoadingState.LOADING)
                when (val response = mediaRepository.getImageDataUrl(id, MediaSize.Sz50x50.size)) {
                    is NetworkResult.Success -> {
                        val avatarBitmap = BitmapFactory.decodeStream(response.data.byteStream())
                        listOfAvatars.value.add(mapOf(id to avatarBitmap))
                        _avatarImageState.emit(LoadingState.LOADED)
                    }
                    is NetworkResult.Error -> {
                        _avatarImageState.emit(LoadingState.ERROR)
                    }
                    is NetworkResult.Exception -> {
                        _avatarImageState.emit(LoadingState.ERROR)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _avatarImageState.emit(LoadingState.ERROR)
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun onTriggerEvent(event: ProviderSearchEvent) {
        try {
            viewModelScope.launch {
                when (event) {
                    is ProviderSearchEvent.EnterText -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            text = event.text
                        )
                    }

                    is ProviderSearchEvent.ClearInput -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            text = ""
                        )
                    }

                    is ProviderSearchEvent.FocusInput -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            isFocused = event.focusState.isFocused
                        )
                    }
                    is ProviderSearchEvent.ToggleOpenMap -> {
                        _isShownMap.value = !isShownMap.value
                        if (_isShownMap.value) {
                            _providersScreenType.value = ProvidersScreenType.MAP
                        } else {
                            _providersScreenType.value = ProvidersScreenType.PROVIDERS
                        }
                    }
                    is ProviderSearchEvent.Search -> {
                        onSearch()
                    }
                    is ProviderSearchEvent.NavItemClick -> {
                        onNavClick(event.navItem)
                    }
                    is ProviderSearchEvent.OnChangeSliderFilterState -> {
                        onChangeSliderFilter(event.type, event.value)
                    }
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

    fun onSelectProvidersTab(item: ProvidersTabItems) {
        selectedProvidersTab.value = item
    }

    private fun onSearch() {
        _inputFieldState.value = inputFieldState.value.copy(text = "")
    }

    fun onNavClick(navItem: ServiceCategory?) {
        _selectedNav.value = navItem
    }

    private fun onChangeSliderFilter(type: FilterTypes, value: Int) {
        when (type) {
            is FilterTypes.Distance -> {
                _searchFilterState.value = searchFilterState.value.copy(
                    distance = Distance(value = value)
                )
            }

            is FilterTypes.ReturnRate -> {
                _searchFilterState.value = searchFilterState.value.copy(
                    returnRate = ReturnRate(value = value)
                )
            }

            is FilterTypes.MinStartRating -> {
                _searchFilterState.value = searchFilterState.value.copy(
                    minStartRating = MinStartRating(value = value)
                )
            }

            is FilterTypes.OrdersDelivered -> {
                _searchFilterState.value = searchFilterState.value.copy(
                    ordersDelivered = OrdersDelivered(value = value)
                )
            }
            else -> {}
        }
    }

    fun onSelectProvidersVerification(index: Int) {
        val selectedIndexes = ArrayList(_searchFilterState.value.providersVerifications)
        val verification = ProvidersVerificationOptions.listValue[index].verificationType
        if (index in _searchFilterState.value.providersVerifications) {
            selectedIndexes.remove(index)
            listOfVrfs.value.remove(verification)
        } else {
            selectedIndexes.add(index)
            listOfVrfs.value.add(verification)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            providersVerifications = selectedIndexes
        )
    }

    fun onSelectProvidersAdminVerification(index: Int) {
        val selectedIndexes = ArrayList(_searchFilterState.value.providersAdminVerifications)
        val verification = ProvidersAdminVerificationsOptions.listValue[index].verificationType
        if (index in _searchFilterState.value.providersAdminVerifications) {
            selectedIndexes.remove(index)
            listOfVrfs.value.remove(verification)
        } else {
            selectedIndexes.add(index)
            listOfVrfs.value.add(verification)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            providersAdminVerifications = selectedIndexes
        )
    }

    fun onSelectClearanceCertificates(index: Int) {
//        val selectedIndexes = ArrayList(_searchFilterState.value.clearanceCertifications)
        if (ClearanceCertificationsOptions.listValue[index].label == "None") {
            listOfVrfs.value.remove(VerificationType.CLEARANCE)
            clearanceTypeId.value = null
            return
        }
//        if (index in _searchFilterState.value.clearanceCertifications) {
//            selectedIndexes.remove(index)
//        } else {
//            selectedIndexes.add(index)
//        }
        clearanceTypeId.value =
            ClearanceCertificationsOptions.listValue[index].clearanceTypeId.toLong()
        listOfVrfs.value.add(VerificationType.CLEARANCE)
//        _searchFilterState.value = _searchFilterState.value.copy(
//            clearanceCertifications = selectedIndexes
//        )
    }


    private fun getProvidersCoordinates() {
        _providersCoordinates.value = mutableListOf()
        providersForMapList.value.forEach { provider ->
            val listOfCatIds = mutableListOf<Long>()
            provider.prv.services.forEach { providerService ->
                ApiViewModel.categories.value.forEach { category ->
                    category.services.forEach { qservice ->
                        if (qservice.id == providerService.qServiceId) {
                            if (!listOfCatIds.contains(category.id)) {
                                listOfCatIds.add(category.id)
                            }
                        }
                    }
                }
            }
            _providersCoordinates.value.add(
                CustomMarkerState(
                    latitude = provider.prv.contacts.address.lat,
                    longitude = provider.prv.contacts.address.lng,
                    address = provider.prv.contacts.address.shortAddress,
                    name = provider.prv.org.name,
                    proximity = provider.distance,
                    languages = provider.prv.org.langs,
                    rating = if (provider.prv.rating != null) provider.prv.rating else 0,
                    calloutCharge = provider.prv.calloutCharge,
                    freeCancellation = provider.prv.cancelHrs,
                    categories = listOfCatIds,
                    imageUrl =
                    when (_avatarImageState.value) {
                        LoadingState.LOADED -> {
                            if (provider.prv.org.avatarId != null) {
                                listOfAvatars.value.find { map ->
                                    map.containsKey(provider.prv.org.avatarId)
                                }?.get(provider.prv.org.avatarId)!!
                            } else {
                                R.drawable.pvr_profile_ava
                            }
                        }
                        else -> R.drawable.pvr_profile_ava
                    },
//                    ApiViewModel.categories.value.firstOrNull() { it.id == listOfCatIds[0] }!!.avatarUrl ?:
                    markerIconUrl = "https://pub.qloga.com/p4p/cats/cleaning.svg"
                )
            )

        }
    }

    private fun urlToBitmap(
        context: Context
    ): BitmapDescriptor? {
        var bitmap: Bitmap? = null
        var drawable: Drawable?

        // retrieve the background
        val background = ContextCompat.getDrawable(context, R.drawable.ic_tap_marker) ?: return null
        background.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)


        val loadBitmap = viewModelScope.launch(Dispatchers.IO) {
            svgSate.emit(LoadingState.LOADING)
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data("https://pub.qloga.com/p4p/cats/cleaning.svg")
                .decoderFactory(SvgDecoder.Factory())
                .build()
            val result = loader.execute(request)
            if (result is SuccessResult) {
                Log.d("Tag", "Image success")

                drawable = result.drawable
                drawable!!.setBounds(
                    40,
                    20,
                    drawable!!.intrinsicWidth + 40,
                    drawable!!.intrinsicHeight + 20
                )
                bitmap = drawable!!.toBitmap(
                    background.intrinsicWidth,
                    background.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )

                val canvas = android.graphics.Canvas(bitmap!!)
                background.draw(canvas)
                drawable!!.draw(canvas)

            } else if (result is ErrorResult) {
                Log.d("Tag", "Image Failed")
                cancel(result.throwable.localizedMessage ?: "ErrorResult", result.throwable)
            }
        }
        var bitmapDescriptor: BitmapDescriptor? = null

        loadBitmap.invokeOnCompletion { throwable ->
            bitmap?.let {
                Log.d("Tag", "Convert")
                bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(it)
                svg.value = BitmapDescriptorFactory.fromBitmap(it)
                Log.d("Tag", "Convert $bitmapDescriptor")
                Log.d("Tag", "Convert 3 ${svg.value}")
                viewModelScope.launch { svgSate.emit(LoadingState.LOADED) }

            } ?: throwable?.let {
                Log.d("Tag", "Not Convert")
                bitmapDescriptor = null
            }
        }
        return bitmapDescriptor
    }
}

data class Providers(
    val name: String,
    val others: String
)
