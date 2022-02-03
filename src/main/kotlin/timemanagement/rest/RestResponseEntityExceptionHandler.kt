package timemanagement.rest

import org.hibernate.JDBCException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import timemanagement.exception.IllegalAccessException
import timemanagement.exception.InvalidUserDetailsException
import timemanagement.exception.NotFoundException
import timemanagement.exception.SqlErrorState

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(JDBCException::class)
    protected fun handleConflict(ex: JDBCException, request: WebRequest): ResponseEntity<Any> {
        val httpStatus: HttpStatus = SqlErrorState.getStatus(ex.sqlState) ?: HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(httpStatus).body(ex.sqlException.message)
    }

    @ExceptionHandler(IllegalAccessException::class)
    protected fun handleConflict(ex: IllegalAccessException, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ex.message, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(NotFoundException::class)
    protected fun handleConflict(ex: NotFoundException, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }
}