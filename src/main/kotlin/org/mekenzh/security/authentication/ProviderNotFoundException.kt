package org.mekenzh.security.authentication

import org.mekenzh.security.core.AuthenticationException

class ProviderNotFoundException(msg: String) : AuthenticationException(msg)
