package eac.qloga.android.core.shared.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.services.OktaManager
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.data.p4p.P4pRepository
import eac.qloga.android.data.shared.models.ResponseEnrollsModel
import eac.qloga.android.data.ApiInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    application: Application,
    private val apiHelper: P4pRepository,
    private val oktaManager: OktaManager,
    private val interceptor: ApiInterceptor

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