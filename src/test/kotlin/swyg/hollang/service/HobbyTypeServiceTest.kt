package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.HobbyType

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class HobbyTypeServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val hobbyTypeService: HobbyTypeService
) {

    @BeforeEach
    fun beforeEach() {
        val hobbyType = HobbyType(
            "홀랑 유형",
            "홀랑 유형 상세정보",
            "ENFP",
            "https://example.com/example.fbx",
            "https://example.com/example.png",
            mutableListOf("ESTJ", "ESFP", "INFJ")
        )
        em.persist(hobbyType)

    }

    @Test
    fun findByMbtiType(){
        //given
        val mbtiType = "ENFP"

        //when
        val findHobbyType = hobbyTypeService.getHobbyTypeByMbtiType(mbtiType)

        //then
        Assertions.assertThat(findHobbyType.name).isEqualTo("홀랑 유형")
        Assertions.assertThat(findHobbyType.fitHobbyTypes.size).isSameAs(3)
        Assertions.assertThat(findHobbyType.fitHobbyTypes.contains("ESTJ")).isTrue
    }

    @Test
    fun findByMbtiTypeIsIn(){
        //given
        val hobbyType1 = HobbyType(
            "홀랑 유형 1",
            "홀랑 유형 1 상세정보",
            "ESTJ",
            "https://example.com/example.fbx",
            "https://example.com/example.png",
            mutableListOf("ESTJ", "ESFP", "INFJ")
        )
        em.persist(hobbyType1)
        val hobbyType2 = HobbyType(
            "홀랑 유형 2",
            "홀랑 유형 2 상세정보",
            "ESFP",
            "https://example.com/example.fbx",
            "https://example.com/example.png",
            mutableListOf("ESTJ", "ESFP", "INFJ")
        )
        em.persist(hobbyType2)
        val hobbyType3 = HobbyType(
            "홀랑 유형 3",
            "홀랑 유형 3 상세정보",
            "INFJ",
            "https://example.com/example.fbx",
            "https://example.com/example.png",
            mutableListOf("ESTJ", "ESFP", "INFJ")
        )
        em.persist(hobbyType3)

        //when
        val mbtiTypes = listOf("ESTJ", "ESFP", "INFJ")
        val findHobbyTypes = hobbyTypeService.getHobbyTypesByMbtiTypes(mbtiTypes)

        //then
        Assertions.assertThat(findHobbyTypes.size).isSameAs(3)
        Assertions.assertThat(findHobbyTypes[0]).isInstanceOf(HobbyType::class.java)
        Assertions.assertThat(findHobbyTypes[0].name).isIn(mutableListOf("홀랑 유형 1", "홀랑 유형 2", "홀랑 유형 3"))
    }
}