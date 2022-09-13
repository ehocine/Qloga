package eac.qloga.android.core.shared.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import eac.qloga.android.core.services.OktaManager
import eac.qloga.android.data.ApiInterceptor
import eac.qloga.android.data.qbe.PlatformRepository
import eac.qloga.bare.enums.SettingsScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsViewModel @Inject constructor(
    private val apiInterceptor: ApiInterceptor,
    private val oktaManager: OktaManager,
    private val platformRepository: PlatformRepository
) {
    @SuppressLint("MutableCollectionMutableState")
    val settings: MutableState<HashMap<String, String>> = mutableStateOf(hashMapOf())

    suspend fun getSettings(): Boolean {
        apiInterceptor.setAccessToken(oktaManager.gettingOktaToken())
        return try {
            val response = platformRepository.getSettings(
                scope = SettingsScope.FAMILY,
                null,
                null
            )
            if (response.isSuccessful) {
                settings.value = response.body()!!
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}