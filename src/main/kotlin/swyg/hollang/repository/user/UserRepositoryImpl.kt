package swyg.hollang.repository.user

import org.springframework.stereotype.Repository
import swyg.hollang.dto.CreateUserRequest
import swyg.hollang.entity.User

@Repository
class UserRepositoryImpl(private val userJpaRepository: UserJpaRepository): UserRepository {

    override fun save(createUserRequest: CreateUserRequest): User {
        return userJpaRepository.save(User(createUserRequest.name))
    }

}