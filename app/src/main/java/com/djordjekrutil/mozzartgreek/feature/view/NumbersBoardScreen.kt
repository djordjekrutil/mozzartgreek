package com.djordjekrutil.mozzartgreek.feature.view

import android.os.CountDownTimer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djordjekrutil.mozzartgreek.R
import com.djordjekrutil.mozzartgreek.feature.model.Game
import com.djordjekrutil.mozzartgreek.feature.utils.convertMillisToDateTimeFormat
import com.djordjekrutil.mozzartgreek.feature.utils.convertMillisToHHMMSS
import com.djordjekrutil.mozzartgreek.feature.utils.dpToSp
import com.djordjekrutil.mozzartgreek.feature.utils.randomColor
import com.djordjekrutil.mozzartgreek.feature.viewmodel.NumbersBoardViewModel
import com.djordjekrutil.mozzartgreek.ui.ShowToast
import com.djordjekrutil.mozzartgreek.ui.theme.primary
import com.djordjekrutil.mozzartgreek.ui.theme.secondary
import java.time.Duration
import java.time.Instant

@Composable
fun NumbersBoardScreen(viewModel: NumbersBoardViewModel, game: Game?) {

    val selectedNumbers = viewModel.selectedNumbers
    val numbers = (1..80).toList()
    val columnCount = 5
    val currentTime by rememberUpdatedState(newValue = Instant.now())

    val endTime = Instant.ofEpochMilli(game!!.drawTime)
    val timeLeft = remember { mutableStateOf(Duration.between(currentTime, endTime)) }
    val endTimeState by rememberUpdatedState(newValue = endTime)

    viewModel.setGame(game)
    viewModel.loadSelectedNumbers()

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

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        modifier = Modifier.background(secondary)
    ) {
        item(span = { GridItemSpan(columnCount) }) {
            BoardInfo(
                game = game,
                viewModel = viewModel,
                selectedNumbers = selectedNumbers,
                timeLeft = timeLeft
            )
        }
        items(numbers) { number ->
            CircleWithNumber(
                number = number,
                numberOfColumns = columnCount,
                selected = selectedNumbers.contains(number),
                selectedNumbersCount = selectedNumbers.size,
                onClick = { viewModel.clickOnNumber(number) })
        }
    }
}

@Composable
fun BoardInfo(
    game: Game,
    viewModel: NumbersBoardViewModel,
    selectedNumbers: List<Int>,
    timeLeft: MutableState<Duration>
) {
    Column {
        Text(
            text = stringResource(
                R.string.kolo_vreme_izvlacenja,
                game.drawId.toString(),
                convertMillisToDateTimeFormat(
                    game.drawTime
                )
            ),
            color = primary,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )

        Text(
            text = if (timeLeft.value.seconds > 0) stringResource(
                R.string.vreme_za_uplatu,
                convertMillisToHHMMSS(timeLeft.value.toMillis())
            ) else stringResource(
                R.string.zatvoreno
            ),
            color = if (timeLeft.value.seconds > 60) primary else Color.Red,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

        Text(
            text = stringResource(
                R.string.ukupno_selektovanih_brojeva,
                selectedNumbers.size
            ),
            color = primary,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 18.sp
        )

        ShowPricePoints(game = game)

        Button(
            onClick = { viewModel.addRandomNumbers() },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = primary)
        ) {
            Text(
                stringResource(R.string.random_brojevi),
                color = secondary,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun CircleWithNumber(
    number: Int,
    numberOfColumns: Int = 10,
    selected: Boolean,
    selectedNumbersCount: Int,
    onClick: () -> Unit
) {
    val spacingDp = 8
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val totalSpacingDp = (numberOfColumns - 1) * spacingDp
    val itemWidthDp = (screenWidthDp - totalSpacingDp) / numberOfColumns
    val showToast = remember { mutableStateOf(false) }
    if (showToast.value) {
        ShowToast(message = stringResource(R.string.maksimalan_broj_brojeva))
        LaunchedEffect(showToast.value) {
            showToast.value = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size((itemWidthDp + 1.5 * spacingDp).dp)
            .padding(spacingDp.dp)
            .clip(CircleShape)
            .then(
                if (selectedNumbersCount < 15 || selected) Modifier.clickable(onClick = onClick)
                else Modifier.clickable { showToast.value = true }
            )
    ) {
        Canvas(modifier = Modifier.size(itemWidthDp.dp)) {
            if (selected) {
                drawCircle(
                    color = primary,
                    center = Offset(size.width / 2, size.height / 2),
                    radius = size.minDimension / 2,
                    style = Stroke(width = 6.dp.toPx())
                )
            } else {
                drawCircle(
                    color = randomColor(),
                    center = Offset(size.width / 2, size.height / 2),
                    radius = size.minDimension / 2,
                    style = Stroke(width = 2.dp.toPx())
                )
            }

        }
        Text(
            text = number.toString(),
            color = Color.LightGray,
            fontSize = dpToSp((itemWidthDp / 3).dp, LocalContext.current)
        )
    }
}

@Composable
fun ShowPricePoints(game: Game) {
    Column {
        game.pricePoints.addOn.forEach { addOn ->
            Text(
                text = stringResource(R.string.tip_igre, addOn.gameType ?: "N/A"),
                color = primary,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = stringResource(R.string.vrednost, addOn.amount ?: 0.0),
                color = primary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}