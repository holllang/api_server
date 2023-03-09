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
    val testResponse: TestResponse,

    @Type(JsonType::class)
    @Column(name = "result", columnDefinition = "json", nullable = false, updatable = false)
    val result: MutableMap<String, Any>? = mutableMapOf()

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    val id: Long? = null
}