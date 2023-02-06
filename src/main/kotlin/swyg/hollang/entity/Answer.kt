package swyg.hollang.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Answer (

    @Id
    @Column(name = "answer_number")
    var number: Long? = null,

    @ManyToOne
    @JoinColumn(name = "question_number")
    var question: Question? = null,

    @Column(name = "content", nullable = false)
    var content: String? = null,

    @Column(name = "img_url")
    var imgUrl: String? = null

) : BaseTimeEntity()