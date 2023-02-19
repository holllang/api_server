package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.repository.hobby.HobbyRepository

@Service
@Transactional(readOnly = true)
class HobbyService(private val hobbyRepository: HobbyRepository) {

    //추천받은 취미의 추천 카운트를 1씩 증가
    @Transactional
    fun addHobbiesRecommendCount(hobbies: MutableList<MutableMap<String, String>>){
        for (hobby in hobbies) {
            val findHobby = hobbyRepository.findByName(hobby["name"]!!)
            findHobby.recommendCount = findHobby.recommendCount?.plus(1)
        }

    }
}