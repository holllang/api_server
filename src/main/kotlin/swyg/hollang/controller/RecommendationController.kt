package swyg.hollang.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swyg.hollang.dto.RecommendHobbyAndTypesResponse
import swyg.hollang.dto.common.SuccessResponse
import swyg.hollang.service.HobbyService
import swyg.hollang.service.HobbyTypeService
import swyg.hollang.service.RecommendationService
import swyg.hollang.utils.WebProperties

@RestController
@RequestMapping("/recommendations")
class RecommendationController(
    private val recommendationService: RecommendationService,
    private val hobbyService: HobbyService,
    private val hobbyTypeService: HobbyTypeService
) {

    @GetMapping("/{recommendationId}")
    fun recommendHobbyAndTypes(@PathVariable("recommendationId") recommendationId: Long)
        : ResponseEntity<SuccessResponse<RecommendHobbyAndTypesResponse>> {
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

        return ResponseEntity.ok()
            .body(SuccessResponse(
                HttpStatus.OK.name,
                WebProperties.SUCCESS_RESPONSE_MESSAGE,
                RecommendHobbyAndTypesResponse(
                    findRecommendation, recommendedHobbyType, recommendedHobbies, fitHobbyTypes
                )
            ))
    }
}