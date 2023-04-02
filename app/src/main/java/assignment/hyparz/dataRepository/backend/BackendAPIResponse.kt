package assignment.hyparz.dataRepository.backend

import androidx.annotation.Keep
import com.google.gson.Gson

@Keep
class BackendAPIResponse<T>() {
    var status: ResponseStatus? = null
    var message: String? = null
    private var data: T? = null
    private val gson: Gson = Gson()


    constructor(status: ResponseStatus?, data: T?) : this() {
        this.status = status
        this.data = data
    }

    constructor(status: ResponseStatus?, message: String?) : this(status, message, null) {}
    constructor(status: ResponseStatus?, message: String?, data: T?) : this(status, data) {
        this.message = message
    }


    fun getData(tClass: Class<T>?): T {
        return gson.fromJson(dataJSON, tClass)
    }

    val dataJSON: String
        get() = gson.toJson(data)

    val isFailure: Boolean
        get() = data == null || status == null || status === ResponseStatus.failure

    val isSuccess: Boolean
        get() = status === ResponseStatus.success
}
