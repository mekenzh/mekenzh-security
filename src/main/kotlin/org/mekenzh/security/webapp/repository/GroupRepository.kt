package org.mekenzh.security.webapp.repository

import org.mekenzh.security.webapp.entity.GroupEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : CrudRepository<GroupEntity, Long> {
    fun findByName(name: String): GroupEntity
}