package com.example.gc02.view;

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.gc02.R
import com.example.gc02.database.ArticleDao
import com.example.gc02.database.BaseDatos
import com.example.gc02.database.UserDao
import com.example.gc02.model.Article
import com.example.gc02.model.User
import kotlinx.coroutines.runBlocking
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CrearPerfilActivityTest {

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

    }

    @Test
    fun registerUserTest(){
        onView(
            withId(R.id.loginlayout)
        )
            .check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.registerbutton), withText("Registrarse"),
                childAtPosition(
                    allOf(
                        withId(R.id.loginlayout), isDisplayed()
                    ),
                    3
                )
            )
        ).perform(click())
        Thread.sleep(2000)
        onView(
            withId(R.id.crearPerfil))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.et_username),
                childAtPosition(
                    allOf(
                        withId(R.id.crearPerfil), isDisplayed()
                    ),
                    2
                )
            )
        ).perform(replaceText("user1"))

        onView(
            allOf(
                withId(R.id.et_email),
                childAtPosition(
                    allOf(
                        withId(R.id.crearPerfil), isDisplayed()
                    ),
                    4
                )
            )
        ).perform(replaceText("user@user.com"))

        onView(
            allOf(
                withId(R.id.et_password),
                childAtPosition(
                    allOf(
                        withId(R.id.crearPerfil), isDisplayed()
                    ),
                    6
                )
            )
        ).perform(replaceText("user123"))


        onView(
            allOf(
                withId(R.id.et_rePassword),
                childAtPosition(
                    allOf(
                        withId(R.id.crearPerfil), isDisplayed()
                    ),
                    9
                )
            )
        ).perform(replaceText("user123"))

        closeSoftKeyboard()

        onView(
            allOf(
                withId(R.id.bt_register),
                childAtPosition(
                    allOf(
                        withId(R.id.crearPerfil), isDisplayed()
                    ),
                    7
                )
            )
        ).perform(click())

        onView(withId(R.id.loginlayout)).check(matches(isDisplayed()))

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
    fun closeVolatileDB(){
        volatileBD.close()
    }

}
