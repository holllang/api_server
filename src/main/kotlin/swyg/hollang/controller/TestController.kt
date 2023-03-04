package swyg.hollang.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import swyg.hollang.dto.TestDetailsResponse
import swyg.hollang.service.TestService
import swyg.hollang.dto.common.SuccessResponse
import swyg.hollang.utils.WebProperties

@RestController
@RequestMapping("/tests")
@CrossOrigin(origins = ["\${client.server.host}"])
class TestController(private val testService: TestService) {

    @GetMapping
    fun testDetails(@RequestParam(value = "version", required = true) version: Long)
        : ResponseEntity<SuccessResponse<TestDetailsResponse>> {

        val testDetailsResponse: TestDetailsResponse = testService.findShuffledTestByVersion(version)
        return ResponseEntity.ok()
            .body(SuccessResponse(HttpStatus.OK.name, WebProperties.SUCCESS_RESPONSE_MESSAGE, testDetailsResponse))
    }
}