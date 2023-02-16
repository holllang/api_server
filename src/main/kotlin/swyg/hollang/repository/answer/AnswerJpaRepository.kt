package swyg.hollang.repository.answer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.Answer

interface AnswerJpaRepository: JpaRepository<Answer, Long> {

    @Query("select a from Answer a " +
            "where a.number = :answerNumber " +
            "and a.question.number = :questionNumber " +
            "and a.question.test.version = :testVersion")
    fun findByQuestionNumberWithTestVersion(answerNumber: Long, questionNumber: Long, testVersion: Long): Answer?
}