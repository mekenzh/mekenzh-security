package org.mekenzh.security.webapp.service

import org.mekenzh.security.encoder.PasswordEncoder
import org.mekenzh.security.webapp.entity.UserEntity
import org.mekenzh.security.webapp.model.User
import org.mekenzh.security.webapp.repository.UserRepository
import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val groupService: GroupService
) {

    fun createUser(user: User) {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        val password = passwordEncoder.encode(user.password, salt)
        val groupEntities = groupService.findGroupsByName(user.groups)
        userRepository.save(UserEntity(null, user.login, password, salt, groupEntities))
    }
}
//[6, -15, -113, -11, -74, -33, -3, -27, -26, 65, 60, -124, 63, 45, 114, -65]
//[-64, 96, 64, -98, 45, -127, 120, 67, -95, 79, -68, -2, 120, -52, -43, -59, 50, 57, 25, 92, -58, 124, 64, 73, 71, -97, -109, 27, 120, 21, -60, -79]