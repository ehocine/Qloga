package eac.qloga.android.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eac.qloga.android.data.ApiInterceptor
import eac.qloga.android.data.landing.LandingApi
import eac.qloga.android.data.p4p.P4pApi
import eac.qloga.android.data.p4p.customer.P4pCustomerApi
import eac.qloga.android.data.p4p.provider.P4pProviderApi
import eac.qloga.android.data.qbe.*
import eac.qloga.android.data.qmp.ChatApi
import eac.qloga.android.data.qmp.MsgApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.qloga.com/"

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHTTPClient(appInterceptor: ApiInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .apply { addInterceptor(appInterceptor) }
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideObjectMapper(): ObjectMapper {
        return jacksonObjectMapper().registerModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        client: OkHttpClient,
        objectMapper: ObjectMapper
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }

    /// API providers

    /// Landing
    @Singleton
    @Provides
    fun provideLandingApi(retrofit: Retrofit): LandingApi {
        return retrofit.create(LandingApi::class.java)
    }


    /// P4P
    @Singleton
    @Provides
    fun provideP4pApi(retrofit: Retrofit): P4pApi {
        return retrofit.create(P4pApi::class.java)
    }

    @Singleton
    @Provides
    fun provideP4pProviderApi(retrofit: Retrofit): P4pProviderApi {
        return retrofit.create(P4pProviderApi::class.java)
    }

    @Singleton
    @Provides
    fun provideP4pCustomerApi(retrofit: Retrofit): P4pCustomerApi {
        return retrofit.create(P4pCustomerApi::class.java)
    }

    /// QBE
    @Singleton
    @Provides
    fun provideAlbumsApi(retrofit: Retrofit): AlbumsApi {
        return retrofit.create(AlbumsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideEventsApi(retrofit: Retrofit): EventsApi {
        return retrofit.create(EventsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFamiliesApi(retrofit: Retrofit): FamiliesApi {
        return retrofit.create(FamiliesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMailsApi(retrofit: Retrofit): MailsApi {
        return retrofit.create(MailsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMediaApi(retrofit: Retrofit): MediaApi {
        return retrofit.create(MediaApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOrgsApi(retrofit: Retrofit): OrgsApi {
        return retrofit.create(OrgsApi::class.java)
    }

    @Singleton
    @Provides
    fun providePlatformApi(retrofit: Retrofit): PlatformApi {
        return retrofit.create(PlatformApi::class.java)
    }

    /// QMP
    @Singleton
    @Provides
    fun provideChatApi(retrofit: Retrofit): ChatApi {
        return retrofit.create(ChatApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMsgApi(retrofit: Retrofit): MsgApi {
        return retrofit.create(MsgApi::class.java)
    }

}