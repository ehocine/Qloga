package eac.qloga.android.features.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.data.api.APIHelper
import eac.qloga.android.data.model.ResponseEnrollsModel
import eac.qloga.android.di.retrofit.AppInterceptor
import eac.qloga.android.features.LoadingState
import eac.qloga.android.features.OktaManager
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

    companion object{
        var responseEnrollsModel: MutableState<ResponseEnrollsModel> = mutableStateOf(
            ResponseEnrollsModel()
        )
    }

    fun getEnrolls() {
        viewModelScope.launch(Dispatchers.IO) {
            interceptor.setAccessToken(oktaManager.gettingOktaToken())
            getEnrollsLoadingState.emit(LoadingState.LOADING)
            try {
                responseEnrollsModel.value = apiHelper.enrolls()
                Log.d("Tag", "$responseEnrollsModel")
                getEnrollsLoadingState.emit(LoadingState.LOADED)
            } catch (e: Exception) {
                getEnrollsLoadingState.emit(LoadingState.ERROR)
                Log.e("error", e.toString())
            }
        }
    }

}