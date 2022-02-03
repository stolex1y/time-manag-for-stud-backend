package timemanagement.exception

import org.springframework.http.HttpStatus

enum class SqlErrorState(val state: String, val status: HttpStatus) {
    INVALID_ARGUMENT_STATE("22023", HttpStatus.BAD_REQUEST),
    ILLEGAL_ACCESS_STATE("0L000", HttpStatus.FORBIDDEN);

    companion object {
        fun getStatus(state: String): HttpStatus? {
            return values().filter {
                it.state == state.uppercase()
            }.getOrNull(0)?.status
        }
    }
}