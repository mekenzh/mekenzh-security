package org.mekenzh.security.authentication

import org.mekenzh.security.core.AuthenticationException

class UserNotFoundException(message: String) : AuthenticationException(message) {
}