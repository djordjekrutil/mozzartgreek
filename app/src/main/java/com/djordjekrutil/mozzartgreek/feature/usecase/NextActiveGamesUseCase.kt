package com.djordjekrutil.mozzartgreek.feature.usecase

import com.djordjekrutil.mozzartgreek.core.exception.Failure
import com.djordjekrutil.mozzartgreek.core.functional.Either
import com.djordjekrutil.mozzartgreek.core.intrecator.UseCase
import com.djordjekrutil.mozzartgreek.feature.model.Game
import com.djordjekrutil.mozzartgreek.feature.repository.GamesRepository
import javax.inject.Inject

class NextActiveGamesUseCase
@Inject constructor(private val gamesRepository: GamesRepository.INetwork) :
    UseCase<List<Game>, Int>() {
    override suspend fun run(params: Int): Either<Failure, List<Game>> =
        gamesRepository.nextActiveGames(params)
}