package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.entity.*

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class TestResponseDetailServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testResponseDetailService: TestResponseDetailService
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
    fun createTestResponseDetail() {
        //given
        val createdUser = User("쨈")
        em.persist(createdUser)
        val createdTestResponse = TestResponse(createdUser)
        em.persist(createdTestResponse)
        em.flush()

        val createTestResponseDetailRequests = mutableListOf(
            CreateTestResponseDetailRequest(1, 1),
            CreateTestResponseDetailRequest(2, 2),
            CreateTestResponseDetailRequest(3, 1),
            CreateTestResponseDetailRequest(4, 1),
            CreateTestResponseDetailRequest(5, 2),
            CreateTestResponseDetailRequest(6, 1),
            CreateTestResponseDetailRequest(7, 2),
            CreateTestResponseDetailRequest(8, 1),
            CreateTestResponseDetailRequest(9, 1),
            CreateTestResponseDetailRequest(10, 2),
            CreateTestResponseDetailRequest(11, 2),
            CreateTestResponseDetailRequest(12, 2),
        )

        //when
        testResponseDetailService.createTestResponseDetail(createdTestResponse, createTestResponseDetailRequests)

        //then
        val findTestResponseDetails =
            em.createQuery("select trd from TestResponseDetail trd where trd.testResponse.id = :testResponseId",
                TestResponseDetail::class.java)
                .setParameter("testResponseId", createdTestResponse.id)
                .resultList

        assertThat(findTestResponseDetails.size).isSameAs(12)
        assertThat(findTestResponseDetails[0].testResponse.user.name).isEqualTo(createdUser.name)
    }
}