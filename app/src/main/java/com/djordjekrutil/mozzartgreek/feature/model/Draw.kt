package com.djordjekrutil.mozzartgreek.feature.model

import com.google.gson.annotations.SerializedName


data class Draws(
    @SerializedName("content") val draws: List<Draw>,
    val totalPages: Long,
    val totalElements: Long,
    val last: Boolean,
    val numberOfElements: Long,
    val sort: List<Sort>,
    val first: Boolean,
    val size: Long,
    val number: Long,
)

data class Draw(
    val gameId: Long,
    val drawId: Long,
    val drawTime: Long,
    val status: String,
    val drawBreak: Long,
    val visualDraw: Long,
    val pricePoints: PricePoints2,
    val winningNumbers: WinningNumbers,
    val prizeCategories: List<PrizeCategory>,
    val wagerStatistics: WagerStatistics,
)

data class PricePoints2(
    val addOn: List<AddOn2>,
    val amount: Double,
)

data class AddOn2(
    val amount: Double,
    val gameType: String,
)

data class WinningNumbers(
    val list: List<Long>,
    val bonus: List<Long>,
    val sidebets: Sidebets,
)

data class Sidebets(
    val evenNumbersCount: Long,
    val oddNumbersCount: Long,
    val winningColumn: Long,
    val winningParity: String,
    val oddNumbers: List<Long>,
    val evenNumbers: List<Long>,
    val columnNumbers: ColumnNumbers,
)

data class ColumnNumbers(
    @SerializedName("1")
    val n1: List<Long>,
    @SerializedName("2")
    val n2: List<Long>,
    @SerializedName("3")
    val n3: List<Long>,
    @SerializedName("4")
    val n4: List<Long>,
    @SerializedName("5")
    val n5: List<Long>,
    @SerializedName("6")
    val n6: List<Long>,
    @SerializedName("7")
    val n7: List<Long>,
    @SerializedName("8")
    val n8: List<Long>,
    @SerializedName("9")
    val n9: List<Long>,
    @SerializedName("10")
    val n10: List<Long>,
)

data class PrizeCategory(
    val id: Long,
    val divident: Double,
    val winners: Long,
    val distributed: Double,
    val jackpot: Long,
    val fixed: Double,
    val categoryType: Long,
    val gameType: String,
)

data class WagerStatistics(
    val columns: Long,
    val wagers: Long,
    val addOn: List<Any?>,
)

data class Sort(
    val direction: String,
    val property: String,
    val ignoreCase: Boolean,
    val nullHandling: String,
    val descending: Boolean,
    val ascending: Boolean,
)
