package eac.qloga.android.core.shared.viewmodels

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
import eac.qloga.android.data.ApiInterceptor
import eac.qloga.android.data.shared.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    application: Application,
    private val oktaManager: OktaManager,
    private val interceptor: ApiInterceptor,
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
                while (true) {
                    Log.d("Refresh", "Refresh token")
                    if (refreshOktaToken()) {
                        interceptor.setAccessToken(oktaManager.gettingOktaToken()) // refresh token for the interceptor
                    }
                    delay(3600000) // delay for one hour before refreshing the token
//                    delay(1000) // Test
                }

            } else {
                signedInUser.value = User()
            }
        }
    }

    private suspend fun refreshOktaToken(): Boolean {
        return oktaManager.refreshToken()
    }

    fun oktaLogin(context: Context) {
        viewModelScope.launch {
            if (oktaManager.login(context)) {
                signedInUser.value = oktaManager.getUserInfo()
            }
        }
    }

    fun appleLogin(context: Context) {
        viewModelScope.launch {
            if (oktaManager.appleSignIn(context)) {
                signedInUser.value = oktaManager.getUserInfo()
            }
        }
    }

    fun googleLogin(context: Context) {
        viewModelScope.launch {
            if (oktaManager.googleSignIn(context)) {
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