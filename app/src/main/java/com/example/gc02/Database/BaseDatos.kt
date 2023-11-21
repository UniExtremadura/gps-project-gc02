package com.example.gc02.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gc02.model.User
import com.example.gc02.model.Article
import com.example.gc02.Database.UserDao

@Database(entities = [User::class, Article::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun articleDao(): ArticleDao

    companion object {
        private var INSTANCE: BaseDatos? = null

        fun getInstance(context: Context): BaseDatos? {
            if (INSTANCE == null) {
                synchronized(BaseDatos::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        BaseDatos::class.java, "BaseDatos.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}