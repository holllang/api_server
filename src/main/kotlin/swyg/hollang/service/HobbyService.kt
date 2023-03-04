package swyg.hollang.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Hobby
import swyg.hollang.repository.hobby.HobbyRepository

@Service
@Transactional(readOnly = true)
class HobbyService(private val hobbyRepository: HobbyRepository) {

    //추천받은 취미의 추천 카운트를 1씩 증가
    fun addHobbiesRecommendCount(hobbies: MutableList<MutableMap<String, String>>): Int {
        val names = hobbies.map { it["name"] ?: "" }
        val updatedHobbyNumber = hobbyRepository.updateRecommendCountByName(names)
        if(updatedHobbyNumber != hobbies.size){
            throw EntityNotFoundException("추천 수를 증가시킬 취미의 개수가 일치하지 않습니다.")
        }
        return updatedHobbyNumber
    }

    fun getHobbyByName(names: List<String>): List<Hobby> {
        return hobbyRepository.findByNameIsIn(names)
    }

    fun getHobbies(pageable: Pageable): Page<Hobby> {
        return hobbyRepository.findAll(pageable)
    }
}