package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity
import swyg.hollang.entity.id.TestResponseDetailId

@Entity
@IdClass(TestResponseDetailId::class)
class TestResponseDetail (

    @Id
    @ManyToOne
    @JoinColumn(name = "test_response_id", nullable = false, updatable = false)
    val testResponse: TestResponse,

    @Id
    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false, updatable = false)
    val answer: Answer

) : BaseTimeEntity()