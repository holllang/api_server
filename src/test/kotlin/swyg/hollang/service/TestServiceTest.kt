package swyg.hollang.service

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
        val testResponse = testService.findTestByVersion(validVersion)

        //then
        Assertions.assertThat(testResponse.test.questions.size).isSameAs(12)
        Assertions.assertThat(testResponse.test.questions[0].content).isEqualTo("질문 1")
        Assertions.assertThat(testResponse.test.questions[0].answers[0].content).isEqualTo("질문 1 답변 1")

        Assertions.assertThatThrownBy { testService.findTestByVersion(invalidVersion) }
            .isExactlyInstanceOf(EntityNotFoundException::class.java)
    }
}