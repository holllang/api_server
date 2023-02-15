package swyg.hollang.exceptionhandler.advice

import jakarta.persistence.EntityNotFoundException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import swyg.hollang.dto.common.ExceptionResponse

@Order(0)
@RestControllerAdvice
class TestExceptionControllerAdvice {

    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFoundExHandler(ex: EntityNotFoundException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity
            .badRequest()
            .body(ExceptionResponse(HttpStatus.BAD_REQUEST.name, ex.message.toString()))
    }
}