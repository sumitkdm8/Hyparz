package assignment.hyparz.dataRepository

import android.webkit.CookieManager
import android.webkit.WebStorage
import assignment.hyparz.dataRepository.backend.IBackendAPI

interface IDataRepository {
    val backendAPI: IBackendAPI?
//    val sharedPref: ISharedPref
//    val database: IDatabaseDao
}
