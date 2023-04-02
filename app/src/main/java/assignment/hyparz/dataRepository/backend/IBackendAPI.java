package assignment.hyparz.dataRepository.backend;

import android.util.Log;

import assignment.hyparz.dataRepository.backend.listingApi.ListingAPI;
import retrofit2.Response;

public interface IBackendAPI {

    default ListingAPI getListingAPI() {
        return getListingAPI();
    }

    IBackendAPI setBackendAPICallback(IBackendAPICallback backendAPICallback);

    IBackendAPI setBackendAPICallback(IBackendAPICallback backendAPICallback, int responseCode);

    IBackendAPI setHeader(String key, String value);

    static BackendAPIResponse getResponse(Response<BackendAPIResponse> response) {
        Log.v("IBackendAPI", "!!!! API Response : getResponse : " + response);
        if (response != null) {
            int code = response.code();
            switch (code) {
                case 200:
                    return response.body();
                case 403:
                    return new BackendAPIResponse(ResponseStatus.failure, " SessionExpired : " + code);
                default:
                    return new BackendAPIResponse(ResponseStatus.failure, " Response Code : " + code);
            }
        }
        return null;
    }


}
