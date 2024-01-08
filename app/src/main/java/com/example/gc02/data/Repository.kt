package com.example.gc02.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.gc02.api.ShopApi
import com.example.gc02.api.APIError
import com.example.gc02.data.api.Shop
import com.example.gc02.database.UserDao
import com.example.gc02.database.BaseDatos
import com.example.gc02.database.ValuationDao
import com.example.gc02.model.Article
import com.example.gc02.model.Comentario
import com.example.gc02.model.User
import com.example.gc02.model.UserShopCrossRef
import com.example.gc02.model.UserwithShops
import com.example.gc02.model.Valuation

class Repository(
    private val baseDatos: BaseDatos,
    private val networkService: ShopApi
) {
    private val articleDao = baseDatos.articleDao()
    private val userDao = baseDatos.userDao()
    private val valuationDao = baseDatos.valuationDao()
    private val commentDao = baseDatos.comentarioDao()
    private var lastUpdateTimeMillis: Long = 0L

    val shops = articleDao.getArticles()

    private val userFilter = MutableLiveData<Long>()

    val shopsUser: LiveData<List<Article>> =
        userFilter.switchMap{ userid -> articleDao.getShopsByUser(userid) }

    val shopsFavUser: LiveData<UserwithShops> =
        userFilter.switchMap{ userid -> articleDao.getUserWithShops2(userid) }

    val valuationsUser: LiveData<List<Valuation>> =
        userFilter.switchMap{ userid -> valuationDao.getValuationsByUser(userid)}

    private val articleFilter = MutableLiveData<Long>()
    val commentsArticle: LiveData<List<Comentario>> =
        articleFilter.switchMap { articleid -> commentDao.getCommentByArticleAndUser(articleid,articleDao.findById1(articleid).userId!!) }

    fun setUserid(userid: Long) {
        userFilter.value = userid
    }
    /**
     * Update the shows cache.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    suspend fun tryUpdateRecentShopsCache() {
        if (shouldUpdateShowsCache()) fetchRecentShops()
    }

    /**
     * Fetch a new list of shows from the network, and append them to [ShowDao]
     */
    private suspend fun fetchRecentShops() {
        try {
            val shops =  networkService.getCategories().map(Shop::toShop)
            articleDao.insertAll(shops)
            lastUpdateTimeMillis = System.currentTimeMillis()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    /**
     * Returns true if we should make a network request.
     */
    private suspend fun shouldUpdateShowsCache(): Boolean {
        val lastFetchTimeMillis = lastUpdateTimeMillis
        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || articleDao.getNumberOfArticles() == 0L
    }

    suspend fun fetchShopDetail(shopId: Long): Shop {
        var shop = Shop()
        try {
            shop = networkService.getShopDetail(shopId).shop ?: Shop()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
        return shop
    }
    suspend fun findByNameUser(name:String): User{
        return userDao.findByName(name)
    }
    suspend fun insert(user:User): Long {
        return userDao.insert(user)
    }

    suspend fun deleteArticles(id: Long) {
        return articleDao.deleteArticleByUserId(id)
    }

    suspend fun  updateUser(user: User): Unit{
        return userDao.update(user)
    }
    suspend fun deleteUser(user: User){
        return userDao.delete(user)
    }

    suspend fun getUserWithShopsFavorites(userId: Long): UserwithShops {
        return articleDao.getUserWithShops(userId)
    }


    suspend fun getShop(shopId: Long): Article {
        return articleDao.findById(shopId.toInt())
    }
    suspend fun insertShopFavorite(article: Article, userId: Long) {
        articleDao.insert(article)
        articleDao.insertUserShow(UserShopCrossRef(userId, article.articleId!!))
    }

    suspend fun deleteShopFavorite(article: Article, userId: Long) {
        articleDao.deleteUserShow(UserShopCrossRef(userId, article.articleId!!))
        articleDao.insert(article)
    }

    suspend fun insert(article: Article): Long {
        return articleDao.insert(article)
    }
    suspend fun deleteArticulo(article: Article){
        return articleDao.delete(article)
    }
    suspend fun updateArticulo(article: Article){
        return articleDao.updateProduct(article)
    }
    suspend fun findById(id: Int): Article {
        return articleDao.findById(id)
    }

    suspend fun getAllByUser(userId: Long?): List<Article> {
        return articleDao.getAllByUser(userId)
    }

    suspend fun insertValuation(valuation : Valuation): Long?{
        return valuationDao.insert(valuation)
    }

    suspend fun findByIdValuation(id: Int): Valuation {
        return valuationDao.findById(id)
    }

    suspend fun getAllByUserValuation(userId: Long?): List<Valuation> {
        return valuationDao.getAllByUser(userId)
    }

    suspend fun delete(valuation: Valuation) {
        return valuationDao.delete(valuation)
    }

    suspend fun insertComment(comentario: Comentario): Long? {
        return commentDao.insert(comentario)
    }

    suspend fun deleteComment(comentario: Comentario){
        return commentDao.delete(comentario)
    }

    suspend fun getAllByArticleComment (articleid: Long,userId: Long): List<Comentario> {
        return commentDao.obtenerComentariosByArticleAndUser(articleid,userId)
    }

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 30000

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(
            baseDatos: BaseDatos,
            shopAPI: ShopApi
        ): Repository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(baseDatos, shopAPI).also { INSTANCE = it }
            }
        }
    }
}