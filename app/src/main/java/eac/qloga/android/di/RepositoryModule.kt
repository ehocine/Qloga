package eac.qloga.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import eac.qloga.android.data.p4p.customer.P4pCustomerApi
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.p4p.provider.P4pProviderApi
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.MediaApi
import eac.qloga.android.data.qbe.MediaRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideP4pProviderRepository(
        @QLOGAApiService p4pProviderApi: P4pProviderApi
    ): P4pProviderRepository {
        return P4pProviderRepository(p4pProviderApi)
    }

    @Singleton
    @Provides
    fun provideP4pCustomerRepository(
        @QLOGAApiService p4pCustomerApi: P4pCustomerApi
    ): P4pCustomerRepository {
        return P4pCustomerRepository(p4pCustomerApi)
    }

    @Singleton
    @Provides
    fun provideMediaRepository(
        @QLOGAApiService mediaApi: MediaApi
    ): MediaRepository {
        return MediaRepository(mediaApi)
    }
}