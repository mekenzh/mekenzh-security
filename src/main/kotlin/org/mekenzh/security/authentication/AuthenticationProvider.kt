package org.mekenzh.security.authentication
import org.mekenzh.security.core.Authentication
import org.mekenzh.security.core.AuthenticationException
import kotlin.reflect.KClass

interface AuthenticationProvider {
    /**
     * Performs authentication
     * @throws AuthenticationException if authentication fails
     * */
    fun authenticate(authentication: Authentication): Authentication?

    /**
     * Checks if this provider supports the given authentication class
     * @return true if this provider supports the given authentication class
     * */
    fun supports(authClass: KClass<out Authentication>): Boolean
}