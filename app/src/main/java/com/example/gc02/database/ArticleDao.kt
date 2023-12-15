package com.example.gc02.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.gc02.model.Article
import com.example.gc02.model.UserShopCrossRef
import com.example.gc02.model.UserwithShops

//import es.unex.giiis.asee.tiviclone.data.model.UserShowCrossRef
//import es.unex.giiis.asee.tiviclone.data.model.UserWithShows

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article WHERE articleId = :id")
    suspend fun findById(id: Int): Article

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Delete
    suspend fun delete(article: Article)
    @Transaction
    @Query("SELECT * FROM User where userId = :userId")
    suspend fun getUserWithShops(userId: Long): UserwithShops

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserShop(crossRef: UserShopCrossRef)

    @Transaction
    suspend fun insertAndRelate(article: Article, userId: Long) {
        insert(article)
        insertUserShop(UserShopCrossRef(userId, article.articleId))
    }
    @Update
    suspend fun updateProduct(article: Article)

    @Transaction
    @Query("SELECT * FROM Article")
    suspend fun getAll(): List<Article>
}