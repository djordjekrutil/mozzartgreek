package com.djordjekrutil.mozzartgreek.feature.usecase

import com.djordjekrutil.mozzartgreek.core.exception.Failure
import com.djordjekrutil.mozzartgreek.core.functional.Either
import com.djordjekrutil.mozzartgreek.core.intrecator.UseCase
import com.djordjekrutil.mozzartgreek.feature.model.Draws
import com.djordjekrutil.mozzartgreek.feature.repository.GamesRepository
import javax.inject.Inject

class GetDrawsResultsUseCase
@Inject constructor(private val gamesRepository: GamesRepository.INetwork) :
    UseCase<Draws, GetDrawsResultsUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, Draws> =
        gamesRepository.getDrawsResults(params.gameId, params.fromDate, params.toDate)

    data class Params(
        val gameId: Int,
        val fromDate: String,
        val toDate: String
    )
}