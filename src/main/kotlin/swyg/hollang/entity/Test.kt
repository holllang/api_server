package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Test (

    @Column(name = "version", unique = true, updatable = false, nullable = false)
    val version: Long

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    val id: Long? = null

    @OneToMany(mappedBy = "test", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    val questions: MutableList<Question> = mutableListOf()
}