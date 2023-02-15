package swyg.hollang.entity

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import swyg.hollang.entity.common.BaseTimeEntity
import java.util.Objects

@Entity
class Recommendation (

    @Id
    @OneToOne
    @JoinColumn(name = "test_response_id", nullable = false, updatable = false)
    var testResponse: TestResponse,

    @Type(JsonType::class)
    @Column(name = "result", columnDefinition = "json", nullable = false, updatable = false)
    var result: MutableMap<String, Objects>? = mutableMapOf()

) : BaseTimeEntity()