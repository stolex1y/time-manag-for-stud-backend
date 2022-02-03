package timemanagement.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class IllegalAccessException(
    message: String? = "",
    cause: Throwable? = null
) : RuntimeException(message, cause) {
    constructor(cause: Throwable?, message: String?) : this(message, cause)
    constructor(c: Throwable?) : this(cause = c)
    constructor(m: String?) : this(message = m)
}