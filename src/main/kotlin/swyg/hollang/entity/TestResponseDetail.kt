package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class TestResponseDetail (

    @Id
    @ManyToOne
    @JoinColumn(name = "test_response_id")
    var testResponse: TestResponse? = null,

    @Id
    @ManyToOne
    @JoinColumn(name = "answer_id")
    var answer: Answer? = null

) : BaseTimeEntity()