package ir.sk.stock.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ControllerAdvisor {
    @ExceptionHandler(StockNotFoundException::class)
    fun handleStockNotFoundException(ex: StockNotFoundException): ResponseEntity<ApiErrorMessage> =
        createErrorResponse(HttpStatus.NOT_FOUND, ex.message)

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseEntity<ApiErrorMessage> =
        createErrorResponse(HttpStatus.BAD_REQUEST, "${ex.name} should be of type ${ex.requiredType?.name}")

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(ex: MissingServletRequestParameterException): ResponseEntity<ApiErrorMessage> =
        createErrorResponse(HttpStatus.BAD_REQUEST, "Required request param '${ex.parameterName}' is missing")

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ApiErrorMessage> {
        val defaultMessage: String = ex.bindingResult.fieldErrors.first().defaultMessage ?: "Validation failed"
        return createErrorResponse(HttpStatus.BAD_REQUEST, defaultMessage)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<ApiErrorMessage> {
        val defaultMessage: String = ex.constraintViolations.first().message ?: "Validation failed"
        return createErrorResponse(HttpStatus.BAD_REQUEST, defaultMessage)
    }

    private fun createErrorResponse(status: HttpStatus, message: String?): ResponseEntity<ApiErrorMessage> {
        val apiErrorMessage = ApiErrorMessage(
            status.value(),
            status.reasonPhrase,
            message
        )
        return ResponseEntity(apiErrorMessage, status)
    }
}
