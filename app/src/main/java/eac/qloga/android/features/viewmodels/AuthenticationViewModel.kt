package eac.qloga.android.features.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.features.BrowserState
import eac.qloga.android.features.OktaManager
import eac.qloga.android.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    application: Application,
    private val oktaManager: OktaManager,
) : AndroidViewModel(application) {

    //    private val _oktaState = MutableStateFlow<BrowserState>(BrowserState.Loading)
    val oktaState: Flow<BrowserState> = oktaManager.oktaState
    var loggedIn: MutableStateFlow<Boolean> = oktaManager.loggedIn
    var signedInUser: MutableState<User> = mutableStateOf(User())

    init {
        viewModelScope.launch {
            if (oktaManager.checkToken()) {
                signedInUser.value = oktaManager.getUserInfo()
                refreshOktaToken()
            } else {
                signedInUser.value = User()
            }
        }
    }

    private fun refreshOktaToken() {
        viewModelScope.launch {
            oktaManager.refreshToken()
        }
    }

    fun oktaLogin(context: Context) {
        viewModelScope.launch {
            if (oktaManager.login(context)) {
                signedInUser.value = oktaManager.getUserInfo()
            }
        }
    }

    fun oktaLogout(context: Context) {
        viewModelScope.launch {
            if (oktaManager.logout(context)) {
                signedInUser.value = User()
            }
        }
    }
}