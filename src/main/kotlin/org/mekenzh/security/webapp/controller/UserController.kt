package org.mekenzh.security.webapp.controller

import org.mekenzh.security.core.AuthenticationHolder
import org.mekenzh.security.webapp.model.User
import org.mekenzh.security.webapp.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) {

    @GetMapping("/hello")
    fun securedHello(): String {
        val authentication = AuthenticationHolder.authentication.get()
        return "Secured Hello to ${authentication?.name}!"
    }


    @PostMapping("/user")
    fun createUser(@RequestBody user: User) {
        userService.createUser(user)
    }
}


