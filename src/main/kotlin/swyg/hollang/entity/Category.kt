package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Category (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "category_parent_id")
    var parent: Category? = null,

    @ManyToMany(fetch = LAZY)
    @JoinTable(
        name = "category_hobby",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "hobby_id")]
    )
    var hobbies: List<Hobby>? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "level", nullable = false)
    var level: Int? = null

) : BaseTimeEntity()