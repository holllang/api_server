package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.dto.CreateTestResponseRequest
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.TestResponseDetail
import swyg.hollang.repository.answer.AnswerRepository
import swyg.hollang.repository.testresponsedetail.TestResponseDetailRepository
import java.time.ZonedDateTime

@Service
@Transactional(readOnly = true)
class TestResponseDetailService(
    private val answerRepository: AnswerRepository,
    private val testResponseDetailRepository: TestResponseDetailRepository) {

    @Transactional
    fun createTestResponseDetail(
        testResponse: TestResponse,
        createTestResponseDetailRequests: MutableList<CreateTestResponseDetailRequest>) {

        val testResponseDetails = mutableListOf<TestResponseDetail>()
        for (createTestResponseDetailRequest in createTestResponseDetailRequests) {
            val findAnswer = answerRepository
                .findByQuestionNumberWithTestVersion(
                    createTestResponseDetailRequest.answerNumber,
                    createTestResponseDetailRequest.questionNumber,
                    1)
            val testResponseDetail = TestResponseDetail(testResponse, findAnswer)
            testResponseDetails.add(testResponseDetail)
        }
        testResponseDetailRepository.batchInsert(testResponseDetails)
    }
}