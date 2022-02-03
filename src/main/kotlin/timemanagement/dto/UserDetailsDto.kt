package timemanagement.dto

import timemanagement.model.User

data class UserDetailsDto(
    var login: String,
    var pass: String
) {
    constructor(user: User): this(user.login, user.pass)

    fun update(user: User) {
        user.login = login
        user.pass = pass
    }

    fun toUser() = User(
        login = this.login,
        pass = this.pass
    )
}