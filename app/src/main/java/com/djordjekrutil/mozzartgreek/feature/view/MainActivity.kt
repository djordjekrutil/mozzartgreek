package com.djordjekrutil.mozzartgreek.feature.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.djordjekrutil.mozzartgreek.R
import com.djordjekrutil.mozzartgreek.core.platform.BaseActivity
import com.djordjekrutil.mozzartgreek.feature.navigation.Navigation
import com.djordjekrutil.mozzartgreek.feature.navigation.NavigationItem
import com.djordjekrutil.mozzartgreek.feature.utils.navigateAvoidingDuplicate
import com.djordjekrutil.mozzartgreek.ui.theme.MozzartGreekTheme
import com.djordjekrutil.mozzartgreek.ui.theme.primary
import com.djordjekrutil.mozzartgreek.ui.theme.secondary
import com.djordjekrutil.mozzartgreek.ui.theme.tertiary
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity @Inject constructor() : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MozzartGreekTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = secondary
                ) {
                    MainScreen(navController)
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            selectedTabIndex = when (destination.route) {
                NavigationItem.Draws.route -> 0
                NavigationItem.LiveDrawing.route -> 1
                NavigationItem.DrawsResults.route -> 2
                else -> 0
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
        )
        {
            Navigation(navController = navController)
        }
        CustomTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
        ) { index ->
            val route = when (index) {
                0 -> NavigationItem.Draws.route
                1 -> NavigationItem.LiveDrawing.route
                2 -> NavigationItem.DrawsResults.route
                else -> NavigationItem.Draws.route
            }
            navigateAvoidingDuplicate(navController, route)
            selectedTabIndex = index
        }
    }
}

@Composable
fun CustomTabRow(selectedTabIndex: Int, modifier: Modifier, onTabSelected: (Int) -> Unit) {
    val tabTitles = listOf(
        stringResource(R.string.izvlacenja),
        stringResource(R.string.uzivo_izvlacenje),
        stringResource(R.string.rezultati)
    )
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = primary,
        contentColor = tertiary,
        indicator = { tabPositions ->
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = secondary
            )
        }
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                selectedContentColor = secondary,
                unselectedContentColor = secondary.copy(alpha = 0.5f),
            )
        }
    }
}
