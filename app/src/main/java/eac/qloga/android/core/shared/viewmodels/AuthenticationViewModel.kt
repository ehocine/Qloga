package eac.qloga.android.core.shared.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.services.BrowserState
import eac.qloga.android.core.services.OktaManager
import eac.qloga.android.core.shared.utils.QTAG
import eac.qloga.android.data.shared.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    application: Application,
    private val oktaManager: OktaManager,
) : AndroidViewModel(application) {

    val oktaState: Flow<BrowserState> = oktaManager.oktaState
    var signedInUser: MutableState<User> = mutableStateOf(User())

    companion object {
        const val TAG = "${QTAG}-AuthenticationViewModel"
    }

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

    @SuppressLint("LongLogTag")
    fun appleLogin(context: Context) {
        viewModelScope.launch {
            if (oktaManager.appleSignIn(context)) {
                signedInUser.value = oktaManager.getUserInfo()
                Log.d(TAG, "User: ${signedInUser.value}")
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun googleLogin(context: Context) {
        viewModelScope.launch {
            if (oktaManager.googleSignIn(context)) {
                signedInUser.value = oktaManager.getUserInfo()
                Log.d(TAG, "User: ${signedInUser.value}")
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