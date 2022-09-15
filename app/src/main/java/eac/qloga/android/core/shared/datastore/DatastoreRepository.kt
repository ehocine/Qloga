package eac.qloga.android.core.shared.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {
    suspend fun toggleNotShowAgainCustomer()
    suspend fun toggleNotShowAgainProvider()
    suspend fun getNotShowAgainProvider(): Flow<Boolean>
    suspend fun getNotShowAgainCustomer(): Flow<Boolean>

    companion object{
        val NOT_SHOW_AGAIN_CUSTOMER_KEY = booleanPreferencesKey("not_show_again_customer_key")
        val NOT_SHOW_AGAIN_PROVIDER_KEY = booleanPreferencesKey("not_show_again_provider_key")
    }
}