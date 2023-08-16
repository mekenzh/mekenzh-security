package org.mekenzh.security.services

import org.mekenzh.security.config.SecurityAppProperties
import org.mekenzh.security.encoder.PasswordEncoder
import org.mekenzh.security.entities.UserEntity
import org.mekenzh.security.model.User
import org.mekenzh.security.repositories.UserRepository
import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val props: SecurityAppProperties
) {

    fun createUser(user: User) {
        val pepper = props.pepper.toByteArray()
        val random = SecureRandom()
        val salt = ByteArray(16 + pepper.size)
        random.nextBytes(salt)
        val password = passwordEncoder.encode(user.password, salt + pepper)
        userRepository.save(UserEntity(null, user.login, password, salt))
    }
}