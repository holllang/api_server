package swyg.hollang.controller

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Category
import swyg.hollang.entity.Hobby

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
class HobbyControllerTest(
    @Autowired private val em: EntityManager,
    @Autowired private val mockMvc: MockMvc
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
    fun getHobbiesRank(){
        //given
        val page = 0
        val size = 20
        val sort = "recommendCount"

        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/hobbies")
                .queryParam("page", page.toString())
                .queryParam("size", size.toString())
                .queryParam("sort", sort))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
    }
}