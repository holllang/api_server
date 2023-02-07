package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class TestResponse (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_response_id")
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @OneToOne(mappedBy = "testResponse", fetch = LAZY, cascade = [ALL])
    var recommendation: Recommendation? = null,

    @OneToMany(mappedBy = "testResponse", fetch = LAZY, cascade = [ALL])
    var testResponseDetails: List<TestResponseDetail>? = null

) : BaseTimeEntity()