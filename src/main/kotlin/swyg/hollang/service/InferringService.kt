package swyg.hollang.service

import org.springframework.stereotype.Service
import swyg.hollang.dto.CreateRecommendationResultResponse
import swyg.hollang.dto.CreateTestResponseDetailRequest

@Service
class InferringService(private val recommendationApiClient: RecommendationApiClient) {

    fun inferHobbiesAndType(createTestResponseDetailRequests: MutableList<CreateTestResponseDetailRequest>)
        : CreateRecommendationResultResponse {
        return recommendationApiClient.inferHobbiesAndType(createTestResponseDetailRequests)
    }
}