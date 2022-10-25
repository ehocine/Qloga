package eac.qloga.android.features.p4p.shared.scenes.verifications

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.bare.dto.Verification
import eac.qloga.bare.enums.VerificationHolderType
import javax.inject.Inject

@HiltViewModel
class VerificationsViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val TAG = "VerificationsViewModel"
        val verifications = mutableStateOf<List<Verification>>(emptyList())
        var accountType by mutableStateOf<AccountType?>(null)
    }

    private val _groupedVerification =
        mutableStateOf<Map<VerificationHolderType,List<Verification>>>(emptyMap())
    val groupedVerification: State<Map<VerificationHolderType,List<Verification>>> = _groupedVerification

    fun groupVerifications(){
        _groupedVerification.value = verifications.value.groupBy { verification ->
            val groupType: VerificationHolderType = if(verification.holderType == null) {
                VerificationHolderType.PERSON
            }else verification.holderType
            groupType
        }
    }
}
