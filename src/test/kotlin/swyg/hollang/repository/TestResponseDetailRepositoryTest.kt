package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User
import swyg.hollang.entity.id.TestResponseDetailId
import swyg.hollang.repository.testresponsedetail.TestResponseDetailRepository

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class TestResponseDetailRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testResponseDetailRepository: TestResponseDetailRepository
) {

    @BeforeEach
    fun beforeEach() {
        val test = swyg.hollang.entity.Test(1)
        em.persist(test)

        for(i in 1..12){
            val question = Question(i.toLong(), test, "질문 $i", "https://question$i")
            em.persist(question)

            for(j in 1..2){
                val answer = Answer(question, j.toLong(), "질문 $i 답변 $j")
                em.persist(answer)
                question.answers.add(answer)
            }

            test.questions.add(question)
        }
    }

    @Test
    fun save() {
        //given
        val savedUser = User("쨈")
        em.persist(savedUser)
        val savedTestResponse = TestResponse(savedUser)
        em.persist(savedTestResponse)
        val findAnswers = em.createQuery(
            "select a from Answer a " +
                    "where a.number = :answerNumber " +
                    "and a.question.number = :questionNumber " +
                    "and a.question.test.version = :testVersion"
        )
            .setParameter("answerNumber", 2)
            .setParameter("questionNumber", 1)
            .setParameter("testVersion", 1)
            .resultList as MutableList<Answer>

        //when
        val savedTestResponseDetail = testResponseDetailRepository.save(savedTestResponse, findAnswers[0])

        //then
        assertThat(savedTestResponseDetail.testResponse).isEqualTo(savedTestResponse)
        assertThat(savedTestResponseDetail.answer).isEqualTo(findAnswers[0])
        assertThat(savedTestResponseDetail.answer.content).isEqualTo("질문 1 답변 2")
        assertThat(savedTestResponseDetail.answer.question.content).isEqualTo("질문 1")
    }
}