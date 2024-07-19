package com.djordjekrutil.mozzartgreek.feature.repository

import com.djordjekrutil.mozzartgreek.core.exception.Failure
import com.djordjekrutil.mozzartgreek.core.functional.Either
import com.djordjekrutil.mozzartgreek.core.platform.NetworkHandler
import com.djordjekrutil.mozzartgreek.feature.model.Draw
import com.djordjekrutil.mozzartgreek.feature.model.Draws
import com.djordjekrutil.mozzartgreek.feature.model.Game
import com.djordjekrutil.mozzartgreek.feature.service.GamesService
import javax.inject.Inject

interface GamesRepository {

    interface INetwork {
        fun nextActiveGames(gameId: Int): Either<Failure, List<Game>>
        fun getDraw(gameId: Int, drawId: Int): Either<Failure, Game>
        fun getDrawsResults(
            gameId: Int,
            fromDate: String,
            toDate: String
        ): Either<Failure, Draws>
    }

    class Network @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val gamesService: GamesService
    ) : INetwork {

        override fun nextActiveGames(gameId: Int): Either<Failure, List<Game>> {
            return if (networkHandler.isConnected) {
                val response = gamesService.nextActiveGames(gameId).execute()
                return response.body()?.let {
                    Either.Right(it)
                } ?: Either.Left(Failure.ServerError)
            } else
                Either.Left(Failure.NetworkConnection)
        }

        override fun getDraw(gameId: Int, drawId: Int): Either<Failure, Game> {
            return if (networkHandler.isConnected) {
                val response = gamesService.getDraw(gameId, drawId).execute()
                return response.body()?.let {
                    Either.Right(it)
                } ?: Either.Left(Failure.ServerError)
            } else
                Either.Left(Failure.NetworkConnection)
        }

        override fun getDrawsResults(
            gameId: Int,
            fromDate: String,
            toDate: String
        ): Either<Failure, Draws> {
            return if (networkHandler.isConnected) {
                val response = gamesService.getDrawsResults(gameId, fromDate, toDate).execute()
                return response.body()?.let {
                    Either.Right(it)
                } ?: Either.Left(Failure.ServerError)
            } else
                Either.Left(Failure.NetworkConnection)
        }

    }
}