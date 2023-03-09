package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import org.hibernate.annotations.BatchSize
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Question (

    @Column(name = "question_number", nullable = false)
    val number: Long,

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    val test: Test,

    @Column(name = "content", nullable = false)
    val content: String,

    @Column(name = "image_url", nullable = false)
    val imageUrl: String

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    val id: Long? = null

    @OneToMany(mappedBy = "question", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    @BatchSize(size = 100)
    val answers: MutableList<Answer> = mutableListOf()

}