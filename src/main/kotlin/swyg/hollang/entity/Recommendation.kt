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
    @JoinColumn(name = "test_response_id")
    var testResponse: TestResponse? = null,

    @Type(JsonType::class)
    @Column(name = "result", columnDefinition = "json")
    var result: Map<String, Objects>? = null

) : BaseTimeEntity()