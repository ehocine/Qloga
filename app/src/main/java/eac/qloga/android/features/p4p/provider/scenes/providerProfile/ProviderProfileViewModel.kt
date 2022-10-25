package eac.qloga.android.features.p4p.provider.scenes.providerProfile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.services.OktaManager
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.PROVIDER_ID
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.ApiInterceptor
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.qbe.MediaRepository
import eac.qloga.android.data.shared.models.MediaSize
import eac.qloga.android.data.shared.utils.NetworkResult
import eac.qloga.android.features.p4p.shared.scenes.reviews.ReviewsViewModel
import eac.qloga.p4p.order.dto.OrderReview
import eac.qloga.p4p.prv.dto.Provider
import eac.qloga.p4p.prv.enums.PrvFields
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProviderProfileViewModel @Inject constructor(
    private val p4pCustomerRepository: P4pCustomerRepository,
    private val mediaRepository: MediaRepository
): ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    companion object{
        const val TAG = "PvrDetailsViewModel"
        var providerId by mutableStateOf<Long?>(null)
        var showHeartBtn by mutableStateOf(true)
        val provider = MutableStateFlow<Provider>(Provider())
    }
    
    private val _providerState  = MutableStateFlow(LoadingState.IDLE)
    val providerState = _providerState.asStateFlow()

    private val _avatarBitmap = mutableStateOf<Bitmap?>(null)
    val avatarBitmap: State<Bitmap?> = _avatarBitmap

    private val _avatarState  = MutableStateFlow(LoadingState.IDLE)
    val avatarState = _avatarState.asStateFlow()

    private val _favouriteProviders = mutableStateOf<Set<Provider>>(emptySet())
    val favouriteProviders: State<Set<Provider>> = _favouriteProviders

    private val _reviews = mutableStateOf<List<OrderReview>>(emptyList())
    val reviews: State<List<OrderReview>> = _reviews

    private val _reviewsState = MutableStateFlow(LoadingState.IDLE)
    val reviewsState = _reviewsState.asStateFlow()

    init {
        preCallsLoad()
    }

    fun preCallsLoad(){
        loadProvider()
        getFavouriteProviders()
        getReviews()
        provider.value.org.avatarId?.let{ getAvatar(it) }
    }

    private fun loadProvider(){
        viewModelScope.launch(coroutineExceptionHandler) {
            _providerState.emit(LoadingState.LOADING)
            providerId?.let {
                p4pCustomerRepository.get()
                val response = p4pCustomerRepository.getProviderInfo(prvId = it)
                if(response.isSuccessful){
                    provider.emit(Provider())
                    provider.emit(response.body()!!)
                    provider.value.org.avatarId?.let { getAvatar(provider.value.org.avatarId) }
                    _providerState.emit(LoadingState.LOADED)
                }else{
                    _providerState.emit(LoadingState.ERROR)
                    Log.e(TAG, "loadProvider: code = ${response.code()}")
                }
            }
            _providerState.emit(LoadingState.LOADED)
        }
    }

    private fun getReviews(){
        viewModelScope.launch( coroutineExceptionHandler) {
            try {
                _reviewsState.emit(LoadingState.LOADING)
                providerId?.let { id ->
                    val response = p4pCustomerRepository.getProviderReviews(prvId = id)
                    if(response.isSuccessful){
                        _reviews.value = emptyList()
                        _reviews.value = response.body()!!
                        _reviewsState.emit(LoadingState.LOADED)
                    }else{
                        _reviewsState.emit(LoadingState.ERROR)
                    }
                }
                _reviewsState.emit(LoadingState.LOADED)
            }catch (e: IOException){
                _reviewsState.emit(LoadingState.ERROR)
                Log.e(TAG, "getReviews: ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getFavouriteProviders(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                val response = p4pCustomerRepository.getFavPrvs(listOf(PrvFields.ID))
                if(response.isSuccessful){
                    _favouriteProviders.value = emptySet()
                    _favouriteProviders.value = response.body()!!.toSet()
                }
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun onAddFavouriteProvider(providerId: Long){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                val response = p4pCustomerRepository.addFavPrv(providerId)
                if(response.isSuccessful){
                    getFavouriteProviders()
                    Log.d(TAG, "onAddFavouriteProvider: Favourite Added Successfully!")
                }
            }catch (e: IOException){
                e.printStackTrace()
                Log.e(TAG, "onAddFavouriteProvider: ${e.message}")
            }
        }
    }

    private fun onDeleteFavouriteProvider(providerId: Long){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                val response = p4pCustomerRepository.deleteFavPrv(providerId)
                if(response.isSuccessful){
                    getFavouriteProviders()
                    Log.d(TAG, "onDeleteFavouriteProvider: Favourite Deleted Successfully!")
                }
            }catch (e: IOException){
                e.printStackTrace()
                Log.e(TAG, "onDeleteFavouriteProvider: ${e.message}")
            }
        }
    }

    fun getAvatar(id: Long){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            if(ApiViewModel.bitmapImages[id] != null){
                _avatarBitmap.value = ApiViewModel.bitmapImages[id]
                return@launch
            }

            _avatarState.emit(LoadingState.LOADING)
            when(val response = mediaRepository.getImageDataUrl(id,MediaSize.Sz150x150)){
                is NetworkResult.Success -> {
                    val bitmap = BitmapFactory.decodeStream(response.data.byteStream())
                    ApiViewModel.bitmapImages[id] = bitmap
                    _avatarBitmap.value = bitmap
                    _avatarState.emit(LoadingState.LOADED)
                }
                is NetworkResult.Error -> {
                    Log.e(TAG, "getAvatar: code = ${response.code}, error = ${response.message}")
                    _avatarState.emit(LoadingState.ERROR)
                }
                is NetworkResult.Exception -> {
                    response.e.printStackTrace()
                    _avatarState.emit(LoadingState.ERROR)
                }
            }
        }
    }

    fun onToggleFavouritePrv(){
        if(provider.value in favouriteProviders.value){
            // To improve UX, when click instantly change heart state, avoid delay reflection
            val result = ArrayList(_favouriteProviders.value)
            result.remove(provider.value)
            _favouriteProviders.value = result.toSet()
            return onDeleteFavouriteProvider(providerId!!)
        }
        val result = ArrayList(_favouriteProviders.value)
        result.add(provider.value)
        _favouriteProviders.value = result.toSet()
        onAddFavouriteProvider(providerId!!)
    }
}