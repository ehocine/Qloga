package eac.qloga.android.features.p4p.shared.scenes.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(): ViewModel(){

    companion object{
        const val TAG = "AccountViewModel"

        var selectedAccountType by mutableStateOf(AccountType.PROVIDER)
    }

    fun onSwitchAccountType(accType: AccountType){
        selectedAccountType = accType
    }
}