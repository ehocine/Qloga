package eac.qloga.android

import android.app.Application
import com.okta.authfoundation.AuthFoundationDefaults
import com.okta.authfoundation.client.OidcClient
import com.okta.authfoundation.client.OidcConfiguration
import com.okta.authfoundation.client.SharedPreferencesCache
import com.okta.authfoundation.credential.CredentialDataSource.Companion.createCredentialDataSource
import com.okta.authfoundationbootstrap.CredentialBootstrap
import dagger.hilt.android.HiltAndroidApp
import okhttp3.HttpUrl.Companion.toHttpUrl

@HiltAndroidApp
class QlogaApplication : Application() {

//    @Inject
//    lateinit var oktaManager: OktaManager

    override fun onCreate() {
        super.onCreate()
//        oktaManager = OktaManager(this)
        // Initializes Auth Foundation and Credential Bootstrap classes for use in the Activity.
        AuthFoundationDefaults.cache = SharedPreferencesCache.create(this)
        val oidcConfiguration = OidcConfiguration(
            clientId = BuildConfig.CLIENT_ID,
            defaultScope = "openid email profile offline_access",
        )
        val client = OidcClient.createFromDiscoveryUrl(
            oidcConfiguration,
            BuildConfig.DISCOVERY_URL.toHttpUrl(),
        )
        CredentialBootstrap.initialize(client.createCredentialDataSource(this))
    }
}