package eac.qloga.android.features.p4p.shared.scenes.account

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.PlatformRepository
import eac.qloga.android.features.p4p.shared.utils.AccountType
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    application: Application,
    private val p4pProviderRepository: P4pProviderRepository,
    private val p4pCustomerRepository: P4pCustomerRepository,
): AndroidViewModel(application){

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    companion object{
        const val TAG = "AccountViewModel"
        var selectedAccountType by mutableStateOf<AccountType?>(null)
    }

    fun onSwitchAccountType(accType: AccountType){
        selectedAccountType = accType
    }
}