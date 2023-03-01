package swyg.hollang.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swyg.hollang.dto.CountAllTestResponseResponse
import swyg.hollang.dto.CreateTestResponseRequest
import swyg.hollang.dto.common.SuccessResponse
import swyg.hollang.manager.TestResponseManager
import swyg.hollang.service.*
import swyg.hollang.utils.WebProperties

@RestController
@RequestMapping("/test-responses")
@CrossOrigin(origins = ["\${client.server.host}"])
class TestResponseController(
    private val testResponseService: TestResponseService,
    private val testResponseManager: TestResponseManager
) {

    @PostMapping
    fun createTestResponse(@Valid @RequestBody createTestResponseRequest: CreateTestResponseRequest)
        : ResponseEntity<SuccessResponse<Any>> {
        val createTestResponseResponse = testResponseManager.createUserTestResponse(createTestResponseRequest)
        return ResponseEntity.ok()
            .body(SuccessResponse(
                HttpStatus.OK.name,
                WebProperties.SUCCESS_RESPONSE_MESSAGE,
                createTestResponseResponse))
    }

    @GetMapping("/count")
    fun countAllTestResponse(): ResponseEntity<SuccessResponse<CountAllTestResponseResponse>> {
        val numberOfTestResponse = testResponseService.countAllTestResponse()
        return ResponseEntity.ok()
            .body(SuccessResponse(
                HttpStatus.OK.name,
                WebProperties.SUCCESS_RESPONSE_MESSAGE,
                CountAllTestResponseResponse(numberOfTestResponse)
            ))
    }
}