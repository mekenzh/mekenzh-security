package org.mekenzh.security.core

class UsernamePasswordAuthentication(
    override val name: String,
    override val roles: Set<Role> = mutableSetOf(),
    override var authenticated: Boolean = false,
    override var credentials: Any?,
    override val details: Any,
) : Authentication, CredentialsContainer {
    override fun eraseCredentials() {
        credentials = null
    }
}