package com.djordjekrutil.mozzartgreek.feature.service

import com.djordjekrutil.mozzartgreek.feature.model.Draws
import com.djordjekrutil.mozzartgreek.feature.model.Game
import com.djordjekrutil.mozzartgreek.feature.service.api.GamesApi
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesService
@Inject constructor(retrofit: Retrofit) : GamesApi {

    private val gamesApi by lazy { retrofit.create(GamesApi::class.java) }

    override fun nextActiveGames(gameId: Int): Call<List<Game>> =
        gamesApi.nextActiveGames(gameId)

    override fun getDraw(id: Int, drawId: Int): Call<Game> =
        gamesApi.getDraw(id, drawId)

    override fun getDrawsResults(gameId: Int, fromDate: String, toDate: String): Call<Draws> =
        gamesApi.getDrawsResults(gameId, fromDate, toDate)
}