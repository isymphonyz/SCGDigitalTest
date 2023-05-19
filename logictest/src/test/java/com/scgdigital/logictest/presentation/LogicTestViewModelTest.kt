package com.scgdigital.logictest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.domain.SCGDigitalRoute
import com.scgdigital.logictest.LogicTestViewEvent
import com.scgdigital.logictest.LogicTestViewModel
import com.scgdigital.logictest.presentation.analytics.LogicTestAnalytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class LogicTestViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: LogicTestViewModel
    private lateinit var analytics: LogicTestAnalytics
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        analytics = mock()
        coroutineDispatcherProvider = mock()

        viewModel = LogicTestViewModel(coroutineDispatcherProvider, false, analytics)
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
    fun test_onFindMiddleIndexClick() {
        viewModel.onFindMiddleIndexClick()

        val event = viewModel.event.value
        assertThat(event).isInstanceOf(LogicTestViewEvent.Navigate::class.java)

        val navigateEvent = event as LogicTestViewEvent.Navigate
        assertThat(navigateEvent.route).isEqualTo(SCGDigitalRoute.FIND_MIDDLE_INDEX)
    }

    @Test
    fun test_onPalindromeClick() {
        viewModel.onPalindromeClick()

        val event = viewModel.event.value
        assertThat(event).isInstanceOf(LogicTestViewEvent.Navigate::class.java)

        val navigateEvent = event as LogicTestViewEvent.Navigate
        assertThat(navigateEvent.route).isEqualTo(SCGDigitalRoute.PALINDROME)
    }
}
