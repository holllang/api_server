package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.Hobby
import swyg.hollang.entity.HobbyType
import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.User

data class RecommendHobbyAndTypesResponse(
    @JsonIgnore val recommendationEntity: Recommendation,
    @JsonIgnore val hobbyTypeEntity: HobbyType,
    @JsonIgnore val hobbiesEntity: List<Hobby>,
    @JsonIgnore val fitHobbyTypesEntity: List<HobbyType>){

    val recommendation: RecommendationDto = RecommendationDto(
        recommendationEntity,
        hobbyTypeEntity,
        hobbiesEntity,
        fitHobbyTypesEntity
    )

    data class RecommendationDto(
        @JsonIgnore val recommendationEntity: Recommendation,
        @JsonIgnore val hobbyTypeEntity: HobbyType,
        @JsonIgnore val hobbiesEntity: List<Hobby>,
        @JsonIgnore val fitHobbyTypesEntity: List<HobbyType>
    ) {
        val id: Long = recommendationEntity.id!!
        val user: UserDto = UserDto(recommendationEntity.testResponse.user)
        val hobbyType: HobbyTypeDto = HobbyTypeDto(hobbyTypeEntity)
        val hobbies: List<HobbyDto> = hobbiesEntity.map { HobbyDto(it) }
        val fitHobbyTypes: List<HobbyTypeDto> = fitHobbyTypesEntity.map { HobbyTypeDto(it) }
    }

    data class UserDto(@JsonIgnore val userEntity: User){
        val id: Long = userEntity.id!!
        val name: String = userEntity.name
    }

    data class HobbyTypeDto(@JsonIgnore val hobbyTypeEntity: HobbyType) {
        val id: Long = hobbyTypeEntity.id!!
        val name: String = hobbyTypeEntity.name
        val description: String = hobbyTypeEntity.description
        val threeDimensionImageUrl: String = hobbyTypeEntity.threeDimensionImageUrl
        val imageUrl: String = hobbyTypeEntity.imageUrl
    }

    data class HobbyDto(@JsonIgnore val hobbyEntity: Hobby) {
        val id: Long = hobbyEntity.id!!
        val name: String = hobbyEntity.name
        val description: String = hobbyEntity.description
        val imageUrl: String = hobbyEntity.imageUrl
    }
}



