package com.djordjekrutil.mozzartgreek.feature.usecase

import com.djordjekrutil.mozzartgreek.core.exception.Failure
import com.djordjekrutil.mozzartgreek.core.functional.Either
import com.djordjekrutil.mozzartgreek.core.intrecator.UseCase
import com.djordjekrutil.mozzartgreek.feature.model.Game
import com.djordjekrutil.mozzartgreek.feature.repository.GamesRepository
import javax.inject.Inject

class GetDrawUseCase
@Inject constructor(private val gamesRepository: GamesRepository.INetwork) :
    UseCase<Game, GetDrawUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, Game> =
        gamesRepository.getDraw(params.gameId, params.drawId)

    data class Params(
        val gameId: Int,
        val drawId: Int
    )
}