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
import eac.qloga.android.data.get_address.GetAddressApi
import eac.qloga.android.data.landing.LandingApi
import eac.qloga.android.data.p4p.P4pApi
import eac.qloga.android.data.p4p.customer.P4pCustomerApi
import eac.qloga.android.data.p4p.lookups.LookupsApi
import eac.qloga.android.data.p4p.provider.P4pProviderApi
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.*
import eac.qloga.android.data.qmp.ChatApi
import eac.qloga.android.data.qmp.MsgApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val GET_ADDRESS_BASE_URL = "https://api.getAddress.io/"
    private const val QLOGA_BASE_URL = "https://api.qloga.com/"
    private const val PRIVATE_QLOGA_BASE_URL = "https://prt.qloga.com/"

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideQLOGAOkHTTPClient(appInterceptor: ApiInterceptor): OkHttpClient {
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
    @GetAddressApiService
    fun provideGetAddressRetrofitInstance(
        objectMapper: ObjectMapper
    ): Retrofit {
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .build()
                chain.proceed(newRequest)
            }).build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(GET_ADDRESS_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun provideQLOGARetrofitInstance(
        client: OkHttpClient,
        objectMapper: ObjectMapper
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(QLOGA_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }


    @Singleton
    @Provides
    @PrivateQLOGAApiService
    fun providePrivateQLOGARetrofitInstance(
        client: OkHttpClient,
        objectMapper: ObjectMapper
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(PRIVATE_QLOGA_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }

    @Singleton
    @Provides
    @PublicQLOGAApiService
    fun providePublicQLOGARetrofitInstance(
        objectMapper: ObjectMapper
    ): Retrofit {
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .build()
                chain.proceed(newRequest)
            }).build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(QLOGA_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }

    /// API providers

    //GetAddress
    @Singleton
    @Provides
    @GetAddressApiService
    fun provideGetAddressApiService(@GetAddressApiService retrofit: Retrofit): GetAddressApi {
        return retrofit.create(GetAddressApi::class.java)
    }

    /// Landing
    @Singleton
    @Provides
    @PublicQLOGAApiService
    fun provideLandingApi(@PublicQLOGAApiService retrofit: Retrofit): LandingApi {
        return retrofit.create(LandingApi::class.java)
    }


    /// P4P
    @Singleton
    @Provides
    @QLOGAApiService
    fun provideP4pApi(@QLOGAApiService retrofit: Retrofit): P4pApi {
        return retrofit.create(P4pApi::class.java)
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun provideP4pProviderApi(@QLOGAApiService retrofit: Retrofit): P4pProviderApi {
        return retrofit.create(P4pProviderApi::class.java)
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun provideP4pCustomerApi(@QLOGAApiService retrofit: Retrofit): P4pCustomerApi {
        return retrofit.create(P4pCustomerApi::class.java)
    }

    /// QBE
    @Singleton
    @Provides
    @QLOGAApiService
    fun provideAlbumsApi(@QLOGAApiService retrofit: Retrofit): AlbumsApi {
        return retrofit.create(AlbumsApi::class.java)
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun provideEventsApi(@QLOGAApiService retrofit: Retrofit): EventsApi {
        return retrofit.create(EventsApi::class.java)
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun provideFamiliesApi(@QLOGAApiService retrofit: Retrofit): FamiliesApi {
        return retrofit.create(FamiliesApi::class.java)
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun provideMailsApi(@QLOGAApiService retrofit: Retrofit): MailsApi {
        return retrofit.create(MailsApi::class.java)
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun provideMediaApi(@QLOGAApiService retrofit: Retrofit): MediaApi {
        return retrofit.create(MediaApi::class.java)
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun provideOrgsApi(@QLOGAApiService retrofit: Retrofit): OrgsApi {
        return retrofit.create(OrgsApi::class.java)
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun providePlatformApi(@QLOGAApiService retrofit: Retrofit): PlatformApi {
        return retrofit.create(PlatformApi::class.java)
    }

    /// QMP
    @Singleton
    @Provides
    @QLOGAApiService
    fun provideChatApi(@QLOGAApiService retrofit: Retrofit): ChatApi {
        return retrofit.create(ChatApi::class.java)
    }

    @Singleton
    @Provides
    @QLOGAApiService
    fun provideMsgApi(@QLOGAApiService retrofit: Retrofit): MsgApi {
        return retrofit.create(MsgApi::class.java)
    }

    // Get categories
    @Singleton
    @Provides
    @PrivateQLOGAApiService
    fun provideCategoriesApi(@PrivateQLOGAApiService retrofit: Retrofit): LookupsApi {
        return retrofit.create(LookupsApi::class.java)
    }
}

@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class GetAddressApiService

@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class QLOGAApiService


@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class PrivateQLOGAApiService

@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class PublicQLOGAApiService