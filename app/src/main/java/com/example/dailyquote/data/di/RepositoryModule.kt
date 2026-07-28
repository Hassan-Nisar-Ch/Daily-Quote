package com.example.dailyquote.data.di

import com.example.dailyquote.data.repository.QuoteRepositoryImpl
import com.example.dailyquote.domain.repository.QuoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Repository Binding Module using @Binds
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPostRepository(
        quoteRepositoryImpl: QuoteRepositoryImpl
    ): QuoteRepository
}