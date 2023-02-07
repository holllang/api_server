package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class User (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @OneToOne(mappedBy = "user", fetch = LAZY, cascade = [ALL])
    var testResponse: TestResponse? = null

) : BaseTimeEntity()