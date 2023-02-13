package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Answer (

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    var question: Question,

    @Column(name = "answer_number", nullable = false)
    var number: Long,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "img_url", nullable = false)
    var imgUrl: String

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    var id: Long? = null

}