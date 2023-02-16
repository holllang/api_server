package swyg.hollang.repository.testresponse

import org.springframework.data.jpa.repository.JpaRepository
import swyg.hollang.entity.TestResponse

interface TestResponseJpaRepository: JpaRepository<TestResponse, Long> {

}