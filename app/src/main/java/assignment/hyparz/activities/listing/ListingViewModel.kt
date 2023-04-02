package assignment.hyparz.activities.listing

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import assignment.hyparz.dataRepository.DataRepository
import assignment.hyparz.dataRepository.IDataRepository
import assignment.hyparz.dataRepository.backend.BackendAPIResponse
import assignment.hyparz.dataRepository.backend.IBackendAPICallback
import assignment.hyparz.dataRepository.backend.listingApi.ListingAPI
import assignment.hyparz.dataRepository.backend.model.ListingResponse
import assignment.hyparz.dataRepository.backend.model.Result
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call

class ListingViewModel(application: Application) : AndroidViewModel(application){
    private var listingLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    var dataRepository: IDataRepository

    init {
        dataRepository = DataRepository.getInstance(application)!!
    }

    fun getListing(numOfResultRequest: String): MutableLiveData<List<Result>> {
        val listingAPI = dataRepository.backendAPI?.listingAPI!!
        this.viewModelScope.launch(Dispatchers.IO) {
            val fetchListingDetails = listingAPI.fetchListingDetails(numOfResultRequest)
            if (fetchListingDetails.isSuccessful && fetchListingDetails.body() != null) {
                val listingResponse: ListingResponse = fetchListingDetails.body()!!
                listingLiveData.postValue(listingResponse.results)
            }
        }
        return listingLiveData
    }

}
