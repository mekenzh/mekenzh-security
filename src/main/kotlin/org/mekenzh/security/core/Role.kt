package org.mekenzh.security.core

interface Role {
    val name: String
}
data class SimpleRole(override val name: String) : Role
