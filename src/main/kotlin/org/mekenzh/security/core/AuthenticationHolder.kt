package org.mekenzh.security.core

object AuthenticationHolder {
    val authentication = ThreadLocal<Authentication?>()
}