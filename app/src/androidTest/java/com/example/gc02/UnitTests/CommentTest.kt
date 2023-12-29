package com.example.gc02.UnitTests

import androidx.room.Room
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gc02.database.BaseDatos
import com.example.gc02.database.ComentarioDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.gc02.model.Comentario


@LargeTest
@RunWith(AndroidJUnit4::class)
class CommentTest {
/*
    private lateinit var comentarioDao: ComentarioDao
    private lateinit var db: BaseDatos

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, BaseDatos::class.java)
            .build()
        comentarioDao = db.comentarioDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeCommentAndReadInDataBase() {
        val comentario1: Comentario = createComentario()
        val comentario2: Comentario = createComentario()

        val id1 = comentarioDao.insert1(comentario1)
        val id2 = comentarioDao.insert1(comentario2)

        val listComments: List<Comentario> = comentarioDao.obtenerComentariosPrueba()

        Assert.assertTrue(listComments[0].commentId == id1)
        Assert.assertTrue(listComments[1].commentId == id2)
        Assert.assertTrue(listComments[0].comentario == "Comentario sobre el producto a comprar.")
        Assert.assertTrue(listComments[1].comentario == "Comentario sobre el producto a comprar.")
        Assert.assertFalse(listComments[0].commentId == listComments[1].commentId)
    }

    companion object {
        fun createComentario(): Comentario {
            return Comentario(
                null,
                "Usuario",
                "Comentario sobre el producto a comprar."
            )
        }
    }

 */
}