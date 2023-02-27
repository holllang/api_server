package swyg.hollang.repository.recommendation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.Recommendation

interface RecommendationJpaRepository: JpaRepository<Recommendation, Long> {

    @Query("select r from Recommendation r " +
            "join fetch r.testResponse tr " +
            "join fetch tr.user u " +
            "where r.id = :recommendationId")
    fun findWithUserById(recommendationId: Long): Recommendation?
}