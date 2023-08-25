package org.mekenzh.security.authentication

import org.mekenzh.security.config.SecurityAppProperties
import org.mekenzh.security.core.Authentication
import org.mekenzh.security.core.AuthenticationException
import org.mekenzh.security.core.CredentialsContainer
import org.springframework.stereotype.Component


@Component
class AuthenticationManager(
    private val providers: List<AuthenticationProvider>,
    securityProperties: SecurityAppProperties,
) {
    private val eraseCredentials: Boolean = securityProperties.eraseCredentials

    init {
        if (providers.isEmpty()) {
            throw IllegalArgumentException("No AuthenticationProviders found")
        }
    }

    /**
     * Attempts to authenticate the passed [authentication] object
     * @return a fully authenticated object including credentials
     * @throws AuthenticationException if authentication fails
     * */
    fun authenticate(authentication: Authentication): Authentication {
        var result: Authentication?
        var lastException: AuthenticationException? = null
        for (provider in providers) {
            if (provider.supports(authentication::class)) {
                try {
                    result = provider.authenticate(authentication)
                    if (result != null) {
                        if (eraseCredentials && result is CredentialsContainer) {
                            result.eraseCredentials()
                        }
                        return result
                    }
                } catch (e: AuthenticationException) {
                    lastException = e
                } catch (e: Exception) {
                    throw e
                }
            }
        }
        if (lastException != null) {
            throw lastException
        } else {
            throw ProviderNotFoundException("No AuthenticationProvider found for ${authentication::class}")
        }
    }
}