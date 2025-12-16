package com.example.mobilechallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilechallenge.database.dao.TreeDao
import com.example.mobilechallenge.database.entities.TreeEntity


@Database(entities = [TreeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun treeDao(): TreeDao
}
