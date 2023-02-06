package swyg.hollang.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class HobbyAndType (

    @Id
    @ManyToOne
    @JoinColumn(name = "hobby_id")
    var hobby: Hobby? = null,

    @Id
    @ManyToOne
    @JoinColumn(name = "hobby_type_id")
    var hobbyType: HobbyType? = null

) : BaseTimeEntity()