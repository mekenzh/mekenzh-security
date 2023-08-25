package org.mekenzh.security.webapp.entity

import jakarta.persistence.*

@Entity
@Table(name = "ROLES")
data class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column(nullable = false)
    val name: String,
    val description: String?
)
