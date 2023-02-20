package swyg.hollang.repository.recommendation

import org.springframework.data.jpa.repository.JpaRepository
import swyg.hollang.entity.Recommendation

interface RecommendationJpaRepository: JpaRepository<Recommendation, Long> {
}