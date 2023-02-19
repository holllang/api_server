package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore

data class CountAllTestResponseResponse(
    @JsonIgnore val count: Long) {
    val testResponse: CountTestResponseDto = CountTestResponseDto(count)
}

data class CountTestResponseDto(val count: Long)
