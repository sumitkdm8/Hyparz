package assignment.hyparz.dataRepository.backend.model

import java.io.Serializable

class Result : Serializable {
    var gender: String? = null
    var name: Name? = null
    var location: Location? = null
    var email: String? = null
    var login: Login? = null
    var dob: Dob? = null
    var registered: Registered? = null
    var phone: String? = null
    var cell: String? = null
    var id: Id? = null
    var picture: Picture? = null
    var nat: String? = null
}