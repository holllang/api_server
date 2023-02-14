package swyg.hollang.dto.common

data class SuccessResponse<T> (val code: String, val message: String, val data: T)
