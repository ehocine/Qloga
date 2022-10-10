package eac.qloga.android.features.p4p.shared.scenes.prvCstTC

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.p4p.core.dto.Rating
import javax.inject.Inject

@HiltViewModel
class PrvCstTCViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) {

    companion object{
        var accountType by mutableStateOf<AccountType?>(null)
    }
}