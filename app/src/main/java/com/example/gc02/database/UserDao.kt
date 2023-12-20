package com.example.gc02.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.gc02.model.Article
import com.example.gc02.model.User

@Dao interface UserDao {
    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User
    @Insert
    suspend fun insert(user: User): Long

    @Insert
     fun insert1(user: User): Long

    @Update
    suspend fun update(user: User)

    @Update
    fun update1(user: User)

    @Query("SELECT * FROM User")
     fun getAll(): List<User>
    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
     fun findByName1(first: String): User
}