package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class TestResponse (

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    var user: User,

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_response_id")
    var id: Long? = null

    @OneToOne(mappedBy = "testResponse", cascade = [ALL], orphanRemoval = true)
    var recommendation: Recommendation? = null

    @OneToMany(mappedBy = "testResponse", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    var testResponseDetails: MutableList<TestResponseDetail> = mutableListOf()

}