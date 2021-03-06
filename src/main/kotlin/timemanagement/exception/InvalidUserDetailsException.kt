package timemanagement.exception

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidUserDetailsException(
    msg: String? = "",
    ex: Throwable? = null
) : AuthenticationException(msg, ex)