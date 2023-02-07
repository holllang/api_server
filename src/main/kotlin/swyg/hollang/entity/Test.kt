package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Test (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    var id: Long? = null,

    @Column(name = "version", unique = true, updatable = false, nullable = false)
    var version: Long? = null,

    @OneToMany(mappedBy = "test", fetch = LAZY, cascade = [ALL])
    var questions: List<Question>? = null

) : BaseTimeEntity()