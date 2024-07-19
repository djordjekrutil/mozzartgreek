package com.djordjekrutil.mozzartgreek.feature.utils

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions

fun navigateAvoidingDuplicate(navController: NavHostController, route: String) {
    val currentDestination = navController.currentBackStackEntry?.destination
    if (currentDestination?.route != route) {
        navController.navigate(route, navOptions {
            launchSingleTop = true
            restoreState = true
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        })
    }
}