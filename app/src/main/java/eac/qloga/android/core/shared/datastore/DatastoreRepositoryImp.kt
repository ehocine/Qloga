package eac.qloga.android.core.shared.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import eac.qloga.android.core.shared.datastore.DatastoreRepository.Companion.NOT_SHOW_AGAIN_CUSTOMER_KEY
import eac.qloga.android.core.shared.datastore.DatastoreRepository.Companion.NOT_SHOW_AGAIN_PROVIDER_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreRepositoryImp @Inject constructor(
    private val context: Context
): DatastoreRepository {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "data_store")

    override suspend fun toggleNotShowAgainCustomer() {
        context.dataStore.edit { preferences ->
            preferences[NOT_SHOW_AGAIN_CUSTOMER_KEY] = !(preferences[NOT_SHOW_AGAIN_CUSTOMER_KEY] ?: false )
        }
    }

    override suspend fun toggleNotShowAgainProvider() {
        context.dataStore.edit { preferences ->
            preferences[NOT_SHOW_AGAIN_PROVIDER_KEY] = !(preferences[NOT_SHOW_AGAIN_PROVIDER_KEY] ?: false)
        }
    }

    override suspend fun getNotShowAgainProvider(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[NOT_SHOW_AGAIN_PROVIDER_KEY] ?: false
        }
    }

    override suspend fun getNotShowAgainCustomer(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[NOT_SHOW_AGAIN_CUSTOMER_KEY] ?: false
        }
    }
}