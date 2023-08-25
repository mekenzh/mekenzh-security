package org.mekenzh.security.webapp.model

data class User(val login: String, val password: String, val groups: Set<String> = mutableSetOf())