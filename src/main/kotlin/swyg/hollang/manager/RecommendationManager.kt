package swyg.hollang.manager

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.RecommendHobbyAndTypesResponse
import swyg.hollang.service.HobbyService
import swyg.hollang.service.HobbyTypeService
import swyg.hollang.service.RecommendationService

@Component
class RecommendationManager(
    private val recommendationService: RecommendationService,
    private val hobbyService: HobbyService,
    private val hobbyTypeService: HobbyTypeService
) {

    @Transactional(readOnly = true)
    fun getUserRecommendation(recommendationId: Long): RecommendHobbyAndTypesResponse {
        //추천 id로 추천 결과를 가져온다.
        val findRecommendation = recommendationService.getRecommendationWithUserById(recommendationId)
        //추천 결과에 해당하는 취미 유형 정보를 가져온다.
        val recommendedHobbyType = hobbyTypeService
            .getHobbyTypeByMbtiType((findRecommendation.result!!["hobbyType"] as Map<String, String>)["name"]!!)
        //추천받은 취미 유형과 잘맞는 유형을 가져온다.
        val fitHobbyTypes = hobbyTypeService.getHobbyTypesByMbtiTypes(recommendedHobbyType.fitHobbyTypes)
        //추천받은 취미들의 정보를 가져온다.
        val hobbyNames = (findRecommendation.result!!["hobbies"] as List<Map<String, String>>).map {
            it["name"]!!
        }
        val recommendedHobbies = hobbyService.getHobbyByName(hobbyNames)

        return RecommendHobbyAndTypesResponse(
            findRecommendation, recommendedHobbyType, recommendedHobbies, fitHobbyTypes
        )
    }
}