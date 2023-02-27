package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class TestResponseServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testResponseService: TestResponseService
) {

    @Test
    fun createTestResponse() {
        //given
        val createdUser = User("쨈")
        em.persist(createdUser)

        //when
        val createdTestResponse = testResponseService.createTestResponse(createdUser)

        //then
        Assertions.assertThat(createdTestResponse.user).isEqualTo(createdUser)
    }

    @Test
    fun countAllTestResponse() {
        //given
        val testCount = 10L
        for(i in 1..testCount){
            val createdUser = User("쨈$i")
            em.persist(createdUser)
            val createdTestResponse = TestResponse(createdUser)
            em.persist(createdTestResponse)
        }
        //when
        val testResponseCount = testResponseService.countAllTestResponse()

        //then
        Assertions.assertThat(testResponseCount).isSameAs(testCount)

    }

}