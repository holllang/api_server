package swyg.hollang.repository.answer

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Answer

@Repository
class AnswerRepositoryImpl(private val answerJpaRepository: AnswerJpaRepository): AnswerRepository {

    override fun findByQuestionNumberWithTestVersion(answerNumber: Long, questionNumber: Long, testVersion: Long): Answer {
        return answerJpaRepository.findByQuestionNumberWithTestVersion(answerNumber, questionNumber, testVersion)
            ?: throw EntityNotFoundException(
                "테스트 버전 ${testVersion}에 속한 질문 ${questionNumber}번의 답변 ${answerNumber}을 찾을 수 없습니다.")
    }

}