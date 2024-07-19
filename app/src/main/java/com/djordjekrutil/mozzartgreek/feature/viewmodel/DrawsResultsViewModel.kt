package com.djordjekrutil.mozzartgreek.feature.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djordjekrutil.mozzartgreek.feature.model.Draw
import com.djordjekrutil.mozzartgreek.feature.usecase.GetDrawsResultsUseCase
import com.djordjekrutil.mozzartgreek.feature.utils.convertMillisToDateTimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultDrawsViewModel @Inject constructor(
    private val getDrawsResultsUseCase: GetDrawsResultsUseCase
) : ViewModel() {

    private val _drawsStateFlow = mutableStateOf<DrawsState>(DrawsState.Loading)
    val drawsStateFlow = _drawsStateFlow

    init {
        loadDraws()
    }

    fun loadDraws() {
        val today =
            convertMillisToDateTimeFormat(System.currentTimeMillis() - 84600000, "yyyy-MM-dd")
        getDrawsResultsUseCase(
            GetDrawsResultsUseCase.Params(
                1100,
                fromDate = today,
                toDate = today
            )
        ) {
            it.fold(
                {
                    viewModelScope.launch {
                        _drawsStateFlow.value = DrawsState.Error
                    }
                },
                {
                    viewModelScope.launch {
                        _drawsStateFlow.value = DrawsState.Loaded(it.draws)
                    }
                }
            )
        }
    }
}

sealed class DrawsState {
    data object Loading : DrawsState()
    data object Error : DrawsState()
    data class Loaded(val draws: List<Draw>) : DrawsState()
}