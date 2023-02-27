package swyg.hollang.repository.recommendation

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.TestResponse

@Repository
class RecommendationRepositoryImpl(private val recommendationJpaRepository: RecommendationJpaRepository)
    : RecommendationRepository {

    override fun save(testResponse: TestResponse, result: MutableMap<String, Any>): Recommendation {
        return recommendationJpaRepository.save(Recommendation(testResponse, result))
    }

    override fun findWithUserById(recommendationId: Long): Recommendation {
        return recommendationJpaRepository.findWithUserById(recommendationId)
            ?: throw EntityNotFoundException("추천 $recommendationId 번을 찾을 수 없습니다.")
    }
}