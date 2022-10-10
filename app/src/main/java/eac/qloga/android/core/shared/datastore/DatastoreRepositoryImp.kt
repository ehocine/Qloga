package eac.qloga.android.core.shared.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import eac.qloga.android.core.shared.datastore.DatastoreRepository.Companion.SHOW_AGAIN_CUSTOMER_KEY
import eac.qloga.android.core.shared.datastore.DatastoreRepository.Companion.SHOW_AGAIN_PROVIDER_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreRepositoryImp @Inject constructor(
    private val context: Context
): DatastoreRepository {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "data_store")

    override suspend fun toggleCustomerProfileInfoDialog() {
        context.dataStore.edit { preferences ->
            preferences[SHOW_AGAIN_CUSTOMER_KEY] = !(preferences[SHOW_AGAIN_CUSTOMER_KEY] ?: true )
        }
    }

    override suspend fun toggleProviderProfileInfoDialog() {
        context.dataStore.edit { preferences ->
            preferences[SHOW_AGAIN_PROVIDER_KEY] = !(preferences[SHOW_AGAIN_PROVIDER_KEY] ?: true)
        }
    }

    override suspend fun getCustomerProfileInfoDialogState(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[SHOW_AGAIN_CUSTOMER_KEY] ?: true
        }
    }

    override suspend fun getProviderProfileInfoDialogState(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[SHOW_AGAIN_PROVIDER_KEY] ?: true
        }
    }
}