package swyg.hollang.repository.testresponsedetail

import swyg.hollang.entity.Answer
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.TestResponseDetail

interface TestResponseDetailRepository {

    fun save(testResponse: TestResponse, answer: Answer): TestResponseDetail

    fun batchInsert(testResponseDetails: List<TestResponseDetail>): Int
}