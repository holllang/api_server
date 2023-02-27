package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.User

data class CreateTestResponseResponse(
    @JsonIgnore val userEntity: User,
    @JsonIgnore val recommendationEntity: Recommendation
) {
    val user: UserDto = UserDto(userEntity)
    val recommendation: RecommendationDto = RecommendationDto(recommendationEntity)
}

data class UserDto (@JsonIgnore val userEntity: User) {
    val id: Long = userEntity.id!!
    val name: String = userEntity.name
}

data class RecommendationDto(@JsonIgnore val recommendationEntity: Recommendation) {
    val id: Long = recommendationEntity.id!!
}