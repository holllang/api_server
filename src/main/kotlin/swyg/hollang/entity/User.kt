package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class User (

    @Column(name = "name", nullable = false)
    var name: String,

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null

    @OneToOne(mappedBy = "user", fetch = LAZY, cascade = [ALL])
    var testResponse: TestResponse? = null

}