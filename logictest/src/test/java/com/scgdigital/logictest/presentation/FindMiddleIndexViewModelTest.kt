package com.scgdigital.logictest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.logictest.presentation.analytics.LogicTestAnalytics
import com.scgdigital.logictest.presentation.findmiddleindex.FindMiddleIndexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class FindMiddleIndexViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: FindMiddleIndexViewModel
    private lateinit var analytics: LogicTestAnalytics
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        analytics = mock()
        coroutineDispatcherProvider = mock()

        viewModel = FindMiddleIndexViewModel(coroutineDispatcherProvider, false, analytics)
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
    fun test_onFindMiddleIndexClick_found() {
        val input = "1,2,3,4,3,2,1"
        viewModel.onFindMiddleIndexClick(input)

        val state = viewModel.currentState
        assertThat(state.index).isEqualTo(3)
    }

    @Test
    fun test_onFindMiddleIndexClick_notFound() {
        val input = "1,2,3,4"
        viewModel.onFindMiddleIndexClick(input)

        val state = viewModel.currentState
        assertThat(state.index).isEqualTo(-1)
    }

}
