package com.djordjekrutil.mozzartgreek.feature.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djordjekrutil.mozzartgreek.feature.model.Game
import com.djordjekrutil.mozzartgreek.feature.usecase.NextActiveGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveGamesViewModel @Inject constructor(
    private val activeGamesUseCase: NextActiveGamesUseCase
) : ViewModel() {

    private val defaultGameId = 1100

    private val _gamesStateFlow = mutableStateOf<GamesState>(GamesState.Loading)
    val gamesStateFlow = _gamesStateFlow

    init {
        loadGames()
    }

    fun loadGames() {
        activeGamesUseCase(defaultGameId) {
            it.fold(
                {
                    viewModelScope.launch {
                        _gamesStateFlow.value = GamesState.Error
                    }
                },
                {
                    viewModelScope.launch {
                        _gamesStateFlow.value = GamesState.Loaded(it)
                    }
                }
            )
        }
    }
}

sealed class GamesState {
    data object Loading : GamesState()
    data object Error : GamesState()
    data class Loaded(val games: List<Game>) : GamesState()
}