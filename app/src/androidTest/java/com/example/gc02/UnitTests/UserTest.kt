package com.example.gc02.UnitTests

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gc02.database.BaseDatos
import com.example.gc02.database.UserDao
import com.example.gc02.model.User
import com.example.gc02.view.CrearPerfilActivity
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import androidx.lifecycle.lifecycleScope
import androidx.room.Room


@LargeTest
@RunWith(AndroidJUnit4::class)
class UserTest {

    private lateinit var userDao: UserDao
    private lateinit var db: BaseDatos

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, BaseDatos::class.java)
            .build()
        userDao = db.userDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeUserAndReadInDataBase() {
        val user1: User = createUser()
        val user2: User = createUser()

        val id1 = userDao.insert1(user1)
        val id2 = userDao.insert1(user2)

        val listUsers: List<User> = userDao.getAll()

        Assert.assertTrue(listUsers[0].userId == id1)
        Assert.assertTrue(listUsers[1].userId == id2)
        Assert.assertFalse(listUsers[0].userId == listUsers[1].userId)
    }

    companion object {
        fun createUser(): User {
            return User(null, "user1", "user1@gmail.com", "user1")
        }
    }
}