package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.Hobby

data class GetHobbiesRankResponse(@JsonIgnore val hobbiesEntity: List<Hobby>) {
    val hobbies: List<HobbyDto> = hobbiesEntity.map { HobbyDto(it) }

    data class HobbyDto(@JsonIgnore val hobbyEntity: Hobby){
        val id: Long = hobbyEntity.id!!
        val name: String = hobbyEntity.name
        val recommendCount: Long = hobbyEntity.recommendCount!!
    }
}
