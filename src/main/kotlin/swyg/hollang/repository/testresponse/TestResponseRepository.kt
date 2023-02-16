package swyg.hollang.repository.testresponse

import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User

interface TestResponseRepository {

    fun save(user: User): TestResponse
}