package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.TestDetailsResponse
import swyg.hollang.repository.TestRepository

@Service
@Transactional(readOnly = true)
class TestService(private val testRepository: TestRepository) {

    fun findTestByVersion(version: Long): TestDetailsResponse {
        val test = testRepository.findWithQuestionsAndAnswersByVersion(version)
        //질문 목록들을 섞는다
        test.questions.shuffle()
        return TestDetailsResponse(test)
    }
}