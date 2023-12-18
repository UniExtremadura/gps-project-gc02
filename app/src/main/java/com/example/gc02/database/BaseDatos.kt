package com.example.gc02.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gc02.model.Article
import com.example.gc02.model.Comentario
import com.example.gc02.model.User
import com.example.gc02.model.UserShopCrossRef

@Database(entities = [User::class, Article::class, Comentario::class, UserShopCrossRef::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun articleDao(): ArticleDao

    abstract fun comentarioDao() : ComentarioDao

    companion object {
        private var INSTANCE: BaseDatos? = null

        fun getInstance(context: Context): BaseDatos? {
            if (INSTANCE == null) {
                synchronized(BaseDatos::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        BaseDatos::class.java, "BaseDatos.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}