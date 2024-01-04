package com.example.sneakership.core

import android.content.Context
import androidx.room.Room
import com.example.sneakership.data.db.SneakerShipDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context):
            Context = context

    @Provides
    @Singleton
    fun provideSneakerShipDb(@ApplicationContext context: Context):
            SneakerShipDB {
        return Room.databaseBuilder(
            context,
            SneakerShipDB::class.java,
            "SneakerShipDatabase"
        ).build()
    }

}