package com.example.gc02.UnitTests

import androidx.room.Room
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gc02.database.BaseDatos
import com.example.gc02.database.ArticleDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.gc02.model.Article


@LargeTest
@RunWith(AndroidJUnit4::class)
class ArticleTest {

    private lateinit var articleDao: ArticleDao
    private lateinit var db: BaseDatos

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, BaseDatos::class.java)
            .build()
        articleDao = db.articleDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeArticleAndReadInDataBase() {
        val article1: Article = createArticle()
        val article2: Article = createArticle()

        val id1 = articleDao.insert1(article1)
        val id2 = articleDao.insert1(article2)

        val listArticles: List<Article> = articleDao.getAllPrueba()

        Assert.assertTrue(listArticles[0].articleId == id1)
        Assert.assertTrue(listArticles[1].articleId == id2)
        Assert.assertTrue(listArticles[1].userId?.toDouble() == 1.0 && listArticles[1].userId?.toDouble() == 1.0)
        Assert.assertFalse(listArticles[0].articleId == listArticles[1].articleId)
    }

    @Test
    fun writeArticleAndAddToFavorite() {
        val article1: Article = createArticle()
        val article2: Article = createArticle()

        val id1 = articleDao.insert1(article1)
        val id2 = articleDao.insert1(article2)

        article1.isFavorite = true
        article2.isFavorite = true

        articleDao.insertAndRelatePrueba(article1, 2)
        articleDao.insertAndRelatePrueba(article2, 3)

        Assert.assertTrue(article1.isFavorite)
        Assert.assertTrue(article2.isFavorite)

        article1.isFavorite = false
        article2.isFavorite = false

        Assert.assertFalse(article1.isFavorite&&article2.isFavorite)
    }
    companion object {
        fun createArticle(): Article {
            return Article(
                null,
                "Titulo del producto",
                "Descripción detallada del producto a añadir con más de 20 letras.",
                20.0,
                null,
                null,
                false,
                1
            )
        }
    }
}
