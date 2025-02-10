package com.org.childmonitorparent.auth.models

data class User (
    val userType: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = ""
)