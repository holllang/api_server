package swyg.hollang.repository.hobbytype

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import swyg.hollang.entity.HobbyType

@Repository
class HobbyTypeRepositoryImpl(private val hobbyTypeJpaRepository: HobbyTypeJpaRepository)
    : HobbyTypeRepository {

    override fun findByMbtiType(mbtiType: String): HobbyType {
        return hobbyTypeJpaRepository.findByMbtiType(mbtiType)
            ?: throw EntityNotFoundException("홀랑 유형 $mbtiType 을 찾을 수 없습니다.")
    }

    override fun findByMbtiTypeIsIn(mbtiTypes: List<String>): List<HobbyType> {
        return hobbyTypeJpaRepository.findByMbtiTypeIsIn(mbtiTypes)
    }
}