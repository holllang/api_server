package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User
import swyg.hollang.repository.testresponse.TestResponseRepository

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class TestResponseRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testResponseRepository: TestResponseRepository) {

    @Test
    fun save() {
        //given
        val createdUser = User("쨈")
        em.persist(createdUser)

        //when
        val savedTestResponse = testResponseRepository.save(createdUser)

        val findTestResponse = em.createQuery("select tr from TestResponse tr where tr.user.id = :userId", TestResponse::class.java)
            .setParameter("userId", createdUser.id)
            .resultList

        //then
        assertThat(findTestResponse.size).isSameAs(1)
        assertThat(findTestResponse[0]).isEqualTo(savedTestResponse)
    }

    @Test
    fun countAll(){
        //given
        val testCount = 10L
        for(i in 1..testCount){
            val createdUser = User("쨈$i")
            em.persist(createdUser)
            val createdTestResponse = TestResponse(createdUser)
            em.persist(createdTestResponse)
        }
        //when
        val testResponseCount = testResponseRepository.countAll()

        //then
        assertThat(testResponseCount).isSameAs(testCount)
    }
}