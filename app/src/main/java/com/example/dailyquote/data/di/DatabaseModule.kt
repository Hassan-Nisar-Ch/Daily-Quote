package com.example.dailyquote.data.di

import android.app.Application
import androidx.room.Room
import com.example.dailyquote.data.local.dao.QuoteDao
import com.example.dailyquote.data.local.database.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideQuoteDatabase(application: Application): QuoteDatabase {
        return Room.databaseBuilder(
            application,
            QuoteDatabase::class.java,
            "Quote Database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuoteDao(noteDatabase: QuoteDatabase): QuoteDao {
        return noteDatabase.quoteDao()
    }
}