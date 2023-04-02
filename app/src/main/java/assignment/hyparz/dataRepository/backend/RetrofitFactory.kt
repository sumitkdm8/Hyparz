package assignment.hyparz.dataRepository.backend

import assignment.hyparz.utils.Constants
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitFactory(private val builder: BackendAPI.Builder) {
    private var retrofitBuilder: Retrofit.Builder? = null

    protected fun getRetrofit(): Retrofit {
        if (retrofitBuilder == null) retrofitBuilder = Retrofit.Builder()
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
        retrofitBuilder!!.baseUrl(builder.serverURL)
        return retrofitBuilder!!.build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        val connectionTimeOut: String? = connectTimeout
        val readTimeOut: String? = readTimeout
        okHttpClientBuilder
            .connectTimeout(
                if (!connectionTimeOut.isNullOrEmpty()) java.lang.Long.valueOf(
                    connectionTimeOut
                ) else builder.connectTimeout, TimeUnit.SECONDS
            )
            .readTimeout(
                if (!readTimeOut.isNullOrEmpty()) java.lang.Long.valueOf(
                    readTimeOut
                ) else builder.readTimeOut, TimeUnit.SECONDS
            )
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
            .protocols(listOf(Protocol.HTTP_1_1))

        return okHttpClientBuilder.build()
    }

    val readTimeout: String?
        get() {
            return Constants.readTimeout
        }
    val connectTimeout: String?
        get() {
            return Constants.connectionTimeout
        }

    companion object {
        private val TAG: String = "RetrofitFactory"
    }
}
