package swyg.hollang.repository

import swyg.hollang.entity.Test

interface TestRepository {

    fun findWithQuestionsAndAnswersByVersion(version: Long): Test

}