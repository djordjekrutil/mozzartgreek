package com.djordjekrutil.mozzartgreek.feature.navigation

sealed class NavigationItem(var route: String){
    data object Draws : NavigationItem("draws")
    data object NumbersBoard : NavigationItem("numbersForDraws/{game}")
    data object LiveDrawing : NavigationItem("LiveDrawing")
    data object DrawsResults : NavigationItem("DrawsResults")
}