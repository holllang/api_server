package swyg.hollang.dto

data class CreateRecommendationResultResponse(
    val hobbies: MutableList<MutableMap<String, String>>,
    val hobbyType: MutableMap<String, String>
)