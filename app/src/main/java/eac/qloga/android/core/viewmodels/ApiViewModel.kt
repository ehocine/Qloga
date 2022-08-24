package eac.qloga.android.core.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.services.OktaManager
import eac.qloga.android.core.util.LoadingState
import eac.qloga.android.data.api.APIHelper
import eac.qloga.android.data.model.ResponseEnrollsModel
import eac.qloga.android.di.retrofit.AppInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    application: Application,
    private val apiHelper: APIHelper,
    private val oktaManager: OktaManager,
    private val interceptor: AppInterceptor

) : AndroidViewModel(application) {

    var getEnrollsLoadingState = MutableStateFlow(LoadingState.IDLE)

    val responseEnrollsModel: MutableState<ResponseEnrollsModel> = mutableStateOf(
        ResponseEnrollsModel()
    )

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
                Log.d("error", e.toString())
            }
        }
    }

}