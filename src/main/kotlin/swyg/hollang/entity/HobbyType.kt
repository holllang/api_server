package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class HobbyType(

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "mbti_type", nullable = false, length = 4)
    var mbtiType: String,

    @Column(name = "three_dimension_img_url", nullable = false)
    var threeDimensionImgUrl: String,

    @Column(name = "img_url", nullable = false)
    var imgUrl: String,

    @ElementCollection
    @CollectionTable(
        name="fit_hobby_type",
        joinColumns = [JoinColumn(name = "hobby_type_id")]
    )
    var fitHobbyTypes: MutableList<String> = mutableListOf()

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_type_id")
    var id: Long? = null

}