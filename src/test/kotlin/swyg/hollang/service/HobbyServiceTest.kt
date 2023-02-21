package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Category
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

        for (i in 1..40) {
            val hobby = Hobby(
                mutableListOf(Category("category$i", 1)),
                "홀랑 $i",
                "홀랑 $i 상세정보",
                "https://example.com/hollang$i.png"
            )
            hobby.recommendCount = 40L - i
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

    @Test
    fun getHobbies(){
        //given
        val pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "recommendCount"))

        //when
        val findHobbies = hobbyService.getHobbies(pageRequest)

        //then
        assertThat(findHobbies.pageable.pageSize).isSameAs(20)
        assertThat(findHobbies.content[0].name).isEqualTo("홀랑 1")

    }
}