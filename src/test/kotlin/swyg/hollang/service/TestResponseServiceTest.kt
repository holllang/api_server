package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
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

}