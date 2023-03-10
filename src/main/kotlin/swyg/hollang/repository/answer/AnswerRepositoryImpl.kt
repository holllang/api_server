package swyg.hollang.repository.answer

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Answer
import swyg.hollang.entity.QAnswer.answer
import swyg.hollang.entity.QQuestion.question
import swyg.hollang.entity.QTest.test

@Repository
class AnswerRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory): AnswerRepository {

    override fun findByQuestionNumberPairsWithTestVersion(
        questionAnswerPairs: List<Pair<Long, Long>>, testVersion: Long): List<Answer> {

        //빌더를 이용해서 여러개의 조건 조합을 만든다.
        val predicates = questionAnswerPairs.map { pair ->
            val questionNumber = pair.first
            val answerNumber = pair.second
            question.number.eq(questionNumber).and(answer.number.eq(answerNumber))
        }

        val builder = predicates.fold(BooleanBuilder(), BooleanBuilder::or)

        //작성한 빌더를 토대로 쿼리문 생성
        return jpaQueryFactory
            .selectFrom(answer)
            .leftJoin(answer.question, question).fetchJoin()
            .leftJoin(question.test, test).fetchJoin()
            .where(builder.and(test.version.eq(testVersion)))
            .fetch()
    }

}