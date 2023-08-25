package org.mekenzh.security.webapp.repository

import org.mekenzh.security.webapp.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByLogin(login: String): UserEntity?
}