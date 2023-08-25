package org.mekenzh.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.DefaultValue

@ConfigurationProperties(prefix = "mekenzh.security")
data class SecurityAppProperties(
    @DefaultValue("q1w2e3r4t5y6u7i8o9p0")
    val pepper: String,
    @DefaultValue("true")
    val eraseCredentials: Boolean,
    val pbkdf2: Pbkdf2
) {
    data class Pbkdf2(
        @DefaultValue("310000")
        val iterations: Int
    )
}
