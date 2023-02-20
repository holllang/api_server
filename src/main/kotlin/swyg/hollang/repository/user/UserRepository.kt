package swyg.hollang.repository.user

import swyg.hollang.dto.CreateUserRequest
import swyg.hollang.entity.User

interface UserRepository {

    fun save(createUserRequest: CreateUserRequest): User
}