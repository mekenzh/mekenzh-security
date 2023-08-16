package org.mekenzh.security.controllers

import org.mekenzh.security.config.SecurityAppProperties
import org.mekenzh.security.model.User
import org.mekenzh.security.services.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService, val config: SecurityAppProperties) {


    @PostMapping("/user")
    fun createUser(@RequestBody user: User) {
        userService.createUser(user)
    }
}


