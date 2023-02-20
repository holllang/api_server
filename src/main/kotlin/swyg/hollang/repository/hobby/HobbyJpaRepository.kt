package swyg.hollang.repository.hobby

import org.springframework.data.jpa.repository.JpaRepository
import swyg.hollang.entity.Hobby

interface HobbyJpaRepository: JpaRepository<Hobby, Long> {

    fun findByName(name: String): Hobby?

    fun findByNameIsIn(names: List<String>): List<Hobby>
}