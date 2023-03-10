package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.TestResponseDetail
import swyg.hollang.repository.answer.AnswerRepository
import swyg.hollang.repository.testresponsedetail.TestResponseDetailRepository

@Service
@Transactional(readOnly = true)
class TestResponseDetailService(
    private val answerRepository: AnswerRepository,
    private val testResponseDetailRepository: TestResponseDetailRepository) {

    fun createTestResponseDetail(
        testResponse: TestResponse,
        createTestResponseDetailRequests: MutableList<CreateTestResponseDetailRequest>): Int {

        val questionAnswerPairs = createTestResponseDetailRequests.map {
            it.questionNumber to it.answerNumber
        }

        val findAnswers =
            answerRepository.findByQuestionNumberPairsWithTestVersion(questionAnswerPairs, 1)

        val testResponseDetails = findAnswers.map {
            TestResponseDetail(testResponse = testResponse, answer = it)
        }

        return testResponseDetailRepository.batchInsert(testResponseDetails)
    }

}