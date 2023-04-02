package assignment.hyparz.dataRepository.backend.listingApi

import android.util.Log
import assignment.hyparz.dataRepository.backend.BackendAPI
import assignment.hyparz.dataRepository.backend.BackendAPIResponse
import assignment.hyparz.dataRepository.backend.model.ListingResponse
import assignment.hyparz.dataRepository.backend.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListingAPI(
    private var iListingAPI: IListingAPI,
) {
    private val TAG = "ListingAPI"

    fun fetchListingDetails(inputs: String): Response<ListingResponse> {
        Log.v(TAG, "fetchListingDetails Inputs : $inputs")
        return iListingAPI.fetchListingDetails(inputs).execute()
    }

}