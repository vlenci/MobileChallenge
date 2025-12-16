package com.example.mobilechallenge.di

import android.content.Context
import androidx.room.Room
import com.example.mobilechallenge.database.AppDatabase
import com.example.mobilechallenge.database.dao.TreeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mobile_challenge_db"
        ).build()
    }

    @Provides
    fun provideTreeDao(appDatabase: AppDatabase): TreeDao {
        return appDatabase.treeDao()
    }
}
