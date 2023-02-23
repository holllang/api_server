package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.HobbyType
import swyg.hollang.repository.hobbytype.HobbyTypeRepository

@Service
@Transactional(readOnly = true)
class HobbyTypeService(private val hobbyTypeRepository: HobbyTypeRepository) {

    fun getHobbyTypeByMbtiType(mbtiType: String): HobbyType {
        return hobbyTypeRepository.findWithFitHobbyTypesByMbtiType(mbtiType)
    }

    fun getHobbyTypesByMbtiTypes(mbtiTypes: List<String>): List<HobbyType> {
        return hobbyTypeRepository.findByMbtiTypeIsIn(mbtiTypes)
    }
}