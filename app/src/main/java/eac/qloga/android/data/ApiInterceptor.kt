package eac.qloga.android.data

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiInterceptor @Inject constructor() : Interceptor {
    private var accessToken: String? = null
    fun setAccessToken(accessToken: String?) {
        this.accessToken = accessToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()
        accessToken?.let {
            requestBuilder.addHeader("authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}
