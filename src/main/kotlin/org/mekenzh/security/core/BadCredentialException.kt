package org.mekenzh.security.core


class BadCredentialsException : AuthenticationException {

    constructor(msg: String) : super(msg) {}


    constructor(msg: String, cause: Throwable) : super(msg, cause) {}
}