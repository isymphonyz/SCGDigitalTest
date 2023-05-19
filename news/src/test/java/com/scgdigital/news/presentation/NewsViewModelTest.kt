package com.scgdigital.news.presentation

/*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.news.domain.model.Article
import com.scgdigital.news.domain.usecase.NewsUseCase
import com.scgdigital.news.presentation.analytics.NewsAnalytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*


@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsUseCase: NewsUseCase
    private lateinit var analytics: NewsAnalytics
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        newsUseCase = mock()
        analytics = mock()
        coroutineDispatcherProvider = mock()

        // Return the test dispatcher for all coroutine dispatchers
        whenever(coroutineDispatcherProvider.io()).thenReturn(testDispatcher)
        whenever(coroutineDispatcherProvider.main()).thenReturn(testDispatcher)

        viewModel = NewsViewModel(coroutineDispatcherProvider, false, analytics, newsUseCase)
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
        val dateTime = "2023-05-18T00:06:05Z"
        val expectedFormattedDateTime = "May 18, 07:06"

        val formattedDateTime = viewModel.convertDateTime(dateTime)
        Assert.assertEquals(expectedFormattedDateTime, formattedDateTime)
    }

    @Test
    fun test_performQuery() {
        val initialArticles = listOf(
            Article(null, "David", "Robert", "Article 1", "Article 1"),
            Article(null, "John", "Doe", "Article 2", "Article 2"),
            Article(null, "Jane", "Smith", "Article 3", "Article 3"),

        )
        viewModel.setState { copy(articles = initialArticles) }

        val query = "Robert"
        viewModel.performQuery(query)

        val updatedArticles = viewModel.currentState.articles

        Assert.assertNotNull(updatedArticles)
        Assert.assertEquals("David", updatedArticles?.get(0)?.author)
    }
}*/

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.domain.SCGDigitalRoute
import com.scgdigital.common.domain.model.ApiError
import com.scgdigital.common.domain.usecase.UseCaseResponse
import com.scgdigital.news.domain.model.Article
import com.scgdigital.news.domain.model.GetNewsRequestModel
import com.scgdigital.news.domain.model.NewsResponseModel
import com.scgdigital.news.domain.usecase.NewsUseCase
import com.scgdigital.news.presentation.analytics.NewsAnalytics
import com.scgdigital.news.presentation.detail.NewsDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: NewsViewModel
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    @Mock
    private lateinit var analytics: NewsAnalytics

    @Mock
    private lateinit var newsUseCase: NewsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)

        coroutineDispatcherProvider = org.mockito.kotlin.mock()

        viewModel = NewsViewModel(
            coroutineDispatcherProvider,
            false,
            analytics,
            newsUseCase
        )
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
    fun test_performQuery_match() {
        val article1 = Article(title = "Article One")
        val article2 = Article(title = "Article Two")
        val article3 = Article(title = "Another Article")
        val query = "another"

        viewModel.originalArticles = listOf(article1, article2, article3)

        viewModel.performQuery(query)

        val state = viewModel.currentState
        assertThat(state.articles).containsExactly(article3)
    }

    @Test
    fun test_performQuery_notMatch() {
        val article1 = Article(title = "Article One")
        val article2 = Article(title = "Article Two")
        val article3 = Article(title = "Another Article")
        val query = "non-matching"

        viewModel.originalArticles = listOf(article1, article2, article3)

        viewModel.performQuery(query)

        val state = viewModel.currentState
        assertThat(state.articles).isNull()
    }

    @Test
    fun test_convertDateTime() {
        val dateTimeString = "2023-05-19T10:15:30Z"
        val expectedFormattedDateTime = "May 19, 17:15"

        val formattedDateTime = viewModel.convertDateTime(dateTimeString)
        assertThat(formattedDateTime).isEqualTo(expectedFormattedDateTime)
    }

    @Test
    fun test_onNewsClick() {
        val article = Article(title = "Test Article")
        val json = Uri.encode(Gson().toJson(article))

        viewModel.onNewsClick(article)

        val event = viewModel.event.value
        assertThat(event).isInstanceOf(NewsViewEvent.Navigate::class.java)

        val navigateEvent = event as NewsViewEvent.Navigate
        assertThat(navigateEvent.route).isEqualTo(SCGDigitalRoute.NEWS_DETAIL + "/${json}")
    }
}