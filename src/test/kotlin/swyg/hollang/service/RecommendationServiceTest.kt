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
import swyg.hollang.dto.CreateRecommendationResultResponse
import swyg.hollang.entity.*

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class RecommendationServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val recommendationService: RecommendationService
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
    fun save() {
        //given
        val createdUser = User("쨈")
        em.persist(createdUser)
        val createdTestResponse = TestResponse(createdUser)
        em.persist(createdTestResponse)

        val hobbyType = mutableMapOf("name" to "홀랑 유형")
        val hobbies = mutableListOf<MutableMap<String, String>>()
        hobbies.add(mutableMapOf("name" to "홀랑 1"))
        hobbies.add(mutableMapOf("name" to "홀랑 2"))
        hobbies.add(mutableMapOf("name" to "홀랑 3"))
        val createRecommendationResultResponse = CreateRecommendationResultResponse(hobbies, hobbyType)

        //when
        val createdRecommendation = recommendationService
            .save(createdTestResponse, createRecommendationResultResponse)
        //then
        val findRecommendation = em.createQuery(
            "select r from Recommendation r where r.testResponse = :testResponse",
            Recommendation::class.java
        ).setParameter("testResponse", createdTestResponse)
            .resultList
        assertThat(createdRecommendation).isEqualTo(findRecommendation[0])
        assertThat(createdRecommendation.result.toString()).isEqualTo(findRecommendation[0].result.toString())
    }
}