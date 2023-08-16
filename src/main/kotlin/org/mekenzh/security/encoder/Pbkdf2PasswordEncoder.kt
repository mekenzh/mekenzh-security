package org.mekenzh.security.encoder

import org.springframework.stereotype.Component
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Component
class Pbkdf2PasswordEncoder : PasswordEncoder {
    private val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

    override fun encode(password: String, salt: ByteArray): ByteArray {
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, 30000, 256)
        return factory.generateSecret(spec).encoded
    }
}