package com.scgdigital.test

import android.app.Activity
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.scgdigital.common.domain.SCGDigitalRoute
import com.scgdigital.logictest.presentation.LogicTestContainer
import com.scgdigital.logictest.presentation.findmiddleindex.FindMiddleIndexContainer
import com.scgdigital.logictest.presentation.palindrome.PalindromeContainer
import com.scgdigital.news.data.ArticleParamType
import com.scgdigital.news.domain.model.Article
import com.scgdigital.news.presentation.NewsContainer
import com.scgdigital.news.presentation.detail.NewsDetailContainer
import com.squareup.moshi.Moshi

fun NavGraphBuilder.mainScreenGraph(
    navController: NavHostController,
    activity: Activity?
) {

    composable(
        route = SCGDigitalRoute.LOGIC_TEST
    ) {
        LogicTestContainer(
            isSessionTimeout = false,
            data = null,
        ) {
                navEvent ->
            when (navEvent.route) {
                SCGDigitalRoute.FIND_MIDDLE_INDEX -> {
                    navController.navigate(navEvent.route)
                }
                SCGDigitalRoute.PALINDROME -> {
                    navController.navigate(navEvent.route)
                }
                else -> {
                    navController.navigate(navEvent.route) {
                        if (navEvent.clearBackStack) {
                            activity!!.finish()
                        }
                    }
                }
            }
        }
    }

    composable(
        route = SCGDigitalRoute.FIND_MIDDLE_INDEX
    ) {
        FindMiddleIndexContainer(
            isSessionTimeout = false,
            data = null,
        ) {
                navEvent ->
            if (navEvent.route == SCGDigitalRoute.FIND_MIDDLE_INDEX) {
                navController.navigate(navEvent.route)
            } else {
                navController.navigate(navEvent.route) {
                    if (navEvent.clearBackStack) {
                        activity!!.finish()
                    }
                }
            }
        }
    }

    composable(
        route = SCGDigitalRoute.PALINDROME
    ) {
        PalindromeContainer(
            isSessionTimeout = false,
            data = null,
        ) {
                navEvent ->
            if (navEvent.route == SCGDigitalRoute.PALINDROME) {
                navController.navigate(navEvent.route)
            } else {
                navController.navigate(navEvent.route) {
                    if (navEvent.clearBackStack) {
                        activity!!.finish()
                    }
                }
            }
        }
    }

    composable(
        route = SCGDigitalRoute.CODING_TEST
    ) {
        NewsContainer(
            isSessionTimeout = false,
            data = null,
        ) {
                navEvent ->
            when (navEvent.route) {
                SCGDigitalRoute.NEWS_DETAIL -> {
                    navController.navigate(navEvent.route)
                }
                else -> {
                    navController.navigate(navEvent.route) {
                        if (navEvent.clearBackStack) {
                            activity!!.finish()
                        }
                    }
                }
            }
        }
    }

    composable(
        route = SCGDigitalRoute.NEWS_DETAIL + "/{article}",
        arguments = listOf(
            navArgument(name = "article") {
                type = NavType.StringType
            },
        )
    ) {
        val data = it.arguments!!.getString("article")
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(Article::class.java).lenient()
        val article = jsonAdapter.fromJson(data)
        Log.d("iSymKeng", "article: ${article}")
        NewsDetailContainer(
            isSessionTimeout = false,
            article = article!!,
        ) {
                navEvent ->
            when (navEvent.route) {
                SCGDigitalRoute.FIND_MIDDLE_INDEX -> {
                    navController.navigate(navEvent.route)
                }
                SCGDigitalRoute.PALINDROME -> {
                    navController.navigate(navEvent.route)
                }
                else -> {
                    navController.navigate(navEvent.route) {
                        if (navEvent.clearBackStack) {
                            activity!!.finish()
                        }
                    }
                }
            }
        }
    }
}
