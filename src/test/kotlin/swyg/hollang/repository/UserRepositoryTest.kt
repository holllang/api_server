package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateUserRequest
import swyg.hollang.entity.User
import swyg.hollang.repository.user.UserRepository

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class UserRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val userRepository: UserRepository) {

    @Test
    fun save(){
        //given
        val createUserRequest1 = CreateUserRequest("쨈")
        val createUserRequest2 = CreateUserRequest("쟁")

        //when
        val savedUser1 = userRepository.save(createUserRequest1)
        val savedUser2 = userRepository.save(createUserRequest2)

        val findUser1 = em.createQuery("select u from User u where u.name = :name", User::class.java)
            .setParameter("name", "쨈")
            .resultList
        val findUser2 = em.createQuery("select u from User u where u.name = :name", User::class.java)
            .setParameter("name", "쟁")
            .resultList

        //then
        assertThat(findUser1.size).isSameAs(1)
        assertThat(findUser1[0]).isEqualTo(savedUser1)
        assertThat(findUser2.size).isSameAs(1)
        assertThat(findUser2[0]).isEqualTo(savedUser2)
    }
}