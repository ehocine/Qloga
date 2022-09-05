package eac.qloga.android.core.shared.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.services.OktaManager
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.data.ApiInterceptor
import eac.qloga.android.data.p4p.P4pRepository
import eac.qloga.android.data.p4p.lookups.LookupsRepository
import eac.qloga.android.data.qbe.PlatformRepository
import eac.qloga.android.data.shared.models.ResponseEnrollsModel
import eac.qloga.android.data.shared.models.categories.CategoriesResponse
import eac.qloga.android.data.shared.models.conditions.ConditionsResponse
import eac.qloga.bare.dto.person.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    application: Application,
    private val apiHelper: P4pRepository,
    private val oktaManager: OktaManager,
    private val interceptor: ApiInterceptor,
    private val settingsViewModel: SettingsViewModel,
    private val platformRepository: PlatformRepository,
    private val lookupsRepository: LookupsRepository

) : AndroidViewModel(application) {

    companion object {
        val userProfile: MutableState<Person> = mutableStateOf(Person())

        @SuppressLint("MutableCollectionMutableState")
        val categories: MutableState<CategoriesResponse> = mutableStateOf(CategoriesResponse())

        @SuppressLint("MutableCollectionMutableState")
        val conditions: MutableState<ConditionsResponse> = mutableStateOf(ConditionsResponse())
    }

    var getEnrollsLoadingState = MutableStateFlow(LoadingState.IDLE)

    val responseEnrollsModel: MutableState<ResponseEnrollsModel> = mutableStateOf(
        ResponseEnrollsModel()
    )
    var settingsLoadingState = MutableStateFlow(LoadingState.IDLE)

    var userProfileLoadingState = MutableStateFlow(LoadingState.IDLE)

    var categoriesLoadingState = MutableStateFlow(LoadingState.IDLE)

    var conditionsLoadingState = MutableStateFlow(LoadingState.IDLE)


    fun getEnrolls() {
        viewModelScope.launch(Dispatchers.IO) {
            interceptor.setAccessToken(oktaManager.gettingOktaToken())
            getEnrollsLoadingState.emit(LoadingState.LOADING)
            try {
                val response = apiHelper.enrolls()
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
            interceptor.setAccessToken(oktaManager.gettingOktaToken())
            userProfileLoadingState.emit(LoadingState.LOADING)
            try {
                val response = platformRepository.getUserProfile()
                if (response.isSuccessful) {
                    userProfile.value = response.body()!!
                    userProfileLoadingState.emit(LoadingState.LOADED)
                } else {
                    userProfileLoadingState.emit(LoadingState.ERROR)
                    Log.d("Error", "Code: ${response.code()}, message: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                userProfileLoadingState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            interceptor.setAccessToken(oktaManager.gettingOktaToken())
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

    fun getConditions() {
        viewModelScope.launch {
            interceptor.setAccessToken(oktaManager.gettingOktaToken())
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
}