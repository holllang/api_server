package swyg.hollang.repository

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Test

@Repository
class TestRepositoryImpl(private val testJpaRepository: TestJpaRepository) : TestRepository {

    override fun findWithQuestionsAndAnswersByVersion(version: Long): Test {
        //엘비스 연산자 - null이면 예외 반환 아니면 값을 리턴
        return testJpaRepository.findWithQuestionsAndAnswersByVersion(version)
            ?: throw EntityNotFoundException("버전 ${version}의 테스트를 찾을수 없습니다.")
    }

}