package com.scgdigital.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.scgdigital.common.domain.SCGDigitalRoute
import com.scgdigital.common.presentation.component.SCGDigitalScaffold
import com.scgdigital.common.presentation.resource.SpaceSize
import com.scgdigital.common.presentation.theme.SCGDigitalAndroidComposeTheme
import com.scgdigital.resource.R
import com.scgdigital.test.navigation.Navigation
import com.scgdigital.test.navigation.NavigationItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SCGDigitalAndroidComposeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

    DisposableEffect(Unit) {
        onDispose {}
    }

    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    SCGDigitalScaffold(
        bottomBar = {
            BottomNavigationBar(navController, bottomBarState, currentRoute)
        },
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Navigation(navController, SCGDigitalRoute.LOGIC_TEST)
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    bottomBarState: MutableState<Boolean>,
    currentRoute: String?
) {
    val items = listOf(
        NavigationItem.LOGIC_TEST,
        NavigationItem.CODING_TEST
    )
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(SpaceSize.XXS.value)
                .clip(RoundedCornerShape(SpaceSize.L.value, SpaceSize.L.value, 0.dp, 0.dp)),
            backgroundColor = colorResource(id = R.color.dark_blue),
            contentColor = Color.White,
            elevation = SpaceSize.XXS.value
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    icon = { Icon(painterResource(id = item.icon), tint = Color.Unspecified, contentDescription = item.title) },
                    label = {
                        Text(
                            text = item.title,
                            color = colorResource(id = R.color.white)
                        )
                    },
                    selectedContentColor = Color(R.color.marigold),
                    unselectedContentColor = Color(R.color.greyish_brown),
                    alwaysShowLabel = true,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}