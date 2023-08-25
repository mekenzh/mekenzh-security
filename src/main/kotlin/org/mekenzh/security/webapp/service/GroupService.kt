package org.mekenzh.security.webapp.service

import org.mekenzh.security.webapp.entity.GroupEntity
import org.mekenzh.security.webapp.repository.GroupRepository
import org.springframework.stereotype.Service

@Service
class GroupService(val groupRepository: GroupRepository) {
    fun findGroupsByName(names: Set<String>): Set<GroupEntity> {
        return names.map { groupRepository.findByName(it) }.toSet()
    }
}