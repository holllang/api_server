package swyg.hollang.repository.hobby

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Hobby

@Repository
class HobbyRepositoryImpl(private val hobbyJpaRepository: HobbyJpaRepository): HobbyRepository {

    override fun findByName(name: String): Hobby {
        return hobbyJpaRepository.findByName(name)
            ?: throw EntityNotFoundException("취미 $name 을 찾을 수 없습니다.")
    }

    override fun findByNameIsIn(names: List<String>): List<Hobby> {
        return hobbyJpaRepository.findByNameIsIn(names)
    }

    override fun findAll(pageable: Pageable): Page<Hobby> {
        return hobbyJpaRepository.findAll(pageable)
    }
}