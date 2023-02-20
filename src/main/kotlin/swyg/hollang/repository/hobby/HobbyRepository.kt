package swyg.hollang.repository.hobby

import swyg.hollang.entity.Hobby

interface HobbyRepository {

    fun findByName(name: String): Hobby

    fun findByNameIsIn(names: List<String>): List<Hobby>
}