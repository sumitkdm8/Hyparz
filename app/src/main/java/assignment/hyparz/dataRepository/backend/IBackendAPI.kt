package assignment.hyparz.dataRepository.backend

import android.util.Log
import assignment.hyparz.dataRepository.backend.listingApi.ListingAPI
import assignment.hyparz.dataRepository.backend.IBackendAPICallback
import assignment.hyparz.dataRepository.backend.IBackendAPI
import assignment.hyparz.dataRepository.backend.BackendAPIResponse
import retrofit2.Response

interface IBackendAPI {
    val listingAPI: ListingAPI
        get() = listingAPI

    fun setBackendAPICallback(backendAPICallback: IBackendAPICallback<*>?): IBackendAPI?
    fun setBackendAPICallback(
        backendAPICallback: IBackendAPICallback<*>?,
        responseCode: Int
    ): IBackendAPI?

    fun setHeader(key: String?, value: String?): IBackendAPI?

    companion object {
        fun getResponse(response: Response<BackendAPIResponse<*>?>?): BackendAPIResponse<*>? {
            Log.v("IBackendAPI", "!!!! API Response : getResponse : $response")
            if (response != null) {
                val code = response.code()
                return when (code) {
                    200 -> response.body()
                    403 -> BackendAPIResponse<Any?>(
                        ResponseStatus.failure,
                        " SessionExpired : $code"
                    )
                    else -> BackendAPIResponse<Any?>(
                        ResponseStatus.failure,
                        " Response Code : $code"
                    )
                }
            }
            return null
        }
    }
}