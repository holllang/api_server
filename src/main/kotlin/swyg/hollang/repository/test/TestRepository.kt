package swyg.hollang.repository.test

import swyg.hollang.entity.Test

interface TestRepository {

    fun findWithQuestionsAndAnswersByVersion(version: Long): Test

}