package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class HobbyType (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_type_id")
    var id: Long? = null,

    @OneToMany(mappedBy = "hobbyType", cascade = [CascadeType.ALL])
    var hobbyAndTypes: List<HobbyAndType>? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "description", nullable = false)
    var description: String? = null,

    @Column(name = "front_img_url")
    var frontImgUrl: String? = null,

    @Column(name = "back_img_url")
    var backImgUrl: String? = null

) : BaseTimeEntity()