package assignment.hyparz.dataRepository.backend.model

import java.io.Serializable

class Location : Serializable {
    var street: Street? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null
    var postcode: Any? = null
    var coordinates: Coordinates? = null
    var timezone: Timezone? = null
}