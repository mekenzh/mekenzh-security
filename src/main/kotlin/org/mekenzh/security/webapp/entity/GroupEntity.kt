package org.mekenzh.security.webapp.entity

import jakarta.persistence.*

@Entity
@Table(name = "GROUPS")
data class GroupEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    val name: String,
    val description: String?,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "GROUP_ROLES",
        joinColumns = [JoinColumn(name = "GROUP_ID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID")]
    )
    val roles: Set<RoleEntity>
)