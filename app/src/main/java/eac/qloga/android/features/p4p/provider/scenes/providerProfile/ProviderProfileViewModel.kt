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
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.PROVIDER_ID
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.qbe.MediaRepository
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
    savedStateHandle: SavedStateHandle,
    private val p4pCustomerRepository: P4pCustomerRepository,
    private val mediaRepository: MediaRepository
): ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    companion object{
        const val TAG = "PvrDetailsViewModel"
        var showHeartBtn by mutableStateOf(true)
    }

    private val _providerID = mutableStateOf<Long?>(null)
    val providerID: State<Long?> = _providerID

    private val _provider = mutableStateOf(Provider())
    val provider: State<Provider> = _provider
    
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
        // get arguments direct in viewModel
        // TODO remove 1001 id later
        val paramProviderID = savedStateHandle.get<Long>(PROVIDER_ID)?: 1001
        if(paramProviderID != -1L){
            _providerID.value = paramProviderID
        }
    }

    fun preCallsLoad(){
        loadProvider()
        getFavouriteProviders()
        getReviews()
    }

    private fun loadProvider(){
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                _providerState.emit(LoadingState.LOADING)
                providerID.value?.let {
                    val response = p4pCustomerRepository.getProviderInfo(prvId = it)
                    _provider.value = response
                    Log.d(TAG, "loadProvider verifications: ${response.org.verifications}")
                    getAvatar(response.org.avatarId)
                }
                _providerState.emit(LoadingState.LOADED)
            }catch (e: IOException){
                _providerState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    private fun getReviews(){
        viewModelScope.launch( coroutineExceptionHandler) {
            try {
                _reviewsState.emit(LoadingState.LOADING)
                providerID.value?.let { id ->
                    val response = p4pCustomerRepository.getProviderReviews(prvId = id)
                    _reviews.value = response
                    Log.d(ReviewsViewModel.TAG, "getProviderReviews:$response ")
                }
                _reviewsState.emit(LoadingState.LOADED)
            }catch (e: IOException){
                _reviewsState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    private fun getFavouriteProviders(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                p4pCustomerRepository.getFavPrvs(listOf(PrvFields.ID)).apply {
                    _favouriteProviders.value = this.toSet()
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
                    Log.d(TAG, "onAddFavouriteProvider: Favourite Added Successfully!")
                }
                getFavouriteProviders()
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
                    Log.d(TAG, "onDeleteFavouriteProvider: Favourite Deleted Successfully!")
                }
                getFavouriteProviders()
            }catch (e: IOException){
                e.printStackTrace()
                Log.e(TAG, "onDeleteFavouriteProvider: ${e.message}")
            }
        }
    }

    fun getAvatar(id: Long){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                _avatarState.emit(LoadingState.LOADING)
                val response = mediaRepository.getImageDataUrl(id,null)
                val bitmap = BitmapFactory.decodeStream(response.byteStream())
                _avatarBitmap.value = bitmap
                _avatarState.emit(LoadingState.LOADED)
            }catch (e: IOException){
                _avatarState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    fun onToggleFavouritePrv(){
        if(provider.value in favouriteProviders.value){
            // To improve UX, when click instantly change heart state, avoid delay reflection
            val result = ArrayList(_favouriteProviders.value)
            result.remove(provider.value)
            _favouriteProviders.value = result.toSet()
            return onDeleteFavouriteProvider(providerID.value!!)
        }
        val result = ArrayList(_favouriteProviders.value)
        result.add(provider.value)
        _favouriteProviders.value = result.toSet()
        onAddFavouriteProvider(providerID.value!!)
    }
}