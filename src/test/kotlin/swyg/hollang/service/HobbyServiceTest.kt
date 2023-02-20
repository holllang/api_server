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
import swyg.hollang.entity.Hobby

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class HobbyServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val hobbyService: HobbyService
) {

    @BeforeEach
    fun beforeEach() {

        for (i in 1..3) {
            val hobby = Hobby(
                mutableListOf(),
                "홀랑 $i",
                "홀랑 $i 상세정보",
                "https://example.com/hollang$i.png"
            )
            em.persist(hobby)
        }
    }

    @Test
    fun addHobbiesRecommendCount(){
        //given
        val hobbies = mutableListOf<MutableMap<String, String>>()
        hobbies.add(mutableMapOf("name" to "홀랑 1"))
        hobbies.add(mutableMapOf("name" to "홀랑 2"))
        hobbies.add(mutableMapOf("name" to "홀랑 3"))
        //when
        hobbyService.addHobbiesRecommendCount(hobbies)

        //then
        val findHobby = em.createQuery("select h from Hobby h where h.name = :name", Hobby::class.java)
            .setParameter("name", "홀랑 1")
            .resultList

        assertThat(findHobby[0].recommendCount).isSameAs(1L)
    }
}