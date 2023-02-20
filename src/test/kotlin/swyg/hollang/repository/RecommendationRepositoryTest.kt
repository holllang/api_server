package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User
import swyg.hollang.repository.recommendation.RecommendationRepository
import java.util.Objects

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class RecommendationRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val recommendationRepository: RecommendationRepository
) {

    @Test
    fun save() {
        //given
        val createdUser = User("쨈")
        em.persist(createdUser)
        val createdTestResponse = TestResponse(createdUser)
        em.persist(createdTestResponse)

        val result = mutableMapOf<String, Any>()
        result["hobbyType"] = "홀랑 유형 1"
        val hobbies = mutableListOf<MutableMap<String, String>>()
        hobbies.add(mutableMapOf("name" to "추천 홀랑 이름 1"))
        hobbies.add(mutableMapOf("name" to "추천 홀랑 이름 2"))
        hobbies.add(mutableMapOf("name" to "추천 홀랑 이름 3"))
        result["hobbies"] = hobbies

        //when
        val savedRecommendation = recommendationRepository.save(createdTestResponse, result)
        //then
        val findRecommendation = em.createQuery(
            "select r from Recommendation r where r.testResponse = :testResponse",
            Recommendation::class.java
        ).setParameter("testResponse", createdTestResponse)
            .resultList

        assertThat(savedRecommendation).isEqualTo(findRecommendation[0])
        assertThat(findRecommendation[0].result?.get("hobbyType")).isEqualTo("홀랑 유형 1")
        assertThat((findRecommendation[0].result?.get("hobbies") as MutableList<MutableMap<String, String>>).size)
            .isSameAs(3)
        assertThat((findRecommendation[0].result?.get("hobbies") as MutableList<MutableMap<String, String>>)[0]["name"])
            .isEqualTo("추천 홀랑 이름 1")
        assertThat((findRecommendation[0].result?.get("hobbies") as MutableList<MutableMap<String, String>>)[1]["name"])
            .isEqualTo("추천 홀랑 이름 2")
        assertThat((findRecommendation[0].result?.get("hobbies") as MutableList<MutableMap<String, String>>)[2]["name"])
            .isEqualTo("추천 홀랑 이름 3")
    }

    @Test
    fun findWithUserById(){
        //given
        val createdUser = User("쨈")
        em.persist(createdUser)
        val createdTestResponse = TestResponse(createdUser)
        em.persist(createdTestResponse)

        val result = mutableMapOf<String, Any>()
        result["hobbyType"] = "홀랑 유형 1"
        val hobbies = mutableListOf<MutableMap<String, String>>()
        hobbies.add(mutableMapOf("name" to "추천 홀랑 이름 1"))
        hobbies.add(mutableMapOf("name" to "추천 홀랑 이름 2"))
        hobbies.add(mutableMapOf("name" to "추천 홀랑 이름 3"))
        result["hobbies"] = hobbies
        val savedRecommendation = recommendationRepository.save(createdTestResponse, result)

        //when
        val findRecommendation = recommendationRepository.findWithUserById(savedRecommendation.id!!)

        //then
        assertThat(findRecommendation.result!!["hobbyType"]).isEqualTo("홀랑 유형 1")
        val findHobbies = findRecommendation.result!!["hobbies"] as MutableList<MutableMap<String, String>>
        assertThat(findHobbies.size).isSameAs(3)
        assertThat(findRecommendation.testResponse.user.name).isEqualTo("쨈")
    }
}