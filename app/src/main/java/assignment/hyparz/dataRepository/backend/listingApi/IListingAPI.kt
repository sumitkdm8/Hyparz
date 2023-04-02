package assignment.hyparz.dataRepository.backend.listingApi

import assignment.hyparz.dataRepository.backend.BackendAPIResponse
import assignment.hyparz.dataRepository.backend.model.ListingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IListingAPI {
    @GET("api")
    fun fetchListingDetails(@Query(value = "results") requestedCount: String): Call<ListingResponse>
}