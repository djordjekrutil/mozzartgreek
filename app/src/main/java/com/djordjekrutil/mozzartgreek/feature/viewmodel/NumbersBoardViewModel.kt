package com.djordjekrutil.mozzartgreek.feature.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.djordjekrutil.mozzartgreek.feature.model.DrawWithSelectedNumbers
import com.djordjekrutil.mozzartgreek.feature.model.Game
import com.djordjekrutil.mozzartgreek.feature.usecase.GetSelectedNumbersByDrawIdUseCase
import com.djordjekrutil.mozzartgreek.feature.usecase.InsertSelectedNumbersUseCase
import com.djordjekrutil.mozzartgreek.feature.utils.generateRandomNumbers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NumbersBoardViewModel @Inject constructor(
    private val insertSelectedNumbersUseCase: InsertSelectedNumbersUseCase,
    private val getSelectedNumbersByDrawIdUseCase: GetSelectedNumbersByDrawIdUseCase,
) : ViewModel() {

    private val _game = mutableStateOf<Game?>(null)
    val game: State<Game?> = _game

    private var _selectedNumbers = mutableStateListOf<Int>()
    val selectedNumbers: List<Int> = _selectedNumbers

    fun setGame(game: Game) {
        _game.value = game
    }

    fun loadSelectedNumbers() {
        game.value?.let { game ->
            getSelectedNumbersByDrawIdUseCase(game.drawId) {
                it.fold(
                    {},
                    {
                        if (it.numbers.isNotEmpty()) {
                            _selectedNumbers.apply {
                                clear()
                                addAll(it.numbers)
                            }
                        }
                    }
                )
            }
        }
    }

    fun clickOnNumber(number: Int) {
        if (_selectedNumbers.contains(number)) {
            _selectedNumbers = _selectedNumbers.apply { remove(number) }
        } else {
            _selectedNumbers.add(number)
        }
        game.value?.let {
            insertSelectedNumbersUseCase(
                DrawWithSelectedNumbers(
                    it.drawId,
                    _selectedNumbers
                )
            )
        }
    }

    fun addRandomNumbers() {
        val randomNumbers = generateRandomNumbers(1..80, 15)
        _selectedNumbers.apply {
            clear()
            addAll(randomNumbers)
        }
        game.value?.let {
            insertSelectedNumbersUseCase(
                DrawWithSelectedNumbers(
                    it.drawId,
                    _selectedNumbers
                )
            )
        }
    }
}
