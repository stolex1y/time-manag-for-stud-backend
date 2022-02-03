package timemanagement.service

import org.springframework.transaction.annotation.Transactional
import timemanagement.dto.UserDetailsDto
import timemanagement.model.User

@Transactional(readOnly = true)
interface AuthService {
    fun checkUserPass(login: String, pass: String): Boolean
    fun checkUserLogin(login: String): Boolean
    fun getUser(login: String): User?
    @Transactional
    fun createUser(userDto: UserDetailsDto): User?

    @Transactional
    fun updateUserPass(userDto: UserDetailsDto): User?
}