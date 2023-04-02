package assignment.hyparz.dataRepository.backend

import android.content.Context
import android.util.Log
import assignment.hyparz.dataRepository.ContentType
import assignment.hyparz.dataRepository.backend.listingApi.IListingAPI
import assignment.hyparz.dataRepository.backend.listingApi.ListingAPI
import assignment.hyparz.utils.Constants
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class BackendAPI constructor(builder: Builder) : BackendAPIUtils(builder),
    Callback<BackendAPIResponse<*>>, IBackendAPI {
    private lateinit var iBackendAPICallback: IBackendAPICallback<*>
    private var builder: Builder

    init {
        this.builder = builder
        // Resetting the headers explicitly
        builder.setHeaders(null)
    }

    override fun getListingAPI(): ListingAPI {
        builder.setServerURL(Constants.serverUrl)
        return ListingAPI(getRetrofit().create(IListingAPI::class.java))
    }

    /**
     * call this when you need callback for [BackendAPI] when execution is completed
     * you can filter the responseCode on default value 100
     *
     * @param iBackendAPICallback callback interface
     * @return BackendAPI
     */
    override fun setBackendAPICallback(iBackendAPICallback: IBackendAPICallback<*>): IBackendAPI {
        this.setBackendAPICallback(iBackendAPICallback, responseCode)
        return this
    }

    /**
     * call this when you need callback for [BackendAPI] when execution is completed
     * you pass your result code in the parameter and finally filter API result on that
     *
     * @param iBackendAPICallback callback interface
     * @param responseCode        response code value
     * @return BackendAPI
     */
    override fun setBackendAPICallback(
        iBackendAPICallback: IBackendAPICallback<*>,
        responseCode: Int
    ): IBackendAPI {
        super.responseCode = responseCode
        this.iBackendAPICallback = iBackendAPICallback
        return this
    }

    private fun deliverOnFailure(call : Call<BackendAPIResponse<*>>, response: Response<*>) {
        when (val responseCode = response.code()) {
            403 -> {
                var message: String?
                try {
                    val errorResponseBody: BackendAPIResponse<*>? = getErrorResponseBody(response)
                    message = errorResponseBody?.message
                } catch (e: IOException) {
                    message = "Session Expired Error"
                }
                onFailure(call, Exception(message))
            }
            401 -> {

                // TODO Create a separate exception class to handle the unauthorised(401) response
                val message: String = "Access Unauthorized Error"
                onFailure(call, Exception(message))
            }
            404 -> {
                onFailure(call, Exception("Api Not Found Error"))
            }
            503 -> {
                onFailure(call, Exception("Internal Server Error"))
            }
            else -> {
                onFailure(
                    call,
                    Exception("$responseCode : Please Try Again")
                )
            }
        }
    }

    @Throws(IOException::class)
    private fun getErrorResponseBody(response: Response<*>): BackendAPIResponse<*>? {
        val errorResponseBody = response.errorBody()
        if (errorResponseBody != null) {
            val errorResponse = errorResponseBody.string()
            return Gson().fromJson(
                errorResponse,
                BackendAPIResponse::class.java
            )
        }
        return null
    }

    private fun deliveryResult(call: Call<BackendAPIResponse<*>>, response: Any) {
        builder.setHeaders(null)
        if (iBackendAPICallback == null) {
            Log.w("deliveryResult", "No callback initialised for BackendAPI.")
            return
        }
        iBackendAPICallback.onBackendAPICallback(response as Nothing, call, responseCode)
    }

    override fun onFailure(call: Call<BackendAPIResponse<*>>, throwable: Throwable) {
        val backendAPIResponse = BackendAPIResponse<Any>()
        backendAPIResponse.status = ResponseStatus.failure

        if (throwable is SocketTimeoutException) {
            backendAPIResponse.message = "connectionTimeout" + " " + "pleaseTryAgain"
        } else {
            backendAPIResponse.message = throwable.message
        }
        deliveryResult(call, backendAPIResponse)
    }

    override fun setHeader(key: String?, value: String?): IBackendAPI {
        var headers = builder.headers
        if (headers == null) headers = HashMap()
        headers[key] = value
        builder.setHeaders(headers)
        return this
    }

    class Builder(context: Context?) {
        val context: Context
        var readTimeOut: Long
            private set
        var connectTimeout: Long
            private set
        var serverURL: String
            private set
        private var contentType: String
        var headers: MutableMap<String?, String?>? = null
            private set

        init {
            if (context == null) throw RuntimeException("Context cannot be Null.")
            this.context = context
            serverURL = Constants.serverUrl
            readTimeOut = 60L
            connectTimeout = 60L
            contentType = ContentType.APPLICATION_JSON
        }

        fun getContentType(): String {
            return contentType
        }

        fun setContentType(contentType: String?) {
            if (contentType == null) throw RuntimeException("IContentType cannot be Null.")
            this.contentType = contentType
        }

        fun setReadTimeOut(readTimeout: Long): Builder {
            if (readTimeout < 10) throw RuntimeException("Connection Read Timeout cannot be less than 10 seconds")
            readTimeOut = readTimeout
            return this
        }

        fun setConnectTimeout(connectTimeout: Long): Builder {
            if (connectTimeout < 10) throw RuntimeException("Connection Timeout cannot be less than 10 seconds")
            this.connectTimeout = connectTimeout
            return this
        }

        fun setServerURL(serverURL: String?): Builder {
            if (serverURL == null) throw RuntimeException("URL cannot be Null.")
            this.serverURL = serverURL
            return this
        }


        fun build(): IBackendAPI {
            return BackendAPI(this)
        }

        fun setHeaders(mHeaders: MutableMap<String?, String?>?): Builder {
            headers = mHeaders
            return this
        }
    }

    override fun onResponse(
        call: Call<BackendAPIResponse<*>>,
        response: Response<BackendAPIResponse<*>>
    ) {
        if (!response.isSuccessful) {
            deliverOnFailure(call, response)
            return
        }
        val finalResponse = BackendAPIResponse(
            ResponseStatus.success,
            response.body()
        )

        deliveryResult(call, finalResponse)
    }

}
