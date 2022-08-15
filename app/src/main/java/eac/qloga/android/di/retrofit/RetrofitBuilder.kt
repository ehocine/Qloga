package eac.qloga.android.di.retrofit

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eac.qloga.android.data.api.IApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilder {

//    private val token: String =
//        "eyJraWQiOiItejNSN2RHdXlPYVZaQXo1MlFLOGFqZ1ptU1NBZklPMHQ5UFNlWWIxQ0Y4IiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULjRybWppYTNXVzA5RWw1MGNZWWluZGQ1a05JN1pZaU5aZnJSamhldjVjM0Eub2FyMjV5YWdkZWxTeERIdU8zNTciLCJpc3MiOiJodHRwczovL2lkLnFsb2dhLmNvbS9vYXV0aDIvYXVzMTRsbTR6N3BJamVmOTYzNTciLCJhdWQiOiJhcGk6Ly9hcGkucWxvZ2EuY29tIiwiaWF0IjoxNjYwNDA5OTc2LCJleHAiOjE2NjA0MTM1NzYsImNpZCI6IjBvYWh5MmwzMjg4N3VCdGdsMzU3IiwidWlkIjoiMDB1Mmp3NWg4d3AwNXZ2dm0zNTciLCJzY3AiOlsib2ZmbGluZV9hY2Nlc3MiLCJwcm9maWxlIiwib3BlbmlkIiwiZW1haWwiXSwiYXV0aF90aW1lIjoxNjYwNDA5OTY5LCJzdWIiOiIxMDAxQHFsb2dhLmNvbSIsInFmaWQiOjEwMDAsInFjaWQiOiJldS13ZXN0LTI6OGQ2YmExZWUtYmRjYy00MWQ2LWJiOGItZjYzZDNiYTMyNjlhIiwiY2FsSWQiOiIxMDAxIiwicXBpZCI6MTAwMX0.k4wR1QRYcqsbDGg_VFHzSuCs6CgSbmlstyUYSQV3NS7NGJx6uGV3LcLaJPF5cWCwpZTtcz0kjBDolVLvBH28EijsqME7JgfXrlTO5g6_Fx5bagwHJYU6Rt1vcO5M5nQbSPgb1SOH5gEml0E8GWAnCs0xabzl0DIjKHnEfNm3Gv7lbOlAfDhSjTJ4ia_mal2JsRqDroIlHDqRD5A-aouq-yyKR7Won9cIYTTgmtJk8ofW-37EPGW3MSjiglCJeVkCeu70K45dzK2Qh0QiWFmZtKb0TiqL1Y0dTen581A1OUo6Ctvlz8qCXQ0brn4X0wS5prpZVx3MsjbygMRAOxEcjw"

    private const val BASE_URL = "https://api.qloga.com/"

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHTTPClient(appInterceptor: AppInterceptor): OkHttpClient {
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

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): IApiService {
        return retrofit.create(IApiService::class.java)
    }
}