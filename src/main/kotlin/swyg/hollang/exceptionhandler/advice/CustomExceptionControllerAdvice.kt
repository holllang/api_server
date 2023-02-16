package swyg.hollang.exceptionhandler.advice

import jakarta.persistence.EntityNotFoundException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import swyg.hollang.dto.common.ExceptionResponse

@Order(0)
@RestControllerAdvice
class CustomExceptionControllerAdvice {

    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFoundExHandler(ex: EntityNotFoundException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity
            .badRequest()
            .body(ExceptionResponse(HttpStatus.BAD_REQUEST.name, ex.message.toString()))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionResponse> {
        val errors = ex.bindingResult.allErrors.map { it.defaultMessage }.joinToString(",")
        val defaultMessage = ex.bindingResult.fieldError?.defaultMessage
        return ResponseEntity.badRequest().body(ExceptionResponse(HttpStatus.BAD_REQUEST.name, defaultMessage.toString()))
    }
}