package com.djordjekrutil.mozzartgreek.core.di

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun fromIntList(value: List<Int>?): String {
        val gson = Gson()
        if (value == null) {
            return "[]"
        }
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIntList(value: String): List<Int>? {
        val gson = Gson()
        if (value == "[]") {
            return emptyList()
        }
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, type)
    }
}