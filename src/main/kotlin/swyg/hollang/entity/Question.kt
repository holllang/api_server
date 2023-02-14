package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import org.hibernate.annotations.BatchSize
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Question (

    @Column(name = "question_number", nullable = false)
    var number: Long,

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    var test: Test,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "img_url", nullable = false)
    var imgUrl: String

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    var id: Long? = null

    @OneToMany(mappedBy = "question", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    @BatchSize(size = 100)
    var answers: MutableList<Answer> = mutableListOf()

}