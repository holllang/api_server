package swyg.hollang.repository.recommendation

import org.springframework.stereotype.Repository
import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.TestResponse

@Repository
class RecommendationRepositoryImpl(private val recommendationJpaRepository: RecommendationJpaRepository)
    : RecommendationRepository {

    override fun save(testResponse: TestResponse, result: MutableMap<String, Any>): Recommendation {
        return recommendationJpaRepository.save(Recommendation(testResponse, result))
    }
}