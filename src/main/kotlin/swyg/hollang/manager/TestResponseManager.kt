package swyg.hollang.manager

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateTestResponseRequest
import swyg.hollang.dto.CreateTestResponseResponse
import swyg.hollang.service.*

@Component
class TestResponseManager(
    private val userService: UserService,
    private val testResponseService: TestResponseService,
    private val testResponseDetailService: TestResponseDetailService,
    private val inferringService: InferringService,
    private val recommendationService: RecommendationService,
    private val hobbyService: HobbyService
) {

    @Transactional
    fun createUserTestResponse(createTestResponseRequest: CreateTestResponseRequest): CreateTestResponseResponse {
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

        return CreateTestResponseResponse(createdUser, createdRecommendation)
    }
}