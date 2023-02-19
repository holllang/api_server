package swyg.hollang.repository.testresponse

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.TestResponse

interface TestResponseJpaRepository: JpaRepository<TestResponse, Long> {

    @Query("select count(tr) from TestResponse tr")
    fun countAll(): Long
}