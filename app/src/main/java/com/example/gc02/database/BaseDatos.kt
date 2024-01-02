package com.example.gc02.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gc02.model.Article
import com.example.gc02.model.Comentario
import com.example.gc02.model.User
import com.example.gc02.model.UserShopCrossRef
import com.example.gc02.model.Valuation

@Database(entities = [User::class, Article::class, Comentario::class, UserShopCrossRef::class, Valuation::class], version = 2)
abstract class BaseDatos : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun articleDao(): ArticleDao

    abstract fun valuationDao(): ValuationDao

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