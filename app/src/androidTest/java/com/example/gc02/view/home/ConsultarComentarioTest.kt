package com.example.gc02.view.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.gc02.database.BaseDatos
import com.example.gc02.database.UserDao
import com.example.gc02.model.User
import com.example.gc02.view.LoginActivity
import com.example.gc02.R
import com.example.gc02.UnitTests.CommentTest
import com.example.gc02.database.ComentarioDao
import com.example.gc02.model.Comentario
import kotlinx.coroutines.runBlocking
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ConsultarComentarioTest {
/*
    @Rule
    @JvmField
    var lActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    private lateinit var volatileBD: BaseDatos
    private lateinit var userDAO: UserDao
    private lateinit var comentarioDao: ComentarioDao

    @Before
    fun setUpUser() {
        var context: Context = ApplicationProvider.getApplicationContext<Context>()
        volatileBD = BaseDatos.getInstance(context)!!

        userDAO = volatileBD.userDao()
        comentarioDao = volatileBD.comentarioDao()

        //SE CREA UN USER
        val user = User(99, "user1", "user@user.com", "user123")

        //SE CREA UN COMENTARIO
        val comentario1 = Comentario(1,user.name,"Buen vendedor")
        val comentario2 = Comentario(2,user.name,"Vendedor fiable")


        //SE INSERTA EN LA BASE DE DATOS
        runBlocking {
            userDAO.insert(user)
            comentarioDao.insert(comentario1)
            comentarioDao.insert(comentario2)
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    @Test
    fun ConsultarComentarioTest() {
        onView(
            withId(R.id.loginlayout)
        )
            .check(matches(isDisplayed()))

        val appCompatEditText = onView(
            allOf(
                withId(R.id.username),
                childAtPosition(
                    allOf(
                        withId(R.id.loginlayout), isDisplayed()
                    ),
                    1
                )
            )
        )
        appCompatEditText.perform(replaceText("user1"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.password),
                childAtPosition(
                    allOf(
                        withId(R.id.loginlayout), isDisplayed()
                    ),
                    2
                )
            )
        )
        appCompatEditText2.perform(replaceText("user123"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.loginbutton), withText("Iniciar Sesion"),
                childAtPosition(
                    allOf(
                        withId(R.id.loginlayout), isDisplayed()
                    ),
                    4
                )
            )
        )
        materialButton3.perform(click())

        Thread.sleep(2000)

        val homeLayout = onView(
            allOf(
                withId(R.id.page_home),
                isDisplayed()
            )
        )
        homeLayout.check(matches(isDisplayed()))
        // Realiza clic en el botón de navegación que lleva al fragmento deseado.
        onView(withId(R.id.page_perfil)).perform(click())

        // Verifica que el fragmento al que navegaste esté en la pantalla.
        onView(withId(R.id.misProductos)).check(matches(isDisplayed()))


        onView(
            allOf(
                withText("Comentarios"),
                isDescendantOfA(withId(R.id.tabLayout))
            )
        ).perform(click())

        val recyclerView = onView(allOf(
            withId(R.id.layoutComentarios),
            isDescendantOfA(withId(R.id.consultarPerfil))
        ))
        recyclerView.check(matches(isDisplayed()))
        recyclerView.perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
            hasDescendant(withText("Buen vendedor")), click()))
    }




        @After
        fun closeVolatileDB() {
            volatileBD.close()
        }

 */
}
