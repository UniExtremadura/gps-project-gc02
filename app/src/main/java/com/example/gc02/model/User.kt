package com.example.gc02.model

import java.io.Serializable

data class User(
    val name: String = "",
    val password: String = "",
    val email: String = ""
) : Serializable
