package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question
import swyg.hollang.entity.Test

/**
 * @JsonIgnore
 * 엔티티는 밖으로 노출(컨트롤러 단)되면 LAZY Loading init 예외 발생한다.
 * 그 이유는 트랜잭션 생명주기를 서비스와 dao단에서만 고정했기 떄문이다.
 */
data class TestDetailsResponse (@JsonIgnore val testEntity: Test) {
    val test: TestDto = TestDto(testEntity)
}

data class TestDto (@JsonIgnore val testEntity: Test) {
    val id: Long = testEntity.id!!
    val version: Long = testEntity.version
    val questions: List<QuestionDto> = testEntity.questions.map { QuestionDto(it) }
}

data class QuestionDto(@JsonIgnore val question: Question) {
    val id: Long = question.id!!
    val number: Long = question.number
    val content: String = question.content
    val imageUrl: String = question.imageUrl
    val answers: List<AnswerDto> = question.answers.map { AnswerDto(it) }
}

data class AnswerDto(@JsonIgnore val answer: Answer) {
    val id: Long = answer.id!!
    val number: Long = answer.number
    val content: String = answer.content
}