package com.scgdigital.news.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.news.presentation.analytics.NewsAnalytics
import com.scgdigital.news.presentation.detail.NewsDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class NewsDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: NewsDetailViewModel
    private lateinit var analytics: NewsAnalytics
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        analytics = mock()
        coroutineDispatcherProvider = mock()

        viewModel = NewsDetailViewModel(coroutineDispatcherProvider, false, analytics)
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
    fun test_convertDateTime() {
        val dateTimeString = "2023-05-19T10:15:30Z"
        val expectedFormattedDateTime = "May 19, 17:15"

        val formattedDateTime = viewModel.convertDateTime(dateTimeString)
        assertThat(formattedDateTime).isEqualTo(expectedFormattedDateTime)
    }
}