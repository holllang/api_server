package swyg.hollang.repository.testresponsedetail

import org.springframework.data.jpa.repository.JpaRepository
import swyg.hollang.entity.TestResponseDetail
import swyg.hollang.entity.id.TestResponseDetailId

interface TestResponseDetailJpaRepository: JpaRepository<TestResponseDetail, TestResponseDetailId> {
}