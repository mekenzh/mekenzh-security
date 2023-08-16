package org.mekenzh.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("org.mekenzh.security.config")
class MekenzhSecurityApplication

fun main(args: Array<String>) {
	runApplication<MekenzhSecurityApplication>(*args)
}
