package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class TestResponse (

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    val user: User,

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_response_id")
    val id: Long? = null

    @OneToOne(mappedBy = "testResponse", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    val recommendation: Recommendation? = null

    @OneToMany(mappedBy = "testResponse", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    val testResponseDetails: MutableList<TestResponseDetail> = mutableListOf()

}