package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Category (

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "level", nullable = false)
    val level: Int

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "category_parent_id")
    val parent: Category? = null

    @ManyToMany(mappedBy = "categories", fetch = LAZY)
    val hobbies: MutableList<Hobby>? = null
}