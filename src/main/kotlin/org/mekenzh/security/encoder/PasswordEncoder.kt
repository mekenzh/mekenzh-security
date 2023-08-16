package org.mekenzh.security.encoder

interface PasswordEncoder {
    fun encode(password: String, salt: ByteArray): ByteArray
}
