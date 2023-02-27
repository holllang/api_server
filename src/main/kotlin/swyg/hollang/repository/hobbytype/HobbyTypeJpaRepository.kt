package swyg.hollang.repository.hobbytype

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.HobbyType

interface HobbyTypeJpaRepository: JpaRepository<HobbyType, Long> {

    @Query("select ht from HobbyType ht join fetch ht.fitHobbyTypes where ht.mbtiType = :mbtiType")
    fun findWithFitHobbyTypesByMbtiType(mbtiType: String): HobbyType?

    fun findByMbtiTypeIsIn(mbtiTypes: List<String>): List<HobbyType>
}