package org.mekenzh.security.encoder

import org.mekenzh.security.config.SecurityAppProperties
import org.springframework.stereotype.Component
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Component
class Pbkdf2PasswordEncoder(props: SecurityAppProperties) : PasswordEncoder {
    private val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    private val pepper = props.pepper.toByteArray()
    private val iterations = props.pbkdf2.iterations
    override fun encode(password: String, salt: ByteArray): ByteArray {
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt + pepper, iterations, 256)
        return factory.generateSecret(spec).encoded
    }
}