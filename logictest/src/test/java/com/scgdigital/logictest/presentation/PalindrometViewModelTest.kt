package com.scgdigital.logictest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.domain.SCGDigitalRoute
import com.scgdigital.logictest.LogicTestViewEvent
import com.scgdigital.logictest.LogicTestViewModel
import com.scgdigital.logictest.presentation.analytics.LogicTestAnalytics
import com.scgdigital.logictest.presentation.palindrome.PalindromeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class PalindromeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: PalindromeViewModel
    private lateinit var analytics: LogicTestAnalytics
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        analytics = mock()
        coroutineDispatcherProvider = mock()

        viewModel = PalindromeViewModel(coroutineDispatcherProvider, false, analytics)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun test_onEnterScreen() {
        viewModel.onEnterScreen()
        Assert.assertNotNull(analytics)
    }

    @Test
    fun test_onPalindromeClick_true() {
        val input = "level"
        viewModel.onPalindromeClick(input)

        val state = viewModel.currentState
        assertThat(state.isPalindrome).isTrue()
    }

    @Test
    fun test_onPalindromeClick_false() {
        val input = "hello"
        viewModel.onPalindromeClick(input)

        val state = viewModel.currentState
        assertThat(state.isPalindrome).isFalse()
    }

}
