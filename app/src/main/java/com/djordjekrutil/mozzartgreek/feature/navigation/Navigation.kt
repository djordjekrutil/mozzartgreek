package com.djordjekrutil.mozzartgreek.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.djordjekrutil.mozzartgreek.feature.model.Game
import com.djordjekrutil.mozzartgreek.feature.model.GameArgType
import com.djordjekrutil.mozzartgreek.feature.view.ActiveGamesScreen
import com.djordjekrutil.mozzartgreek.feature.view.DrawingScreen
import com.djordjekrutil.mozzartgreek.feature.view.DrawsResultsScreen
import com.djordjekrutil.mozzartgreek.feature.view.NumbersBoardScreen
import com.djordjekrutil.mozzartgreek.feature.viewmodel.ActiveGamesViewModel
import com.djordjekrutil.mozzartgreek.feature.viewmodel.NumbersBoardViewModel
import com.djordjekrutil.mozzartgreek.feature.viewmodel.ResultDrawsViewModel
import com.google.gson.Gson

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(navController, startDestination = NavigationItem.Draws.route) {
        composable(NavigationItem.Draws.route) {
            val activeGamesViewModel: ActiveGamesViewModel = hiltViewModel()
            ActiveGamesScreen(activeGamesViewModel, navController)
        }
        composable(NavigationItem.LiveDrawing.route) {
            DrawingScreen("https://mozzartbet.com/sr/lotto-animation/26#")
        }
        composable(NavigationItem.DrawsResults.route) {
            val resultDrawsViewModel: ResultDrawsViewModel = hiltViewModel()
            DrawsResultsScreen(resultDrawsViewModel)
        }
        composable(
            NavigationItem.NumbersBoard.route,
            arguments = listOf(navArgument("game") {
//                type = GameArgType()
            })
        )
        { backStackEntry ->
            val numbersBoardViewModel: NumbersBoardViewModel = hiltViewModel()
            val game = backStackEntry.arguments?.getString("game")?.let { Gson().fromJson(it, Game::class.java) }
            NumbersBoardScreen(numbersBoardViewModel, game)
        }
    }
}