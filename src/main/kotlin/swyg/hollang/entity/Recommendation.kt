package swyg.hollang.entity

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import org.hibernate.annotations.Type
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Recommendation (

    @OneToOne
    @JoinColumn(name = "test_response_id", nullable = false, updatable = false)
    var testResponse: TestResponse,

    @Type(JsonType::class)
    @Column(name = "result", columnDefinition = "json", nullable = false, updatable = false)
    var result: MutableMap<String, Any>? = mutableMapOf()

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    var id: Long? = null
}