package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Question (

    @Id
    @Column(name = "question_number")
    var number: Long? = null,

    @ManyToOne
    @JoinColumn(name = "test_id")
    var test: Test? = null,

    @OneToMany(mappedBy = "question", fetch = LAZY, cascade = [ALL])
    var answers: List<Answer>? = null,

    @Column(name = "content", nullable = false)
    var content: String? = null,

    @Column(name = "img_url")
    var imgUrl: String? = null

) : BaseTimeEntity()