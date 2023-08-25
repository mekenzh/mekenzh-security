package org.mekenzh.security.webapp.entity

import jakarta.persistence.*

@Entity
@Table(name = "USERS")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    val login: String,
    @Column(name = "ENC_PASSWORD") val encPassword: ByteArray,
    val salt: ByteArray,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "USER_GROUPS",
        joinColumns = [JoinColumn(name = "USER_ID")],
        inverseJoinColumns = [JoinColumn(name = "GROUP_ID")]
    )
    val groupEntities: Set<GroupEntity>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity
        if (id != other.id) return false
        if (login != other.login) return false
        if (!encPassword.contentEquals(other.encPassword)) return false
        if (!salt.contentEquals(other.salt)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + login.hashCode()
        result = 31 * result + encPassword.contentHashCode()
        result = 31 * result + salt.contentHashCode()
        return result
    }
}
