package swyg.hollang.repository.answer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.Answer

interface AnswerJpaRepository: JpaRepository<Answer, Long> {

    @Query("select a from Answer a " +
            "join fetch a.question aq " +
            "join fetch aq.test aqt " +
            "where a.number = :answerNumber and aq.number = :questionNumber and aqt.version = :testVersion")
    fun findByQuestionNumberWithTestVersion(answerNumber: Long, questionNumber: Long, testVersion: Long): Answer?
}