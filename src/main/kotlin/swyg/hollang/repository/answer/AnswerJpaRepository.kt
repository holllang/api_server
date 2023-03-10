package swyg.hollang.repository.answer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.entity.Answer

interface AnswerJpaRepository: JpaRepository<Answer, Long> {

}