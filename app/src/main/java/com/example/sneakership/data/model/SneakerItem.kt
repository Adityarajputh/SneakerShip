package com.example.sneakership.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity(tableName = "cart")
data class SneakerItem(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val brand: String,
    val colorway: String,
    val gender: String,
    val media: Media,
    val name: String,
    val releaseDate: String,
    val retailPrice: Int,
    val shoe: String,
    val styleId: String,
    val title: String,
    val year: Int,
)

class Converters{
    @TypeConverter
    fun fromMedia(media: Media?): String? {
        return media?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toMedia(json: String?): Media? {
        return json?.let { Gson().fromJson(it, Media::class.java) }
    }
}
