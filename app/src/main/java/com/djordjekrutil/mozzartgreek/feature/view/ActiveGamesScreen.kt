package com.djordjekrutil.mozzartgreek.feature.view

import android.os.CountDownTimer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djordjekrutil.mozzartgreek.R
import com.djordjekrutil.mozzartgreek.feature.model.Game
import com.djordjekrutil.mozzartgreek.feature.utils.convertMillisToDateTimeFormat
import com.djordjekrutil.mozzartgreek.feature.utils.convertMillisToHHMMSS
import com.djordjekrutil.mozzartgreek.feature.viewmodel.GamesState
import com.djordjekrutil.mozzartgreek.feature.viewmodel.ActiveGamesViewModel
import com.djordjekrutil.mozzartgreek.ui.ErrorMessage
import com.djordjekrutil.mozzartgreek.ui.ShowToast
import com.djordjekrutil.mozzartgreek.ui.theme.primary
import com.djordjekrutil.mozzartgreek.ui.theme.secondary
import com.djordjekrutil.mozzartgreek.ui.theme.secondary70
import com.google.gson.Gson
import java.time.Duration
import java.time.Instant

@Composable
fun ActiveGamesScreen(
    viewModel: ActiveGamesViewModel,
    navController: NavController
) {
    when (val state = viewModel.gamesStateFlow.value) {
        is GamesState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is GamesState.Error -> {
            ErrorMessage {
                viewModel.loadGames()
            }
        }

        is GamesState.Loaded -> {
            ShowActiveGames(
                games = state.games, navController = navController
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowActiveGames(games: List<Game>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(secondary)
    ) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primary)
            ) {
                Text(
                    text = stringResource(R.string.vreme_izvlacenja),
                    color = secondary,
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = stringResource(R.string.preostalo_za_uplatu),
                    color = secondary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }
        items(games) { game ->
            GameItem(endTime = Instant.ofEpochMilli(game.drawTime), onClick = {
                val route = "numbersForDraws/${Gson().toJson(game)}"
                navController.navigate(route)
            })
            HorizontalDivider(thickness = 1.dp, color = secondary70)
        }
    }
}

@Composable
fun GameItem(endTime: Instant, onClick: () -> Unit) {
    val textRight = stringResource(R.string.zatvoreno)
    val currentTime by rememberUpdatedState(newValue = Instant.now())
    val endTimeState by rememberUpdatedState(newValue = endTime)

    val timeLeft = remember { mutableStateOf(Duration.between(currentTime, endTimeState)) }
    val showToast = remember { mutableStateOf(false) }

    if (showToast.value) {
        ShowToast(message = stringResource(R.string.isteklo_je_vreme_za_uplatu))
        LaunchedEffect(showToast.value) {
            showToast.value = false
        }
    }

    LaunchedEffect(null) {
        object : CountDownTimer(timeLeft.value.toMillis(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft.value = Duration.between(Instant.now(), endTimeState)
            }

            override fun onFinish() {
                this.cancel()
            }
        }.start()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(
                if (timeLeft.value.seconds > 0) Modifier.clickable(onClick = onClick)
                else Modifier.clickable { showToast.value = true }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = convertMillisToDateTimeFormat(endTime.toEpochMilli()),
            color = primary
        )
        Text(
            text = if (timeLeft.value.seconds > 0) convertMillisToHHMMSS(timeLeft.value.toMillis()) else textRight,
            color = if (timeLeft.value.seconds > 60) primary else Color.Red
        )
    }
}
