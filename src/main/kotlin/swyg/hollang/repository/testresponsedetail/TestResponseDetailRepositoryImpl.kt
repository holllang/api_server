package swyg.hollang.repository.testresponsedetail

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Answer
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.TestResponseDetail
import java.sql.Date
import java.time.LocalDate
import java.time.ZonedDateTime

@Repository
class TestResponseDetailRepositoryImpl(
    private val testResponseDetailJpaRepository: TestResponseDetailJpaRepository,
    private val jdbcTemplate: JdbcTemplate)
    : TestResponseDetailRepository {

    override fun save(testResponse: TestResponse, answer: Answer): TestResponseDetail {
        return testResponseDetailJpaRepository.save(TestResponseDetail(testResponse, answer))
    }

    //Batch size를 지정하여 벌크 연산을 진행
    override fun batchInsert(testResponseDetails: List<TestResponseDetail>): Int {
        val batchSize = 50
        return jdbcTemplate.batchUpdate(
            "insert into test_response_detail(test_response_id, answer_id, created_at, updated_at) values (?, ?, ?, ?)",
            testResponseDetails, batchSize
        ) { ps, testResponseDetail ->
            ps.setLong(1, testResponseDetail.testResponse.id!!)
            ps.setLong(2, testResponseDetail.answer.id!!)
            ps.setObject(3, ZonedDateTime.now())
            ps.setObject(4, ZonedDateTime.now())
        }.size
    }

}