package swyg.hollang.repository.answer

import swyg.hollang.entity.Answer

interface AnswerRepository {

    fun findByQuestionNumberPairsWithTestVersion(questionAnswerPairs: List<Pair<Long, Long>>, testVersion: Long): List<Answer>
}