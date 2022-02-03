package timemanagement.dto

import timemanagement.model.User

data class UserDtoRequest(
    var login: String
) {
    fun update(user: User) {
        user.login = login
    }
}

data class UserDtoResponse(
    var id: Int?,
    var login: String,
) {
    constructor(user: User): this(user.id, user.login)
}
