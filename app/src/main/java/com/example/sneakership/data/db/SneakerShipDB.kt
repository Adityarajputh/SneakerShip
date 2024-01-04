package com.example.sneakership.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sneakership.data.dao.CartDao
import com.example.sneakership.data.model.Converters
import com.example.sneakership.data.model.SneakerItem

@Database(entities = [SneakerItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class SneakerShipDB : RoomDatabase(){
    abstract fun getCartDao(): CartDao
}
