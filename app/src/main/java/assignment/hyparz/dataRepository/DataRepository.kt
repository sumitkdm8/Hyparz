package assignment.hyparz.dataRepository

import android.content.Context
import assignment.hyparz.dataRepository.backend.BackendAPI
import assignment.hyparz.dataRepository.backend.IBackendAPI

class DataRepository : IDataRepository {
    private var backendAPIBuilder: BackendAPI.Builder? = null

    private constructor(
        backendAPI: BackendAPI.Builder,
    ) {
        backendAPIBuilder = backendAPI
    }

    private constructor(application: Context) : this(
        BackendAPI.Builder(application),
    )

    override val backendAPI: IBackendAPI
        get() = backendAPIBuilder!!.build()

    companion object {
        private var dataRepository: DataRepository? = null
        fun getInstance(application: Context): IDataRepository? {
            if (dataRepository == null) dataRepository = DataRepository(application)
            return dataRepository
        }
    }
}
