package org.mekenzh.security.authentication

import org.mekenzh.security.core.Authentication
import org.mekenzh.security.core.Role
import org.mekenzh.security.core.SimpleRole
import org.mekenzh.security.core.UsernamePasswordAuthentication
import org.mekenzh.security.encoder.PasswordEncoder
import org.mekenzh.security.webapp.entity.UserEntity
import org.mekenzh.security.webapp.repository.UserRepository
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class DatabaseAuthenticationProvider (val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) : AuthenticationProvider{
    private val supportedAuthClasses = setOf<KClass<out Authentication>>(UsernamePasswordAuthentication::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        if (authentication !is UsernamePasswordAuthentication) {
            throw IllegalArgumentException("Authentication type: ${authentication::class} is not supported")
        }

        userRepository.findByLogin(authentication.name)?.let { userEntity ->
            val encodedPassword = passwordEncoder.encode(authentication.credentials.toString(), userEntity.salt)
            if (userEntity.encPassword.contentEquals(encodedPassword)) {
                val roleSet = collectAllRoles(userEntity)

                return UsernamePasswordAuthentication(
                    name = userEntity.login,
                    roles = roleSet,
                    credentials = arrayOf(authentication.credentials),
                    details = authentication.details,
                )
            }
        }
        throw UserNotFoundException("User ${authentication.name} not found")
    }

    private fun collectAllRoles(userEntity: UserEntity): Set<Role> {
        val roleSet = mutableSetOf<Role>()
        userEntity.groupEntities.forEach { groupEntity ->
            groupEntity.roles.forEach { roleEntity ->
                roleSet.add(SimpleRole(roleEntity.name))
            }
        }
        return roleSet
    }

    override fun supports(authClass: KClass<out Authentication>): Boolean {
        return authClass in supportedAuthClasses
    }
}