package swyg.hollang.repository.testresponsedetail

import org.springframework.stereotype.Repository
import swyg.hollang.entity.Answer
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.TestResponseDetail

@Repository
class TestResponseDetailRepositoryImpl(private val testResponseDetailJpaRepository: TestResponseDetailJpaRepository)
    : TestResponseDetailRepository {

    override fun save(testResponse: TestResponse, answer: Answer): TestResponseDetail {
        return testResponseDetailJpaRepository.save(TestResponseDetail(testResponse, answer))
    }

}