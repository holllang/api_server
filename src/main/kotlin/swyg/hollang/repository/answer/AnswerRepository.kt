package swyg.hollang.repository.answer

import swyg.hollang.entity.Answer

interface AnswerRepository {

    fun findByQuestionNumberWithTestVersion(answerNumber: Long, questionNumber: Long, testVersion: Long): Answer
}