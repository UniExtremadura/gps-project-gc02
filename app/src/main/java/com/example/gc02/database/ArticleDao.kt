package com.example.gc02.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.gc02.model.Article
import com.example.gc02.model.Comentario
import com.example.gc02.model.User
import com.example.gc02.model.UserShopCrossRef
import com.example.gc02.model.UserwithShops

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article WHERE articleId = :id")
    suspend fun findById(id: Int): Article

    @Query("SELECT * FROM Article WHERE articleId = :id")
    fun findByIdPrueba(id: Int): Article

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert1(article: Article): Long

    @Delete
    suspend fun delete(article: Article)

    @Delete
    fun delete1(article: Article)

    @Transaction
    @Query("SELECT * FROM User where userId = :userId")
    suspend fun getUserWithShops(userId: Long): UserwithShops


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserShop(crossRef: UserShopCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserShopPrueba(crossRef: UserShopCrossRef)

    @Transaction
    suspend fun insertAndRelate(article: Article, userId: Long) {
        insert(article)
        article.articleId?.let { UserShopCrossRef(userId, it) }?.let { insertUserShop(it) }
    }

    @Transaction
    fun insertAndRelatePrueba(article: Article, userId: Long) {
        insert1(article)
        article.articleId?.let { UserShopCrossRef(userId, it) }?.let { insertUserShopPrueba(it) }
    }

    // Relaciona un articulo con un usuario (Favoritos)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserShow(crossRef: UserShopCrossRef)

    @Delete
    suspend fun deleteUserShow(userShopCrossRef: UserShopCrossRef)

    @Update
    suspend fun updateProduct(article: Article)

    @Update
    fun updateProduct1(article: Article)


    @Transaction
    @Query("SELECT * FROM Article")
    suspend fun getAll(): List<Article>

    @Transaction
    @Query("SELECT * FROM Article")
    fun getAllPrueba(): List<Article>

    @Transaction
    @Query("SELECT * FROM Article WHERE userId=:userId")
    suspend fun getAllByUser(userId: Long?): List<Article>

    @Query("SELECT * FROM Article WHERE title LIKE :first LIMIT 1")
    fun findByName1(first: String): Article

    @Query("SELECT * FROM Article WHERE articleId = :id")
    fun findById1(id: Long): Article

    @Query("SELECT * FROM Article")
    fun getArticles(): LiveData<List<Article>>
    @Query("SELECT count(*) FROM Article")
    suspend fun getNumberOfArticles(): Long
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(shows: List<Article>)

}
