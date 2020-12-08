package com.mocknetworking

import com.mocknetworking.ui.UserViewModel
import com.mocknetworking.util.isStringHasNumber
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        viewModel = UserViewModel()
    }

    @Test
    fun test_is_string_contain_number() {
        assertEquals(true, "jassi69".isStringHasNumber())
    }

    @Test
    fun test_string_not_contain_number() {
        assertEquals(false, "jassi".isStringHasNumber())
    }


    @Test
    fun test_welcome_msg() {
        val username = "jaspreet"
        viewModel.setUserName(username)
        assertEquals("Welcome Back $username", viewModel.getUserName())
    }


}