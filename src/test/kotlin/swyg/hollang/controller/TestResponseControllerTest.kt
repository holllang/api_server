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
import swyg.hollang.entity.*

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
class TestResponseControllerTest(
    @Autowired private val em: EntityManager,
    @Autowired private val mockMvc: MockMvc
) {

    @BeforeEach
    fun beforeEach() {
        val test = swyg.hollang.entity.Test(1)
        em.persist(test)

        for(i in 1..12){
            val question = Question(i.toLong(), test, "질문 $i", "https://question$i")
            em.persist(question)

            for(j in 1..2){
                val answer = Answer(question, j.toLong(), "질문 $i 답변 $j")
                em.persist(answer)
                question.answers.add(answer)
            }

            test.questions.add(question)
        }

        val hobby1 = Hobby(
            mutableListOf(),
            "유튜버 시작하기",
            "유튜버 시작하기 상세정보",
            "https://example.com/hollang.png"
        )
        val hobby2 = Hobby(
            mutableListOf(),
            "요가",
            "요가 상세정보",
            "https://example.com/hollang.png"
        )
        val hobby3 = Hobby(
            mutableListOf(),
            "헬스",
            "헬스 상세정보",
            "https://example.com/hollang.png"
        )
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)
    }

    @Test
    fun createTestResponse(){

        val requestBody: String = "{\n" +
                "    \"user\": {\n" +
                "        \"name\": \"쨈\"\n" +
                "    },\n" +
                "    \"testResponseDetail\": [\n" +
                "        {\n" +
                "            \"questionNumber\": 1,\n" +
                "            \"answerNumber\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 2,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 3,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 4,\n" +
                "            \"answerNumber\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 5,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 6,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 7,\n" +
                "            \"answerNumber\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 8,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 9,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 10,\n" +
                "            \"answerNumber\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 11,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 12,\n" +
                "            \"answerNumber\": 1\n" +
                "        }\n" +
                "    ]\n" +
                "}"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/test-responses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
    }

    @Test
    fun countAllTestResponse(){

        //given
        val testCount = 10L
        for(i in 1..testCount){
            val createdUser = User("쨈$i")
            em.persist(createdUser)
            val createdTestResponse = TestResponse(createdUser)
            em.persist(createdTestResponse)
        }

        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/test-responses/count")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.content().json("{\n" +
                    "    \"code\": \"OK\",\n" +
                    "    \"message\": \"요청이 성공하였습니다.\",\n" +
                    "    \"data\": {\n" +
                    "        \"testResponse\": {\n" +
                    "            \"count\": 10\n" +
                    "        }\n" +
                    "    }\n" +
                    "}"))
    }
}