package com.djordjekrutil.mozzartgreek.feature.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.djordjekrutil.mozzartgreek.feature.model.DrawWithSelectedNumbers

@Dao
interface SelectedNumbersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSelectedNumbers(drawWithSelectedNumbers: DrawWithSelectedNumbers)

    @Update
    fun updateSelectedNumbers(drawWithSelectedNumbers: DrawWithSelectedNumbers)

    @Query("DELETE FROM DrawWithSelectedNumbers WHERE drawId = :drawId")
    fun deleteDrawSelectedNumbers(drawId: Int)

    @Query("SELECT * FROM DrawWithSelectedNumbers")
    fun getAllDrawsSelectedNumbers(): List<DrawWithSelectedNumbers>?

    @Query("SELECT * FROM DrawWithSelectedNumbers WHERE  drawId = :drawId")
    fun getSelectedNumbersById(drawId: Long): DrawWithSelectedNumbers?
}