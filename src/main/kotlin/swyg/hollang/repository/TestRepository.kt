package swyg.hollang.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.Test

interface TestRepository: JpaRepository<Test, Long> {

    @Query("select t from Test t " +
            "join fetch t.questions tq " +
            "where t.version = :version")
    fun findWithQuestionsAndAnswersByVersion(version: Long): Test?
}