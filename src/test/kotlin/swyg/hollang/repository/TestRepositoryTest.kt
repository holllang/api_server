package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
class TestRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testRepository: TestRepository) {

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
    fun findWithQuestionsAndAnswersByVersion() {
        //given
        val validVersion: Long = 1
        val invalidVersion: Long = 2

        //when
        val test1 = testRepository.findWithQuestionsAndAnswersByVersion(validVersion)

        //then
        assertThat(test1.questions.size).isSameAs(12)

        assertThatThrownBy { testRepository.findWithQuestionsAndAnswersByVersion(invalidVersion) }
            .isExactlyInstanceOf(JpaObjectRetrievalFailureException::class.java)
    }

}