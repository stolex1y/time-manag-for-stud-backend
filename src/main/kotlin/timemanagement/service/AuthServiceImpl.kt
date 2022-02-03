package timemanagement.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import timemanagement.dto.UserDetailsDto
import timemanagement.model.User
import timemanagement.repository.UserRepository

@Service
class AuthServiceImpl(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : AuthService {

    override fun checkUserPass(login: String, pass: String): Boolean {
        val user = repository.findUserByLogin(login) ?: return false
        return passwordEncoder.matches(pass, user.pass)
    }

    override fun checkUserLogin(login: String): Boolean {
        return repository.existsUserByLogin(login)
    }

    override fun getUser(login: String): User? {
        return repository.findUserByLogin(login)
    }

    override fun createUser(userDto: UserDetailsDto): User? {
        val user: User? = repository.findUserByLogin(userDto.login)
        return if (user != null) null
            else {
                userDto.pass = passwordEncoder.encode(userDto.pass)
                repository.save(userDto.toUser())
            }
    }

    override fun updateUserPass(userDto: UserDetailsDto): User? {
        val user: User = repository.findUserByLogin(userDto.login) ?: return null
        user.pass = passwordEncoder.encode(userDto.pass)
        return repository.save(user)
    }
}