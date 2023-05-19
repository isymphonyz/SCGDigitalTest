package com.scgdigital.test.navigation

import com.scgdigital.common.domain.SCGDigitalRoute
import com.scgdigital.resource.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object LOGIC_TEST : NavigationItem(SCGDigitalRoute.LOGIC_TEST, R.drawable.ic_code, SCGDigitalRoute.LOGIC_TEST)
    object CODING_TEST : NavigationItem(SCGDigitalRoute.CODING_TEST, R.drawable.ic_news, SCGDigitalRoute.CODING_TEST)
}