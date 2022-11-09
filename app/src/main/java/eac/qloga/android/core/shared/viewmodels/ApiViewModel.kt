package eac.qloga.android.core.shared.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.services.OktaManager
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.QTAG
import eac.qloga.android.data.ApiInterceptor
import eac.qloga.android.data.p4p.P4pRepository
import eac.qloga.android.data.p4p.lookups.LookupsRepository
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.MediaRepository
import eac.qloga.android.data.qbe.OrgsRepository
import eac.qloga.android.data.qbe.PlatformRepository
import eac.qloga.android.data.shared.models.MediaSize
import eac.qloga.android.data.shared.models.ResponseEnrollsModel
import eac.qloga.android.data.shared.models.categories.CategoriesResponse
import eac.qloga.android.data.shared.models.conditions.ConditionsResponse
import eac.qloga.android.data.shared.models.q_services.QServicesResponse
import eac.qloga.android.data.shared.models.responses.ProviderServiceResponse
import eac.qloga.android.data.shared.utils.NetworkResult
import eac.qloga.bare.dto.Org
import eac.qloga.bare.dto.person.Person
import eac.qloga.p4p.prv.dto.Provider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "$QTAG-ApiViewModel"
@HiltViewModel
class ApiViewModel @Inject constructor(
    application: Application,
    private val p4pRepository: P4pRepository,
    private val oktaManager: OktaManager,
    private val interceptor: ApiInterceptor,
    private val settingsViewModel: SettingsViewModel,
    private val platformRepository: PlatformRepository,
    private val lookupsRepository: LookupsRepository,
    private val orgsRepository: OrgsRepository,
    private val p4pProviderRepository: P4pProviderRepository,
    private val mediaRepository: MediaRepository

) : AndroidViewModel(application) {

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    companion object {
        var userProfile: MutableState<Person> = mutableStateOf(Person())
        var orgs by mutableStateOf<List<Org>>(emptyList())
        var provider by mutableStateOf<Provider?>(null)
        val bitmapImages = mutableStateMapOf<Long,Bitmap>()

        @SuppressLint("MutableCollectionMutableState")
        val providerServices = mutableStateOf(ProviderServiceResponse())

        @SuppressLint("MutableCollectionMutableState")
        val categories: MutableState<CategoriesResponse> = mutableStateOf(CategoriesResponse())

        @SuppressLint("MutableCollectionMutableState")
        val conditions: MutableState<ConditionsResponse> = mutableStateOf(ConditionsResponse())

        @SuppressLint("MutableCollectionMutableState")
        val qServices: MutableState<QServicesResponse> = mutableStateOf(QServicesResponse())

    }

    var getEnrollsLoadingState = MutableStateFlow(LoadingState.IDLE)

    val responseEnrollsModel: MutableState<ResponseEnrollsModel> = mutableStateOf(
        ResponseEnrollsModel()
    )
    var settingsLoadingState = MutableStateFlow(LoadingState.IDLE)

    var userProfileLoadingState = MutableStateFlow(LoadingState.IDLE)

    var orgsLoadingState = MutableStateFlow(LoadingState.IDLE)

    var providerServicesLoadingState = MutableStateFlow(LoadingState.IDLE)

    var providerLoadingState = MutableStateFlow(LoadingState.IDLE)

    var categoriesLoadingState = MutableStateFlow(LoadingState.IDLE)

    var conditionsLoadingState = MutableStateFlow(LoadingState.IDLE)

    var updateUserProfileLoadingState = MutableStateFlow(LoadingState.IDLE)

    val qServicesState = MutableStateFlow(LoadingState.IDLE)

    val updateUserProfileResponse: MutableState<Person> = mutableStateOf(Person())

    val hasAddress: MutableState<Boolean> = mutableStateOf(false)

    fun preCallsLoad() {
        getUserProfile()
        getEnrolls()
        getSettings()
        getCategories()
        getConditions()
        getQServices()
        getOrgs()
    }

    suspend fun getAvatarBitmap(id: Long?, size: Int?): Boolean {
        if(bitmapImages[id] != null) return true
        if(id == null) return false
        return when(val response = mediaRepository.getImageDataUrl(id,size)){
            is NetworkResult.Success -> {
                bitmapImages[id] = BitmapFactory.decodeStream(response.data.byteStream())
                true
            }
            is NetworkResult.Error -> {
                Log.e(TAG, "getAvatarBitmap: code = ${response.code}, error = ${response.message}", )
                false
            }
            is NetworkResult.Exception -> {
                Log.e(TAG, "getAvatarBitmap: error = ${response.e}", )
                false
            }
        }
    }

    fun getProviderServices(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            if(orgs.isEmpty()) return@launch

            providerServicesLoadingState.emit(LoadingState.LOADING)
            when(val response = p4pProviderRepository.getServices(orgs[0].id)){
                is NetworkResult.Success -> {
                    providerServices.value = ProviderServiceResponse()
                    providerServices.value = response.data
                    providerServicesLoadingState.emit(LoadingState.LOADED)
                }
                is NetworkResult.Error -> {
                    Log.e(TAG, "getProviderServices: code = ${response.code} , error = ${response.message}")
                    providerLoadingState.emit(LoadingState.ERROR)
                }
                is NetworkResult.Exception -> {
                    response.e.printStackTrace()
                    providerLoadingState.emit(LoadingState.ERROR)
                }
            }
        }
    }

    fun getProvider(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            if(orgs.isEmpty()) return@launch
            when(val response = p4pProviderRepository.getProvider(orgs[0].id)){
                is NetworkResult.Success -> {
                    provider = Provider()
                    provider = response.data
                    providerLoadingState.emit(LoadingState.LOADED)
                }
                is NetworkResult.Error -> {
                    Log.e(TAG, "getProvider: code = ${response.code} , error = ${response.message}")
                    providerLoadingState.emit(LoadingState.ERROR)
                }
                is NetworkResult.Exception -> {
                    response.e.printStackTrace()
                    providerLoadingState.emit(LoadingState.ERROR)
                }
            }
        }
    }

    fun getEnrolls() {
        viewModelScope.launch(Dispatchers.IO) {
            getEnrollsLoadingState.emit(LoadingState.LOADING)
            try {
                val response = p4pRepository.enrolls()
                if (response.isSuccessful) {
                    responseEnrollsModel.value = response.body()!!
                    getEnrollsLoadingState.emit(LoadingState.LOADED)
                } else {
                    getEnrollsLoadingState.emit(LoadingState.ERROR)
                    Log.d("Error", "Code: ${response.code()}, message: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                getEnrollsLoadingState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    fun getSettings() {
        viewModelScope.launch {
            settingsLoadingState.emit(LoadingState.LOADING)
            if (settingsViewModel.getSettings()) {
                settingsLoadingState.emit(LoadingState.LOADED)
            } else {
                settingsLoadingState.emit(LoadingState.ERROR)
            }
        }
    }

    fun getUserProfile() {
        viewModelScope.launch {
            userProfileLoadingState.emit(LoadingState.LOADING)
            when(val response = platformRepository.getUserProfile()){
                is NetworkResult.Success -> {
                    userProfile.value = Person()
                    userProfile.value = response.data
                    userProfileLoadingState.emit(LoadingState.LOADED)
                }
                is NetworkResult.Error -> {
                    userProfileLoadingState.emit(LoadingState.ERROR)
                    Log.d("Error", "Code: ${response.code}, message: ${response.message}")
                }
                is NetworkResult.Exception -> {
                    response.e.printStackTrace()
                    userProfileLoadingState.emit(LoadingState.ERROR)
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            categoriesLoadingState.emit(LoadingState.LOADING)
            try {
                val response = lookupsRepository.getCategories()
                if (response.isSuccessful) {
                    categories.value = response.body()!!
                    categoriesLoadingState.emit(LoadingState.LOADED)
                } else {
                    categoriesLoadingState.emit(LoadingState.ERROR)
                    Log.d("Error", "Code: ${response.code()}, message: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                categoriesLoadingState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    private fun getQServices() {
        viewModelScope.launch {
            qServicesState.emit(LoadingState.LOADING)
            when(val response = lookupsRepository.getServices()){
                is NetworkResult.Success -> {
                    qServices.value = response.data
                }
                is NetworkResult.Error -> {
                    Log.e(TAG, "getQServices: code = ${response.code} , error = ${response.message}")
                }
                is NetworkResult.Exception -> {
                    response.e.printStackTrace()
                }
            }
        }
    }

    fun getOrgs() {
        viewModelScope.launch {
            orgsLoadingState.emit(LoadingState.LOADING)
            when(val response = orgsRepository.getOrgs()){
                is NetworkResult.Success -> {
                    orgs = emptyList()
                    orgs = response.data
                    getProviderServices()
                    getProvider()
                    orgsLoadingState.emit(LoadingState.LOADED)
                }
                is NetworkResult.Error -> {
                    Log.e(TAG, "getOrgs: code = ${response.code} , error = ${response.message}")
                    orgsLoadingState.emit(LoadingState.ERROR)
                }
                is NetworkResult.Exception -> {
                    response.e.printStackTrace()
                    orgsLoadingState.emit(LoadingState.ERROR)
                }
            }
        }
    }

    private fun getConditions() {
        viewModelScope.launch {
            conditionsLoadingState.emit(LoadingState.LOADING)
            try {
                val response = lookupsRepository.getConditions()
                if (response.isSuccessful) {
                    conditions.value = response.body()!!
                    conditionsLoadingState.emit(LoadingState.LOADED)
                } else {
                    conditionsLoadingState.emit(LoadingState.ERROR)
                    Log.d("Error", "Code: ${response.code()}, message: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                conditionsLoadingState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    fun onUpdateUserProfile(person: Person) {
        viewModelScope.launch {
            try {
                updateUserProfileLoadingState.emit(LoadingState.LOADING)
                val response = platformRepository.updateUser(person)
                if (response.isSuccessful) {
                    updateUserProfileResponse.value = response.body()!!
                    updateUserProfileLoadingState.emit(LoadingState.LOADED)
                } else {
                    updateUserProfileLoadingState.emit(LoadingState.ERROR)
                }

            } catch (e: Exception) {
                updateUserProfileLoadingState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }
}