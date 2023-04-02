package assignment.hyparz.dataRepository.backend

import retrofit2.Call

interface IBackendAPICallback<T> {
    fun onBackendAPICallback(response: T, call: Call<*>?, responseCode: Int)
}