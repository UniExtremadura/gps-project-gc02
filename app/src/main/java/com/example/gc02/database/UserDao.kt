package com.example.gc02.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.gc02.model.Article
import com.example.gc02.model.User

@Dao interface UserDao {
    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User
    @Insert
    suspend fun insert(user: User): Long

    @Insert
     fun insert1(user: User): Long
    @Query("SELECT * FROM User")
     fun getAll(): List<User>
}