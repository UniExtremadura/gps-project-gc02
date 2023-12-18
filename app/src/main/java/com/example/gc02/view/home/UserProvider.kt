package com.example.gc02.view.home

import com.example.gc02.model.User

interface UserProvider {
    fun getUser(): User
}