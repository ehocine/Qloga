package eac.qloga.android.core.shared.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {
    suspend fun toggleCustomerProfileInfoDialog()
    suspend fun toggleProviderProfileInfoDialog()
    suspend fun getCustomerProfileInfoDialogState(): Flow<Boolean>
    suspend fun getProviderProfileInfoDialogState(): Flow<Boolean>

    companion object{
        val SHOW_AGAIN_CUSTOMER_KEY = booleanPreferencesKey("show_again_customer_key")
        val SHOW_AGAIN_PROVIDER_KEY = booleanPreferencesKey("show_again_provider_key")
    }
}