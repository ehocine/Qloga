package eac.qloga.android.features.p4p.shared.scenes.faQuestions

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.QTAG
import eac.qloga.android.data.p4p.lookups.StaticResourcesRepository
import eac.qloga.android.data.shared.models.faq.FAQResponse
import eac.qloga.android.data.shared.models.faq.FaQuestion
import eac.qloga.android.data.shared.models.faq.FaqGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

const val TAG = "$QTAG-FAQViewModel"

@HiltViewModel
class FaqViewModel @Inject constructor(
    private val staticResourcesRepository: StaticResourcesRepository
): ViewModel() {

    companion object{
        var faQuestions by mutableStateOf<FaqGroup?>(null)
    }

    @SuppressLint("MutableCollectionMutableState")
    val faqGroups =  mutableStateOf(FAQResponse())

    private val _faqState = MutableStateFlow(LoadingState.IDLE)
    val faqState = _faqState.asStateFlow()

    init {
        preCallsLoad()
    }

    fun preCallsLoad(){
        getFaqGroup()
    }

    private fun getFaqGroup(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _faqState.emit(LoadingState.LOADING)
                val response = staticResourcesRepository.getFaQuestions()
                if(response.isSuccessful){
                    faqGroups.value = response.body()!!
                    Log.d(TAG, "getFaqGroup: ${faqGroups.value}")
                }else{
                    _faqState.emit(LoadingState.ERROR)
                }
                _faqState.emit(LoadingState.LOADED)
            }catch (e: IOException){
                e.printStackTrace()
                _faqState.emit(LoadingState.ERROR)
            }
        }
    }
}