package com.example.gc02.data

import androidx.room.Transaction
import com.example.gc02.api.ShopApi
import com.example.gc02.api.APIError
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.api.Shop
import com.example.gc02.database.UserDao
import com.example.gc02.database.ArticleDao
import com.example.gc02.model.Article
import com.example.gc02.model.UserShopCrossRef
import com.example.gc02.model.UserwithShops

class Repository private constructor(
    private val userDao: UserDao,
    private val articleDao: ArticleDao,
    private val networkService: ShopApi
) {
    private var lastUpdateTimeMillis: Long = 0L

    val shows = articleDao.getArticles()

    /**
     * Update the shows cache.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    suspend fun tryUpdateRecentShowsCache() {
        if (shouldUpdateShowsCache()) fetchRecentShows()
    }

    /**
     * Fetch a new list of shows from the network, and append them to [ShowDao]
     */
    private suspend fun fetchRecentShows() {
        try {
            val shops = listOf<Shop>().map { it.toShop()}
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

    suspend fun getUserWithShopsFavorites(userId: Long): UserwithShops {
        return articleDao.getUserWithShops(userId)
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

    suspend fun findById(id: Int): Article {
        return articleDao.findById(id)
    }

    suspend fun getAllByUser(userId: Long?): List<Article> {
        return articleDao.getAllByUser(userId)
    }

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 30000

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(
            userDao: UserDao,
            articleDao: ArticleDao,
            shopAPI: ShopApi
        ): Repository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(userDao, articleDao, shopAPI).also { INSTANCE = it }
            }
        }
    }
}