package com.djordjekrutil.mozzartgreek.feature.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.djordjekrutil.mozzartgreek.R
import com.djordjekrutil.mozzartgreek.feature.model.Draw
import com.djordjekrutil.mozzartgreek.feature.utils.convertMillisToDateTimeFormat
import com.djordjekrutil.mozzartgreek.feature.utils.dpToSp
import com.djordjekrutil.mozzartgreek.feature.utils.randomColor
import com.djordjekrutil.mozzartgreek.feature.viewmodel.DrawsState
import com.djordjekrutil.mozzartgreek.feature.viewmodel.ResultDrawsViewModel
import com.djordjekrutil.mozzartgreek.ui.ErrorMessage
import com.djordjekrutil.mozzartgreek.ui.theme.primary
import com.djordjekrutil.mozzartgreek.ui.theme.secondary
import com.djordjekrutil.mozzartgreek.ui.theme.secondary70

@Composable
fun DrawsResultsScreen(
    viewModel: ResultDrawsViewModel
) {
    when (val state = viewModel.drawsStateFlow.value) {
        is DrawsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DrawsState.Error -> {
            ErrorMessage {
                viewModel.loadDraws()
            }
        }

        is DrawsState.Loaded -> {
            ShowDrawsHistory(draws = state.draws)
        }
    }
}

@Composable
fun ShowDrawsHistory(draws: List<Draw>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(secondary)
    ) {
        items(draws) { draw ->
            DrawItem(draw = draw)
            HorizontalDivider(thickness = 1.dp, color = secondary70)
        }
    }
}

@Composable
fun DrawItem(draw: Draw) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(secondary),
    ) {
        Text(
            text = stringResource(
                R.string.vreme_izvlacenja_kolo, convertMillisToDateTimeFormat(
                    draw.drawTime,
                    "dd.MM.yyyy hh:mm"
                ), draw.drawId
            ),
            color = primary
        )
        GridInLazyColumn(numbers = draw.winningNumbers.list)
    }
}

@Composable
fun GridInLazyColumn(numbers: List<Long>, columns: Int = 10) {
    Column {
        for (i in 0 until numbers.size/columns) {
            Row {
                for (j in 0 until columns) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        CircleWithNumber(number = numbers[i * columns + j])
                    }
                }
            }
        }
    }
}

@Composable
fun CircleWithNumber(number: Long, numberOfColumns : Int = 10) {
    val spacingDp = 8
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val totalSpacingDp = (numberOfColumns - 1) * spacingDp
    val itemWidthDp = (screenWidthDp - totalSpacingDp) / numberOfColumns

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size((itemWidthDp + 1.5 * spacingDp).dp)
    ) {
        Canvas(modifier = Modifier.size(itemWidthDp.dp)) {
            drawCircle(
                color = randomColor(),
                center = Offset(size.width / 2, size.height / 2),
                radius = size.minDimension / 2,
                style = Stroke(width = 2.dp.toPx())
            )
        }
        Text(
            text = number.toString(),
            color = Color.LightGray,
            fontSize = dpToSp((itemWidthDp / 3).dp, LocalContext.current)
        )
    }
}
