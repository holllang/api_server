package swyg.hollang.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swyg.hollang.dto.CountAllTestResponseResponse
import swyg.hollang.dto.CreateTestResponseRequest
import swyg.hollang.dto.CreateTestResponseResponse
import swyg.hollang.dto.TestDetailsResponse
import swyg.hollang.dto.common.SuccessResponse
import swyg.hollang.service.*
import swyg.hollang.utils.WebProperties
import java.util.Objects

@RestController
@RequestMapping("/test-responses")
@CrossOrigin(origins = ["\${client.server.host}"])
class TestResponseController(
    private val userService: UserService,
    private val testResponseService: TestResponseService,
    private val testResponseDetailService: TestResponseDetailService,
    private val inferringService: InferringService,
    private val recommendationService: RecommendationService,
    private val hobbyService: HobbyService
) {

    @PostMapping
    fun createTestResponse(@Valid @RequestBody createTestResponseRequest: CreateTestResponseRequest)
        : ResponseEntity<SuccessResponse<Any>> {
        //유저 엔티티 생성
        val createdUser = userService.createUser(createTestResponseRequest.createUserRequest)
        //테스트 응답 엔티티 생성
        val createdTestResponse = testResponseService.createTestResponse(createdUser)
        //테스트 응답 상세정보 엔티티 생성
        testResponseDetailService.createTestResponseDetail(
            createdTestResponse, createTestResponseRequest.createTestResponseDetailRequests)
        //추론 서버에 추론 요청
        val createRecommendationResultResponse =
            inferringService.inferHobbiesAndType(createTestResponseRequest.createTestResponseDetailRequests)
        //추천받은 취미들의 추천수 카운트 증가
        hobbyService.addHobbiesRecommendCount(createRecommendationResultResponse.hobbies)
        //추천 엔티티 생성
        val createdRecommendation = recommendationService.save(createdTestResponse, createRecommendationResultResponse)

        return ResponseEntity.ok()
            .body(SuccessResponse(
                HttpStatus.OK.name,
                WebProperties.SUCCESS_RESPONSE_MESSAGE,
                CreateTestResponseResponse(createdUser, createdRecommendation)))
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