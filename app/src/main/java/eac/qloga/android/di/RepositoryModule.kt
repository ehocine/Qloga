package eac.qloga.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import eac.qloga.android.data.get_address.GetAddressApi
import eac.qloga.android.data.get_address.GetAddressRepository
import eac.qloga.android.data.landing.LandingApi
import eac.qloga.android.data.landing.LandingRepository
import eac.qloga.android.data.p4p.customer.P4pCustomerApi
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.p4p.lookups.LookupsApi
import eac.qloga.android.data.p4p.lookups.LookupsRepository
import eac.qloga.android.data.p4p.provider.P4pProviderApi
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.*
import eac.qloga.android.data.qmp.ChatApi
import eac.qloga.android.data.qmp.ChatRepository
import eac.qloga.android.data.qmp.MsgApi
import eac.qloga.android.data.qmp.MsgRepository
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

    @Singleton
    @Provides
    fun provideGetAddressRepository(
        @GetAddressApiService getAddressApiService: GetAddressApi
    ): GetAddressRepository {
        return GetAddressRepository(getAddressApiService)
    }

    @Singleton
    @Provides
    fun provideLandingRepository(
        @PublicQLOGAApiService landingApi: LandingApi
    ): LandingRepository {
        return LandingRepository(landingApi)
    }

    @Singleton
    @Provides
    fun provideLookupsApi(
        @PrivateQLOGAApiService lookupsApi: LookupsApi
    ): LookupsRepository {
        return LookupsRepository(lookupsApi)
    }

    @Singleton
    @Provides
    fun provideAlbumsRepository(
        @QLOGAApiService apiService: AlbumsApi
    ): AlbumsRepository {
        return AlbumsRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideEventsRepository(
        @QLOGAApiService apiService: EventsApi
    ): EventsRepository {
        return EventsRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideFamiliesRepository(
        @QLOGAApiService apiService: FamiliesApi
    ): FamiliesRepository {
        return FamiliesRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideMailsRepository(
        @QLOGAApiService apiService: MailsApi
    ): MailsRepository {
        return MailsRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideOrgsRepository(
        @QLOGAApiService apiService: OrgsApi
    ): OrgsRepository {
        return OrgsRepository(apiService)
    }

    @Singleton
    @Provides
    fun providePlatformRepository(
        @QLOGAApiService apiService: PlatformApi
    ): PlatformRepository {
        return PlatformRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideChatRepository(
        @QLOGAApiService apiService: ChatApi
    ): ChatRepository {
        return ChatRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideMsgRepository(
        @QLOGAApiService apiService: MsgApi
    ): MsgRepository {
        return MsgRepository(apiService)
    }
}