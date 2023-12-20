package com.example.gc02.view.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.gc02.database.BaseDatos
import com.example.gc02.database.UserDao
import com.example.gc02.model.User
import com.example.gc02.view.LoginActivity
import com.example.gc02.R
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
class ConsultarPerfilFragmentTest {

    @Rule
    @JvmField
    var lActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    private lateinit var volatileBD: BaseDatos
    private lateinit var userDAO: UserDao

    @Before
    fun setUpUser(){
        var context: Context = ApplicationProvider.getApplicationContext<Context>()
        volatileBD = BaseDatos.getInstance(context)!!

        userDAO = volatileBD.userDao()

        //SE CREA UN USER
        val user = User(99,"user1","user@user.com","user123")

        //SE INSERTA EN LA BASE DE DATOS
        runBlocking {
            userDAO.insert(user)
        }
    }

    @Test
    fun informacionPerfilTest(){
        onView(
            withId(R.id.loginlayout))
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
        val textViewName = onView(
            allOf(
                withId(R.id.tvConsultarPerfil), withText("user1"),
                    childAtPosition(
                        allOf(
                            withId(R.id.layoutPerfil), isDisplayed()
                        ),
                        2
                ),isDisplayed()
            )
        )
        textViewName.check(matches(withText("user1")))
        val textViewEmail = onView(
            allOf(
                withId(R.id.tvCorreoPerfil), withText("user@user.com"),
                childAtPosition(
                    allOf(
                        withId(R.id.layoutPerfil), isDisplayed()
                    ),
                    3
                ),isDisplayed()
            )
        )
        textViewEmail.check(matches(withText("user@user.com")))
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
    @After
    fun closeVolatileDB() {
        volatileBD.close()
    }
}