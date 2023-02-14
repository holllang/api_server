package swyg.hollang.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.TestDetailsResponse
import swyg.hollang.repository.TestRepository

@Service
@Transactional(readOnly = true)
class TestService(private val testRepository: TestRepository) {

    fun findTestByVersion(version: Long): TestDetailsResponse {
        //엘비스 연산자 - null이면 예외 반환 아니면 값을 리턴
        val test = testRepository.findWithQuestionsAndAnswersByVersion(version)
            ?: throw EntityNotFoundException("버전 ${version}의 테스트를 찾을수 없습니다.")
        //질문 목록들을 섞는다
        ShuffleService().shuffleElements(test.questions)
        return TestDetailsResponse(test)
    }
}