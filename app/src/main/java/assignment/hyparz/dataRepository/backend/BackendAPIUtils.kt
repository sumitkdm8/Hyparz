package assignment.hyparz.dataRepository.backend

import android.content.Context

abstract class BackendAPIUtils(mBackendAPIBuilder: BackendAPI.Builder) : RetrofitFactory(mBackendAPIBuilder) {
    protected val context: Context = mBackendAPIBuilder.context
    protected var responseCode = 100
}
