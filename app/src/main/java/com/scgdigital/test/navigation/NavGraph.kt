package com.scgdigital.test.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.scgdigital.test.mainScreenGraph

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {

    val activity = LocalContext.current as? Activity

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        mainScreenGraph(navController, activity)
    }
}