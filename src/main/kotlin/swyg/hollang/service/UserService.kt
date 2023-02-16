package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateUserRequest
import swyg.hollang.entity.User
import swyg.hollang.repository.user.UserRepository

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun createUser(createUserRequest: CreateUserRequest): User {
        return userRepository.save(createUserRequest)
    }
}