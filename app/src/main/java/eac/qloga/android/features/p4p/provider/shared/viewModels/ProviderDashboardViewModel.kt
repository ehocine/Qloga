package eac.qloga.android.features.p4p.provider.shared.viewModels

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.R
import eac.qloga.android.core.shared.datastore.DatastoreRepository
import eac.qloga.android.core.shared.utils.CustomMarkerState
import eac.qloga.android.core.shared.utils.DateConverter.zonedDateTimeToStringDate
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.TimeConverter.zonedDateTimeToStringTime
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.MediaRepository
import eac.qloga.android.data.shared.models.MediaSize
import eac.qloga.android.data.shared.models.Page
import eac.qloga.android.data.shared.utils.NetworkResult
import eac.qloga.android.features.p4p.provider.shared.utils.CustomerVerificationOptions
import eac.qloga.android.features.p4p.provider.shared.utils.ProviderBottomNavItems
import eac.qloga.bare.enums.VerificationType
import eac.qloga.p4p.prv.dto.RqSearchResult
import eac.qloga.p4p.search_filters.PrvRequestSearchFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderDashboardViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
    private val p4pProviderRepository: P4pProviderRepository,
    private val mediaRepository: MediaRepository
) : ViewModel() {

    companion object {
        private val _selectNavItem = mutableStateOf(ProviderBottomNavItems.listOfItems[0])
        val selectedNavItem: MutableState<ProviderBottomNavItems> = _selectNavItem
        var alreadyShownProfileInfoDialog by mutableStateOf(false)
    }


    private val _isShownMap = mutableStateOf(false)
    val isShownMap: State<Boolean> = _isShownMap

    @SuppressLint("MutableCollectionMutableState")
    private val _customersCoordinates =
        mutableStateOf<MutableList<CustomMarkerState>>(mutableListOf())
    val customersCoordinates: State<MutableList<CustomMarkerState>> = _customersCoordinates

    private val _selectedCustomerVerificationOption = mutableStateOf<List<Int>>(emptyList())
    val selectedCustomerVerificationOption: State<List<Int>> = _selectedCustomerVerificationOption

    private val _notShowAgainProviderInfoDialog = mutableStateOf(false)
    val notShowAgainProviderInfoDialog: State<Boolean> = _notShowAgainProviderInfoDialog

    private val _showAccountSwitchInfoDialogCount = mutableStateOf(0)
    val showAccountSwitchInfoDialogCount: State<Int> = _showAccountSwitchInfoDialogCount

    private val _selectedStatus = mutableStateOf<List<Int>>(emptyList())
    val selectedStatus: State<List<Int>> = _selectedStatus

    fun onSelectNavItem(navItems: ProviderBottomNavItems) {
        _selectNavItem.value = navItems
    }

    var showProviderInfoDialog by mutableStateOf(false)
        private set

    var showProviderInfoDialogCheck by mutableStateOf(true)
        private set

    val customersLastPage: MutableState<Boolean> = mutableStateOf(false)

    init {
        observePreferences()
    }


    val getCustomersLoadingState = MutableStateFlow(LoadingState.IDLE)
    val getCustomersResponse: MutableState<Page<RqSearchResult>> = mutableStateOf(Page())

    val proximity: MutableState<Long> = mutableStateOf(200L)
    val rating: MutableState<Int> = mutableStateOf(0)

    @SuppressLint("MutableCollectionMutableState")
    val listOfVrfs: MutableState<MutableList<VerificationType>> = mutableStateOf(mutableListOf())
    val matchServices: MutableState<Boolean> = mutableStateOf(false)
    val minOfferSum: MutableState<Long> = mutableStateOf(0L)

    var pageNumber: MutableState<Long> = mutableStateOf(0)

    @SuppressLint("MutableCollectionMutableState")
    val requestsList: MutableState<MutableList<RqSearchResult>> = mutableStateOf(mutableListOf())


    fun getCustomers() {
        viewModelScope.launch {
            try {
                getCustomersLoadingState.emit(LoadingState.LOADING)
                val response = p4pProviderRepository.getCustomers(
                    prvId = ApiViewModel.userProfile.value.id,
                    page = pageNumber.value,
                    psize = 20,
                    filter = PrvRequestSearchFilter().apply {
                        proximity = this@ProviderDashboardViewModel.proximity.value
                        rating = this@ProviderDashboardViewModel.rating.value
                        vrfTypes = this@ProviderDashboardViewModel.listOfVrfs.value
                        matchServices = this@ProviderDashboardViewModel.matchServices.value
                        minOfferSum = this@ProviderDashboardViewModel.minOfferSum.value
                    },
                    selector = null
                )
                if (response.isSuccessful) {
                    getCustomersResponse.value = response.body()!!
                    if (getCustomersResponse.value.content!!.isNotEmpty()) {
                        getCustomersResponse.value.content?.let {
                            requestsList.value.addAll(it)
                        }
                    } else {
                        customersLastPage.value = true
                    }
                    getCustomersLoadingState.emit(LoadingState.LOADED)
                } else {
                    getCustomersLoadingState.emit(LoadingState.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                getCustomersLoadingState.emit(LoadingState.ERROR)
            }
        }
    }

    val getCustomersForMapResponse: MutableState<Page<RqSearchResult>> = mutableStateOf(Page())
    private val customersForMapList: MutableState<List<RqSearchResult>> = mutableStateOf(listOf())

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCustomersForMap() {
        viewModelScope.launch {
            try {
//                getCustomersLoadingState.emit(LoadingState.LOADING)
                val response = p4pProviderRepository.getCustomers(
                    prvId = ApiViewModel.userProfile.value.id,
                    page = 1,
                    psize = 100,
                    filter = PrvRequestSearchFilter().apply {
                        proximity = 200
                        mapCentreLat = ApiViewModel.userProfile.value.contacts.address.lat
                        mapCentreLng = ApiViewModel.userProfile.value.contacts.address.lng
                    },
                    selector = null
                )
                if (response.isSuccessful) {
                    customersForMapList.value = response.body()!!.content!!
                    getCustomersCoordinates()
//                    getCustomersLoadingState.emit(LoadingState.LOADED)
                } else {
//                    getCustomersLoadingState.emit(LoadingState.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
//                getCustomersLoadingState.emit(LoadingState.ERROR)
            }
        }
    }

    private val _avatarImageState = MutableStateFlow(LoadingState.IDLE)
    val avatarImageState = _avatarImageState.asStateFlow()

    @SuppressLint("MutableCollectionMutableState")
    val listOfAvatars: MutableState<MutableList<Map<Long, Bitmap>>> = mutableStateOf(
        mutableListOf()
    )

    fun getCustomerAvatar(id: Long) {
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

    fun onAccountSwitchInfoDialogInc() {
        _showAccountSwitchInfoDialogCount.value = _showAccountSwitchInfoDialogCount.value + 1
    }
    private fun observePreferences() {
        viewModelScope.launch {
            datastoreRepository.getProviderProfileInfoDialogState().onEach {
                showProviderInfoDialog = it
            }.launchIn(viewModelScope)
        }
    }

    fun onClickDialogCheck(){
        showProviderInfoDialogCheck = !showProviderInfoDialogCheck
    }

    fun onDismissInfoDialog(){
        if(showProviderInfoDialogCheck != showProviderInfoDialog){
            onToggleShowProviderInfoDialog()
        }
    }

    private fun onToggleShowProviderInfoDialog(){
        viewModelScope.launch {
            datastoreRepository.toggleProviderProfileInfoDialog()
        }
    }

    fun onChangeCustomerVerificationOption(index: Int){
        val listCopy = ArrayList(_selectedCustomerVerificationOption.value)
        if(index in listCopy){
            listCopy.remove(index)
        }else{
            listCopy.add(index)
        }
        _selectedCustomerVerificationOption.value = listCopy
    }

    fun toggleMapShow(){
        _isShownMap.value = !_isShownMap.value
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCustomersCoordinates() {
        _customersCoordinates.value = mutableListOf()
        customersForMapList.value.forEach {
            _customersCoordinates.value.add(
                CustomMarkerState(
                    latitude = it.request.address.lat,
                    longitude = it.request.address.lng,
                    address = it.request.address.shortAddress,
                    name = it.request.cstProfile.fullName,
                    email = it.request.cstProfile.contacts.email,
                    budget = it.request.offeredSum,
                    proximity = it.distance,
                    orderDate = "${zonedDateTimeToStringDate(it.request.orderedDate)} ${
                        zonedDateTimeToStringTime(
                            it.request.orderedDate
                        )
                    }",
                    servicesList = it.request.services,
                    imageUrl =
                    if (it.request.cstProfile.avatarId != null) {
                        listOfAvatars.value.first { map ->
                            map.containsKey(it.request.cstProfile.avatarId)
                        }[it.request.cstProfile.avatarId]!!
                    } else {
                        R.drawable.ql_cst_avtr_acc
                    }
                )
            )
        }
    }

    fun onSelectStatusOption(index: Int){
        val copyList = ArrayList(_selectedStatus.value)
        if(index in copyList){
            copyList.remove(index)
        }else{
            copyList.add(index)
        }
        _selectedStatus.value = copyList
    }
}