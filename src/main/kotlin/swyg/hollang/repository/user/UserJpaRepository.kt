package swyg.hollang.repository.user

import org.springframework.data.jpa.repository.JpaRepository
import swyg.hollang.entity.User

interface UserJpaRepository: JpaRepository<User, Long> {
}