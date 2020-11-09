package com.bins.tryz

import android.content.Intent
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.bins.tryz.server.MockServer
import com.doga.microbloggingplatformsymphony.R
import com.doga.microbloggingplatformsymphony.TestApp
import com.doga.microbloggingplatformsymphony.ui.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val mockServer =  MockWebServer()

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        mockServer.start(8080)
    }

    @Mock
    private val app: TestApp? = null
    private val mockWebServer: MockWebServer? = null

    @After
    fun tearDown() = mockServer.shutdown()

    @Test
    fun successCase() {
        mockServer.dispatcher = MockServer.ResponseDispatcher()
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, MainActivity::class.java)
        rule.launchActivity(intent)

        SystemClock.sleep(2000)
        //  error view - visible on success response
        Espresso.onView(ViewMatchers.withId(R.id.rvAuthors))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(withId(R.id.rvAuthors))
            .check(matches((hasItemAtPosition(0, withText("Myrtie_Heller51")))))

    }

    @Test
    fun failureCase() {
        mockServer.dispatcher = MockServer.ErrorDispatcher()
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, MainActivity::class.java)
        rule.launchActivity(intent)
    }

    fun hasItemAtPosition(position: Int, matcher: Matcher<View>) : Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description?) {
                description?.appendText("has item at position $position : ")
                matcher.describeTo(description)
            }

            override fun matchesSafely(item: RecyclerView?): Boolean {
                val viewHolder = item?.findViewHolderForAdapterPosition(position)
                return matcher.matches(viewHolder?.itemView)
            }
        }
    }

}