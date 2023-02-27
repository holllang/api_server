package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Category
import swyg.hollang.entity.Hobby
import swyg.hollang.repository.hobby.HobbyRepository

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class HobbyRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val hobbyRepository: HobbyRepository
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
    fun findByName(){
        //given
        val validName = "홀랑 5"
        val invalidName = "홀랑 0"
        //when
        val findValidHobby = hobbyRepository.findByName(validName)

        //then
        assertThat(findValidHobby.description).isEqualTo("$validName 상세정보")
        assertThatThrownBy { hobbyRepository.findByName(invalidName) }
            .isExactlyInstanceOf(JpaObjectRetrievalFailureException::class.java)
    }

    @Test
    fun findAllPageable(){
        //given
        val pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "recommendCount"))

        //when
        val findHobbies = hobbyRepository.findAll(pageRequest)

        //then
        assertThat(findHobbies.pageable.pageSize).isSameAs(20)
        assertThat(findHobbies.content[0].name).isEqualTo("홀랑 1")
    }
}