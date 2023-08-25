package org.mekenzh.security.webapp.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mekenzh.security.authentication.AuthenticationManager
import org.mekenzh.security.core.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets
import java.util.*

const val AUTHENTICATION_SCHEME_BASIC = "Basic"

@Component
class AuthenticationFilter(val authenticationManager: AuthenticationManager) : OncePerRequestFilter() {
    private val authenticationThreadLocal = AuthenticationHolder.authentication
    companion object{
        private val logger: Logger = LoggerFactory.getLogger(AuthenticationFilter::class.java)
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authentication = createAuthFrom(request)
            if (authentication == null) {
                filterChain.doFilter(request, response)
                return
            }
            val session = request.getSession(false)
            if (session != null) {
                request.changeSessionId()
            }
            val authenticatedUser = authenticationManager.authenticate(authentication)
            authenticationThreadLocal.set(authenticatedUser)
        } catch (e: AuthenticationException) {
            authenticationThreadLocal.remove()
            Companion.logger.debug("Authentication request for failed", e)
        }
        filterChain.doFilter(request, response)
    }

    fun createAuthFrom(request: HttpServletRequest): Authentication? {
        var header = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        header = header.trim { it <= ' ' }
        if (!header.startsWith(AUTHENTICATION_SCHEME_BASIC, ignoreCase = true)) {
            return null
        }
        if (header.equals(AUTHENTICATION_SCHEME_BASIC, ignoreCase = true)) {
            throw BadCredentialsException("Empty basic authentication token")
        }
        val base64Token = header.substring(AUTHENTICATION_SCHEME_BASIC.length+1).toByteArray(StandardCharsets.UTF_8)
        val decoded: ByteArray = decode(base64Token)
        val token = String(decoded, StandardCharsets.UTF_8)
        val delim = token.indexOf(":")
        if (delim == -1) {
            throw BadCredentialsException("Invalid basic authentication token")
        }
        val details = WebAuthenticationDetails(request.remoteAddr, extractSessionId(request))
        return UsernamePasswordAuthentication(
            name = token.substring(0, delim),
            credentials = token.substring(delim + 1),
            details = details
        )
    }

    private fun extractSessionId(request: HttpServletRequest): String? {
        val session = request.getSession(false)
        return session?.id
    }

    private fun decode(base64Token: ByteArray): ByteArray {
        return try {
            Base64.getDecoder().decode(base64Token)
        } catch (ex: IllegalArgumentException) {
            throw BadCredentialsException("Failed to decode basic authentication token")
        }
    }
}
data class WebAuthenticationDetails(val remoteAddress: String, val sessionId: String?)