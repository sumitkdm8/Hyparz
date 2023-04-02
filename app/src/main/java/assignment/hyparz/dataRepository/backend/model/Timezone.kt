package assignment.hyparz.dataRepository.backend.model

import java.io.Serializable

class Timezone : Serializable {
    var offset: String? = null
    var description: String? = null
}