package swyg.hollang.entity.id

import swyg.hollang.entity.Answer
import swyg.hollang.entity.TestResponse
import java.io.Serializable

data class TestResponseDetailId(
    val testResponse: TestResponse? = null,
    val answer: Answer? = null): Serializable