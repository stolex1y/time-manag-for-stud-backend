package timemanagement.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import timemanagement.repository.UserRepository

@Component
class UserDetailsServiceImpl(private val repository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: timemanagement.model.User = repository.findUserByLogin(username) ?: throw UsernameNotFoundException("User not found")
        val authorities: List<SimpleGrantedAuthority> = listOf(SimpleGrantedAuthority("user"));
        return User(user.login, user.pass, authorities)
    }
}