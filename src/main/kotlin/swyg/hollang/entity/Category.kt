package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Category (

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "level", nullable = false)
    var level: Int

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "category_parent_id")
    var parent: Category? = null

    @ManyToMany(mappedBy = "categories", fetch = LAZY)
    var hobbies: MutableList<Hobby>? = null
}