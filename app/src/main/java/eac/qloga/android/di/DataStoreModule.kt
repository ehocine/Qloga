package eac.qloga.android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eac.qloga.android.core.shared.datastore.DatastoreRepository
import eac.qloga.android.core.shared.datastore.DatastoreRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun provideDatastoreRepository(
        @ApplicationContext app: Context
    ): DatastoreRepository = DatastoreRepositoryImp(app)
}