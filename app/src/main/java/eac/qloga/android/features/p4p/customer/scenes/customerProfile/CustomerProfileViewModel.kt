package eac.qloga.android.features.p4p.customer.scenes.customerProfile

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.MediaRepository
import eac.qloga.android.data.qbe.PlatformRepository
import eac.qloga.android.data.shared.models.MediaSize
import eac.qloga.android.data.shared.utils.NetworkResult
import eac.qloga.android.features.p4p.provider.scenes.providerProfile.ProviderProfileViewModel
import eac.qloga.bare.dto.person.Person
import eac.qloga.p4p.cst.dto.CstPublicProfile
import eac.qloga.p4p.cst.dto.Customer
import eac.qloga.p4p.order.dto.OrderReview
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CustomerProfileViewModel @Inject constructor(
    application: Application,
    private val p4pProviderRepository: P4pProviderRepository,
    private val mediaRepository: MediaRepository,
    private val p4pCustomerRepository: P4pCustomerRepository,
    private val platformRepository: PlatformRepository,
): AndroidViewModel(application) {

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    companion object{
        const val TAG = "FavCstViewModel"
        val customerId = mutableStateOf<Long?>(null)
        val providerId = mutableStateOf<Long?>(null)
        val customerProfile = MutableStateFlow<CstPublicProfile?>(null)
        val showHeart = mutableStateOf(true)
    }

    private val _profileImage = mutableStateOf<Bitmap?>(null)
    val profileImage: State<Bitmap?> = _profileImage

    private val _profileImageState = MutableStateFlow(LoadingState.IDLE)
    val profileImageState = _profileImageState.asStateFlow()

    private val _isFavourite = mutableStateOf(false)
    val isFavourite: State<Boolean> = _isFavourite

    private val _reviews = mutableStateOf<List<OrderReview>>(emptyList())
    val reviews: State<List<OrderReview>> = _reviews

    private val _reviewsState = MutableStateFlow(LoadingState.IDLE)
    val reviewsState = _reviewsState.asStateFlow()

    var customer by mutableStateOf(Customer())
        private  set
    
    var userProfile by mutableStateOf(Person())
        private  set

    private val _customerState = MutableStateFlow(LoadingState.IDLE)
    val customerState = _customerState.asStateFlow()
    
    private val _userProfileState = MutableStateFlow(LoadingState.IDLE)
    val userProfileState = _userProfileState.asStateFlow()

    init {
        preCallsLoad()
    }

    fun preCallsLoad(){
        getUserProfile()
        getReviews()
        getProfileImage()
        getFavouriteCustomer()
    }
    
    private fun getUserProfile(){
        viewModelScope.launch {
            if(customerProfile.value == null) _userProfileState.emit(LoadingState.LOADING)
            when(val response = platformRepository.getUserProfile()){
                is NetworkResult.Success -> {
                    userProfile = response.data
                    getCustomer()
                    _userProfileState.emit(LoadingState.LOADED)
                }
                is NetworkResult.Error -> {
                    Log.e(TAG, "getUserProfile: code = ${response.code}, error = ${response.message}")
                    _userProfileState.emit(LoadingState.ERROR)
                }
                is NetworkResult.Exception -> {
                    response.e.printStackTrace()
                    _userProfileState.emit(LoadingState.ERROR)
                }
            }
        }
    }

    private fun getCustomer(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _customerState.emit(LoadingState.LOADING)
            when(val response = p4pCustomerRepository.get()){
                is NetworkResult.Success -> {
                    customer = response.data
                    val cstPublicProfile = CstPublicProfile(
                        customer.id,
                        userProfile.avatarId,
                        userProfile.fname,
                        userProfile.mname,
                        userProfile.sname,
                        userProfile.contacts,
                        customer.active,
                        customer.enrollDate,
                        customer.rating,
                        customer.qty,
                        customer.ratingsByCat,
                        userProfile.verifications
                    )
                    customerProfile.emit(cstPublicProfile)
                    getReviews()
                    _customerState.emit(LoadingState.LOADED)
                }
                is NetworkResult.Error -> {
                    Log.e(TAG, "Get Customer: code = ${response.code} , error = ${response.message}")
                    _customerState.emit(LoadingState.ERROR)
                }
                is NetworkResult.Exception -> {
                    response.e.printStackTrace()
                    _customerState.emit(LoadingState.ERROR)
                }
            }
        }
    }

    private fun getReviews(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                _reviewsState.emit(LoadingState.LOADING)
                if(providerId.value != null && customerId.value != null){
                    val response = p4pProviderRepository.getCustomerReviews(providerId.value!!, customerId.value!!)
                    _reviews.value = response
                }
                _reviewsState.emit(LoadingState.LOADED)
            }catch (e: IOException){
                _reviewsState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    private fun getProfileImage() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

            customerProfile.value?.avatarId?.let {
                _profileImageState.emit(LoadingState.LOADING)
                when(val response = mediaRepository.getImageDataUrl(it,MediaSize.Sz150x150)){
                    is NetworkResult.Success -> {
                        val bitmap = BitmapFactory.decodeStream(response.data.byteStream())
                        _profileImage.value = bitmap
                        _profileImageState.emit(LoadingState.LOADED)
                    }
                    is NetworkResult.Error -> {
                        Log.e(TAG, "getAvatar: code = ${response.code}, error = ${response.message}")
                        _profileImageState.emit(LoadingState.ERROR)
                    }
                    is NetworkResult.Exception -> {
                        response.e.printStackTrace()
                        _profileImageState.emit(LoadingState.ERROR)
                    }
                }
            }
        }
    }

    private fun getFavouriteCustomer(){
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                providerId.value?.let { id ->
                   val response = p4pProviderRepository.getFavCustomers(id)
                    _isFavourite.value = customerProfile.value?.id in response.map { it.id }
                }
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun onAddFavouriteCustomer(){
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                if(providerId.value != null && customerId.value != null){
                    val response = p4pProviderRepository.addFavCustomers(prvId = providerId.value!!,
                        customerId.value!!)
                    if(response.isSuccessful){
                        Log.i(TAG, "onAddFavouriteCustomer: Favourite Added Successfully!")
                    }
                    getFavouriteCustomer()
                }
            }catch (e: IOException){
                e.printStackTrace()
                Log.e(TAG, "onAddFavouriteCustomer: ${e.cause}")
            }
        }
    }

    private fun onDeleteFavouriteCustomer(){
        viewModelScope.launch( coroutineExceptionHandler) {
            try {
                if(customerId.value != null && providerId.value != null){
                    val response = p4pProviderRepository.deleteFavCustomers(
                        prvId = providerId.value!!,
                        cstIdForDel = customerId.value!!
                    )
                    if(response.isSuccessful){
                        Log.i(TAG, "onDeleteFavouriteCustomer: Favourite Deleted Successfully!")
                    }
                    getFavouriteCustomer()
                }
            }catch (e: IOException){
                e.printStackTrace()
                Log.e(TAG, "onDeleteFavouriteCustomer: ${e.message}")
            }
        }
    }

    fun toggleHeart(){
        if(_isFavourite.value){
            _isFavourite.value = false
            return onDeleteFavouriteCustomer()
        }
        _isFavourite.value = true
        return onAddFavouriteCustomer()
    }
}