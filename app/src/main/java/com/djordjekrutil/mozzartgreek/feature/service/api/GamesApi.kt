package com.djordjekrutil.mozzartgreek.feature.service.api

import com.djordjekrutil.mozzartgreek.feature.model.Draws
import com.djordjekrutil.mozzartgreek.feature.model.Game
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GamesApi {

    companion object{
        private const val GAMES = "{gameId}/upcoming/20/"
        private const val DRAW = "{gameId}/{drawId}/"
        private const val DRAWS_RESULTS = "{gameId}/draw-date/{fromDate}/{toDate}/"
    }

    @GET(GAMES)
    fun nextActiveGames(@Path("gameId") gameId: Int): Call<List<Game>>

    @GET(DRAW)
    fun getDraw(@Path("gameId") id: Int, @Path("drawId") drawId : Int): Call<Game>

    @GET(DRAWS_RESULTS)
    fun getDrawsResults(@Path("gameId") gameId: Int, @Path("fromDate") fromDate : String, @Path("toDate") toDate: String): Call<Draws>
}