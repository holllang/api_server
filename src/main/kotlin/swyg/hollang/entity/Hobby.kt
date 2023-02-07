package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
@DynamicInsert  //DML 작동시 null값이 아닌 값만 작동함
class Hobby (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    var id: Long? = null,

    @ManyToMany(mappedBy = "hobbies", fetch = LAZY)
    var categories: List<Category>? = null,

    @OneToMany(mappedBy = "hobby", cascade = [CascadeType.ALL])
    var hobbyAndTypes: List<HobbyAndType>? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "description", nullable = false)
    var description: String? = null,

    @Column(name = "img_url")
    var imgUrl: String? = null,

    @Column(name = "recommend_count", nullable = false)
    @ColumnDefault(value = 0.toString())
    var recommendCount: Long? = null,

) : BaseTimeEntity()