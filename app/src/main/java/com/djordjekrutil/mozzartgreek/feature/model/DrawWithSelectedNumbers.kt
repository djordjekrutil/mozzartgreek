package com.djordjekrutil.mozzartgreek.feature.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DrawWithSelectedNumbers(
    @PrimaryKey
    val drawId  : Long,
    val numbers : List<Int>
)
