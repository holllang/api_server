package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User
import swyg.hollang.repository.testresponse.TestResponseRepository

@Service
@Transactional(readOnly = true)
class TestResponseService(private val testResponseRepository: TestResponseRepository) {

    @Transactional
    fun createTestResponse(user: User): TestResponse {
        return testResponseRepository.save(user)
    }
}