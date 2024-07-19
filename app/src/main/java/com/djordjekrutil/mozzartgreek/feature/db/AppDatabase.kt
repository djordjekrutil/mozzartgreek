package com.djordjekrutil.mozzartgreek.feature.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.djordjekrutil.mozzartgreek.core.di.DataConverter
import com.djordjekrutil.mozzartgreek.feature.db.dao.SelectedNumbersDao
import com.djordjekrutil.mozzartgreek.feature.model.DrawWithSelectedNumbers

@Database(
    entities = arrayOf(DrawWithSelectedNumbers::class), version = 5, exportSchema = true
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun SelectedNumbersDao(): SelectedNumbersDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mozzart-greek-database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}