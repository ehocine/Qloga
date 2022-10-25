package eac.qloga.android.features.p4p.provider.shared.viewModels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.services.OktaManager
import eac.qloga.android.core.shared.utils.*
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.ApiInterceptor
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.OrgsRepository
import eac.qloga.android.data.shared.models.conditions.ConditionsResponse
import eac.qloga.android.data.shared.models.responses.ProviderServiceResponse
import eac.qloga.android.data.shared.utils.toJsonString
import eac.qloga.android.data.shared.utils.toJsonStringWithDate
import eac.qloga.android.features.p4p.provider.scenes.servicesConditions.ServicesConditionsViewModel
import eac.qloga.android.features.p4p.provider.shared.utils.ProviderServicesEvents
import eac.qloga.android.features.p4p.provider.shared.utils.ProviderServicesEvents.*
import eac.qloga.p4p.lookups.dto.QService
import eac.qloga.p4p.lookups.dto.ServiceCategory
import eac.qloga.p4p.lookups.dto.ServiceCondition
import eac.qloga.p4p.prv.dto.ProviderService
import eac.qloga.p4p.prv.dto.ProviderServiceCondition
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.http.HTTP_EARLY_HINTS
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

const val TAG = "$QTAG-ProviderServicesViewModel"

@HiltViewModel
class ProviderServicesViewModel @Inject constructor(
    private val p4pProviderRepository: P4pProviderRepository,
    private val interceptor: ApiInterceptor,
    private val oktaManager: OktaManager
): ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    companion object{
        var providedQService by mutableStateOf<QService?>(null)
        var providedPrvService by mutableStateOf(ProviderService())
        val providerConditions =  mutableStateListOf<ServiceCondition>()
    }

    var priceInputField by mutableStateOf(InputFieldState(hint="price"))
        private set

    var timeNormInputField by mutableStateOf(InputFieldState(hint="time norm"))
        private set

    var notify by mutableStateOf(false)
        private set

    var providerServices = mutableStateListOf<ProviderService>()
        private set

    var  sharedText by mutableStateOf(InputFieldState())
        private set

    var initStatesFlag by mutableStateOf(false)
        private set

    private val _savingState = MutableStateFlow(LoadingState.IDLE)
    val savingState = _savingState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun preLoadCalls(){
        getPrvCatServiceConditions()
        getProviderServices()
        if(!initStatesFlag) setInitialInputField()
    }

    @SuppressLint("LongLogTag")
    fun onTriggerEvent(event: ProviderServicesEvents){
        viewModelScope.launch {
            try {
                when(event){
                    is EnterPrice -> {
                        sharedText.text = event.price
                        priceInputField = priceInputField.copy(text = event.price)
                    }
                    is EnterTimeNorm -> { timeNormInputField = timeNormInputField.copy(text = event.timeNorm)}
                    is FocusPrice-> { priceInputField = priceInputField.copy(isFocused = event.focusState.isFocused)}
                    is FocusTimeNorm -> {timeNormInputField = timeNormInputField.copy(isFocused = event.focusState.isFocused)}
                    is ToggleNotify -> { notify = !notify}
                }
            }catch (e: Exception){
                Log.e(TAG, "onTriggerEvents: ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getProviderServices(){
        providerServices.clear()
        providerServices.addAll(ApiViewModel.providerServices.value)
    }

    @SuppressLint("LongLogTag")
    fun onSaveProviderService(){
        viewModelScope.launch {
            interceptor.setAccessToken(oktaManager.gettingOktaToken())
            if(priceInputField.text.isEmpty()) {
                _eventFlow.emit(UiEvent.ShowToast("Price can't be empty!"))
                return@launch
            }
            _savingState.emit(LoadingState.LOADING)

            ApiViewModel.providerServices.value.remove(providedPrvService)

            providedPrvService.qServiceId = providedQService?.id
            providedPrvService.catcher = notify
            providedPrvService.unitTimed = providedPrvService.unitTimed ?: false
            providedPrvService.unitCost = PriceConverter.floatToPrice(priceInputField.text.toFloat())
            providedPrvService.timeNorm = timeNormInputField.text.toLong()
            providedPrvService.conditions = providerConditions.map { it.id }.toSet()

            ApiViewModel.providerServices.value.add(providedPrvService)

            val response = p4pProviderRepository.updateServices(
                ApiViewModel.orgs[0].id,
                ApiViewModel.providerServices.value
            )

            if(response.isSuccessful){
                _eventFlow.emit(UiEvent.ShowToast("Saved!"))
                _savingState.emit(LoadingState.LOADED)
            }else{
                Log.e(TAG, "onSaveProviderService: code = ${response.code()} error = ${response.errorBody()}")
                _eventFlow.emit(UiEvent.ShowToast("Something went wrong!"))
                _savingState.emit(LoadingState.ERROR)
            }
        }
    }

    fun updateCondition(serviceCondition: ServiceCondition){
        viewModelScope.launch {
            if(serviceCondition in providerConditions){
                //remove logic
                providerConditions.remove(serviceCondition)
                providedPrvService.conditions?.remove(serviceCondition.id)
            }else{
                //add logic
                providerConditions.add(serviceCondition)
                providedPrvService.conditions?.add(serviceCondition.id)
            }
        }
    }

    private fun getPrvCatServiceConditions(){
        val ids:List<Long> = providedPrvService.conditions?.toList() ?: emptyList()
        val prvConditions = ApiViewModel.conditions.value.filter { it.id in ids }
        providerConditions.clear()
        providerConditions.addAll(prvConditions)
    }

    private fun setInitialInputField(){
        initStatesFlag = true
        priceInputField.text =
            PriceConverter.priceToFloat(providedPrvService.unitCost?.toFloat()) ?: ""
        timeNormInputField.text = "${providedPrvService.timeNorm ?: providedQService?.timeNorm}"
        notify = providedPrvService.catcher ?: false
    }
}