package swyg.hollang.repository.recommendation

import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.TestResponse

interface RecommendationRepository {

    fun save(testResponse: TestResponse, result: MutableMap<String, Any>): Recommendation

    fun findWithUserById(recommendationId: Long): Recommendation
}