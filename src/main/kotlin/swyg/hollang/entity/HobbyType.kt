package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class HobbyType (

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "three_dimension_img_url", nullable = false)
    var threeDimensionImgUrl: String,

    @Column(name = "img_url", nullable = false)
    var imgUrl: String

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_type_id")
    var id: Long? = null

    @OneToMany(mappedBy = "hobbyType", cascade = [CascadeType.ALL])
    var hobbyAndTypes: List<HobbyAndType>? = null
}