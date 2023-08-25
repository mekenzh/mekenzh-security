package org.mekenzh.security.core

interface Authentication {
    val name: String
    val roles: Set<Role>
    var authenticated: Boolean
    var credentials: Any?
    val details: Any
}
