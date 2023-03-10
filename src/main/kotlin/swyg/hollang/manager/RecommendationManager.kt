package swyg.hollang.manager

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.RecommendHobbyAndTypesResponse
import swyg.hollang.entity.Recommendation
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
        val findRecommendation = recommendationService
            .getRecommendationWithUserById(recommendationId = recommendationId)
        //추천 결과에 해당하는 취미 유형 정보를 가져온다.
        val recommendedHobbyType = hobbyTypeService
            .getHobbyTypeByMbtiType(mbtiType = extractMbtiType(findRecommendation))
        //추천받은 취미 유형과 잘맞는 유형을 가져온다.
        val fitHobbyTypes = hobbyTypeService
            .getHobbyTypesByMbtiTypes(mbtiTypes = recommendedHobbyType.fitHobbyTypes)
        //추천받은 취미들의 정보를 가져온다.
        val recommendedHobbies = hobbyService.getHobbyByName(names = extractHobbyNames(findRecommendation))

        return RecommendHobbyAndTypesResponse(
            findRecommendation, recommendedHobbyType, recommendedHobbies, fitHobbyTypes
        )
    }

    private fun extractHobbyNames(findRecommendation: Recommendation) =
        findRecommendation.result?.get("hobbies")?.let { hobbies ->
            //value가 null값이 아닌 결과만 반환한다
            (hobbies as? List<Map<String, String>>)?.mapNotNull { it["name"] }
        } ?: emptyList()

    private fun extractMbtiType(findRecommendation: Recommendation) =
        findRecommendation.result?.get("hobbyType")?.let { hobbyType ->
            (hobbyType as? Map<String, String>)?.get("name") ?: throw IllegalStateException("Invalid hobby type")
        } ?: throw IllegalStateException("Recommendation result not found")
}