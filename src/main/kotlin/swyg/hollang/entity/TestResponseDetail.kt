package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class TestResponseDetail (

    @Id
    @ManyToOne
    @JoinColumn(name = "test_response_id", nullable = false, updatable = false)
    var testResponse: TestResponse,

    @Id
    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false, updatable = false)
    var answer: Answer

) : BaseTimeEntity()