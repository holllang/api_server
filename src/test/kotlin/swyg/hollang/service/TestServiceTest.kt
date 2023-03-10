package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class TestServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testService: TestService) {

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
    fun findTestByVersion() {
        //given
        val validVersion: Long = 1
        val invalidVersion: Long = 2

        //when
        val testResponse = testService.findShuffledTestByVersion(validVersion)

        //then
        assertThat(testResponse.test.questions.size).isSameAs(12)

        assertThatThrownBy { testService.findShuffledTestByVersion(invalidVersion) }
            .isExactlyInstanceOf(JpaObjectRetrievalFailureException::class.java)
    }
}