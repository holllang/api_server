package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class HobbyType(

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "mbti_type", nullable = false, length = 4)
    val mbtiType: String,

    @Column(name = "three_dimension_img_url", nullable = false)
    val threeDimensionImageUrl: String,

    @Column(name = "img_url", nullable = false)
    val imageUrl: String,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name="fit_hobby_type",
        joinColumns = [JoinColumn(name = "hobby_type_id")]
    )
    val fitHobbyTypes: MutableList<String> = mutableListOf()

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_type_id")
    val id: Long? = null

}