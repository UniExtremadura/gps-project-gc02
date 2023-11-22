package com.example.gc02.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gc02.model.User

@Dao interface UserDao {
    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User
    @Insert
    suspend fun insert(user: User): Long
}