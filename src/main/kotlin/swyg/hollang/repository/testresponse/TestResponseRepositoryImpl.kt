package swyg.hollang.repository.testresponse

import org.springframework.stereotype.Repository
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User

@Repository
class TestResponseRepositoryImpl(private val testResponseJpaRepository: TestResponseJpaRepository)
    : TestResponseRepository {

    override fun save(user: User): TestResponse {
        return testResponseJpaRepository.save(TestResponse(user))
    }

    override fun countAll(): Long {
        return testResponseJpaRepository.countAll()
    }

}