package com.mocknetworking


import ApiRepository
import ToastMatcher
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mocknetworking.util.Constants
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONObject
import org.junit.*
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private var mockWebServer: MockWebServer? = null

    private lateinit var api: ApiRepository


    @Before
    @Throws(Exception::class)
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer?.dispatcher = dispatcher
        mockWebServer?.start()
        mockWebServer?.url(Constants.BASE_URL)

        api = ApiRepository
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        mockWebServer!!.shutdown()
    }


    private val dispatcher: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            if (request.path == "/login") {
                val jsonObject = JSONObject()
                jsonObject.put("isSuccessful", true)
                jsonObject.put("username", "jassu")
                try {
                    return MockResponse().setResponseCode(200)
                        .setBody(jsonObject.toString())
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
            return MockResponse().setResponseCode(404)
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.mocknetworking", appContext.packageName)
    }

    @Test
    fun test_all_views_are_visible() {
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    fun test_username_is_required() {
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.username)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.login)).perform(click())
        onView(withText(R.string.error_username))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun test_username_should_contain_digit() {
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.username)).perform(typeText("jaspreet"), closeSoftKeyboard())
        onView(withId(R.id.login)).perform(click())
        onView(withText(R.string.username_digit_error))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun test_password_is_required() {
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.username)).perform(typeText("jaspreet123"), closeSoftKeyboard())
        onView(withId(R.id.login)).perform(click())
        onView(withText(R.string.error_password))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun test_password_should_contain_digit() {
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.username)).perform(typeText("jaspreet123"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.login)).perform(click())
        onView(withText(R.string.password_digit_error))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }


    @Test
    fun test_username_and_password_are_valid_show_loading_bar() {
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.password)).check(matches(isDisplayed()))
        onView(withId(R.id.username)).perform(typeText("jaspreet123"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.login)).perform(click())
        onView(withText("Loading...")).check(matches((isDisplayed())))
    }

    @Test
    fun test_call_api() {
        val param = LinkedHashMap<String, Any?>()
        param["username"] = "jassi123"
        param["password"] = "pass123"

        val result = api.getLoginParams(param)
        result.doOnSubscribe { }
            .subscribe({
                Assert.assertEquals(true, it.isSuccessful);
                Assert.assertEquals(param["username"], it.username);
            }, {

            })
    }


}