package eac.qloga.android.core.services

import android.content.Context
import android.util.Log
import com.okta.authfoundation.claims.email
import com.okta.authfoundation.claims.expirationTime
import com.okta.authfoundation.claims.name
import com.okta.authfoundation.client.OidcClientResult
import com.okta.authfoundationbootstrap.CredentialBootstrap
import com.okta.webauthenticationui.WebAuthenticationClient.Companion.createWebAuthenticationClient
import eac.qloga.android.BuildConfig
import eac.qloga.android.data.shared.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

const val APPLE_IDP = "0oagorxs5veZBGY2d357"
const val GOOGLE_IDP = "0oagp0zy551uOhvs4357"

@Singleton
class OktaManager @Inject constructor() {

    private val _oktaState = MutableStateFlow<BrowserState>(BrowserState.Loading)
    val oktaState: Flow<BrowserState> = _oktaState
    var loggedIn = MutableStateFlow(false)

    companion object {
        const val TAG = "OktaManager"
    }

    suspend fun checkToken(): Boolean {
        return if (CredentialBootstrap.defaultCredential()
                .getValidAccessToken() != null
        ) {
            _oktaState.value = BrowserState.currentCredentialState()
            loggedIn.value = true
            true
        } else {
            _oktaState.value = BrowserState.currentCredentialState()
            loggedIn.value = false
            false
        }
    }

    suspend fun login(context: Context): Boolean {
        _oktaState.value = BrowserState.Loading
        val result = CredentialBootstrap.oidcClient.createWebAuthenticationClient().login(
            context = context,
            redirectUrl = BuildConfig.SIGN_IN_REDIRECT_URI,
        )
        return when (result) {
            is OidcClientResult.Error -> {
                Log.d("Error", "${result.exception}:  Failed to login.")
                _oktaState.value = BrowserState.currentCredentialState("Failed to login.")
                loggedIn.value = false
                false
            }
            is OidcClientResult.Success -> {
                val credential = CredentialBootstrap.defaultCredential()
                credential.storeToken(token = result.result)
                _oktaState.value = BrowserState.LoggedIn.create()
                loggedIn.value = true
                true
            }
        }
    }

    suspend fun appleSignIn(context: Context): Boolean {
        _oktaState.value = BrowserState.Loading
        val result = CredentialBootstrap.oidcClient.createWebAuthenticationClient().login(
            context = context,
            redirectUrl = BuildConfig.SIGN_IN_REDIRECT_URI,
            extraRequestParameters = mapOf("idp" to APPLE_IDP)
        )
        return when (result) {
            is OidcClientResult.Error -> {
                Log.d("Error", "${result.exception}:  Failed to login.")
                _oktaState.value = BrowserState.currentCredentialState("Failed to login.")
                loggedIn.value = false
                false
            }
            is OidcClientResult.Success -> {
                val credential = CredentialBootstrap.defaultCredential()
                credential.storeToken(token = result.result)
                _oktaState.value = BrowserState.LoggedIn.create()
                loggedIn.value = true
                true
            }
        }
    }

    suspend fun googleSignIn(context: Context): Boolean {
        _oktaState.value = BrowserState.Loading
        val result = CredentialBootstrap.oidcClient.createWebAuthenticationClient().login(
            context = context,
            redirectUrl = BuildConfig.SIGN_IN_REDIRECT_URI,
            extraRequestParameters = mapOf("idp" to GOOGLE_IDP)
        )
        return when (result) {
            is OidcClientResult.Error -> {
                Log.d("Error", "${result.exception}:  Failed to login.")
                _oktaState.value = BrowserState.currentCredentialState("Failed to login.")
                loggedIn.value = false
                false
            }
            is OidcClientResult.Success -> {
                val credential = CredentialBootstrap.defaultCredential()
                credential.storeToken(token = result.result)
                _oktaState.value = BrowserState.LoggedIn.create()
                loggedIn.value = true
                true
            }
        }
    }

    suspend fun logout(context: Context): Boolean {
        _oktaState.value = BrowserState.Loading
        val result =
            CredentialBootstrap.oidcClient.createWebAuthenticationClient().logoutOfBrowser(
                context = context,
                redirectUrl = BuildConfig.SIGN_OUT_REDIRECT_URI,
                CredentialBootstrap.defaultCredential().token?.idToken ?: "",
            )
        return when (result) {
            is OidcClientResult.Error -> {
                Log.d("Error", "${result.exception}:  Failed to logout.")
                _oktaState.value = BrowserState.currentCredentialState("Failed to logout.")
                loggedIn.value = true
                false
            }
            is OidcClientResult.Success -> {
                CredentialBootstrap.defaultCredential().delete()
                _oktaState.value = BrowserState.LoggedOut()
                loggedIn.value = false
                true
            }
        }
    }

    fun getUserInfo(): User {
        return User(
            userName = (_oktaState.asStateFlow().value as BrowserState.LoggedIn).name,
            email = (_oktaState.asStateFlow().value as BrowserState.LoggedIn).email,
            tokenExpiration = (_oktaState.asStateFlow().value as BrowserState.LoggedIn).expirationTime
        )
    }

    suspend fun refreshToken(): Boolean {

        if (CredentialBootstrap.defaultCredential().token != null && CredentialBootstrap.defaultCredential()
                .getAccessTokenIfValid() == null
        ) {
            Log.d(TAG, "Called the refreshToken function")
            _oktaState.value = BrowserState.Loading
            // The access_token expired, refresh the token.
            return when (val result = CredentialBootstrap.defaultCredential().refreshToken()) {
                is OidcClientResult.Error -> {
                    // An error occurred. Access the error in `result.exception`.
                    Log.d("Error", "${result.exception}")
                    _oktaState.value = BrowserState.LoggedOut()
                    loggedIn.value = false
                    Log.d("State", "${_oktaState.value}")
                    false
                }
                is OidcClientResult.Success -> {
                    // Token refreshed successfully.
                    Log.d("Success", "Token refreshed successfully.")
                    val credential = CredentialBootstrap.defaultCredential()
                    credential.storeToken(token = result.result)
                    _oktaState.value = BrowserState.LoggedIn.create()
                    loggedIn.value = true
                    true
                }
            }
        } else {
            return false
        }
    }

    fun gettingOktaToken(): String {

        return when (_oktaState.asStateFlow().value) {
            is BrowserState.LoggedIn -> {
                (_oktaState.asStateFlow().value as BrowserState.LoggedIn).accessToken
            }
            else -> {
                ""
            }
        }
    }
}


sealed class BrowserState {
    object Loading : BrowserState()
    class LoggedOut(val errorMessage: String? = null) : BrowserState()
    class LoggedIn private constructor(
        val name: String,
        val expirationTime: Int,
        val idToken: String,
        val accessToken: String,
        val email: String,
        val errorMessage: String?
    ) : BrowserState() {
        companion object {
            suspend fun create(errorMessage: String? = null): BrowserState {
                val credential = CredentialBootstrap.defaultCredential()
                val name = credential.idToken()?.name ?: ""
                val email = credential.idToken()?.email ?: ""
                val expirationTime = credential.idToken()?.expirationTime ?: 0
                val idToken = credential.token?.idToken ?: ""
                val accessToken = credential.token?.accessToken ?: ""
                return LoggedIn(name, expirationTime, idToken, accessToken, email, errorMessage)
            }
        }
    }

    companion object {
        suspend fun currentCredentialState(errorMessage: String? = null): BrowserState {
            val credential = CredentialBootstrap.defaultCredential()
            return if (credential.token == null) {
                LoggedOut(errorMessage)
            } else {
                LoggedIn.create(errorMessage)
            }
        }
    }
}