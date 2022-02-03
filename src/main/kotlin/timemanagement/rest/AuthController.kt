package timemanagement.rest

import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import timemanagement.dto.UserDetailsDto
import timemanagement.dto.UserDtoResponse
import timemanagement.exception.InvalidUserDetailsException
import timemanagement.exception.NotFoundException
import timemanagement.model.User
import timemanagement.service.AuthService


const val AUTH_ENDPOINT = "/auth/**"

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    companion object {
        fun getUsernameLoggedIn() = SecurityContextHolder.getContext().authentication.name
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registration(@RequestBody userDto: UserDetailsDto) {
        if (authService.checkUserLogin(userDto.login))
            throw InvalidUserDetailsException("User with login ${userDto.login} is already registered")
        authService.createUser(userDto)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody userDto: UserDetailsDto): UserDtoResponse {
        if (!authService.checkUserPass(userDto.login, userDto.pass))
            throw InvalidUserDetailsException("Invalid username or pass")
        val user: User = authService.getUser(userDto.login) ?: throw NotFoundException()
        return UserDtoResponse(user)
    }
}