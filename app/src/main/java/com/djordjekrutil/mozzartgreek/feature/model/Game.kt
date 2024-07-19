package com.djordjekrutil.mozzartgreek.feature.model

import android.net.Uri
import com.djordjekrutil.mozzartgreek.feature.navigation.JsonNavType
import com.google.gson.Gson

data class Game(
    val gameId: Int,
    val drawId: Long,
    val drawTime: Long,
    val status: String,
    val drawBreak: Int,
    val visualDraw: Int,
    val pricePoints: PricePoints
)
{
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}

class GameArgType : JsonNavType<Game>() {
    override fun fromJsonParse(value: String): Game = Gson().fromJson(value, Game::class.java)
    override fun Game.getJsonParse(): String = Gson().toJson(this)
}

data class PricePoints(
    val addOn: List<AddOn>
)


data class AddOn(
    val amount: Double?,
    val gameType: String?
)