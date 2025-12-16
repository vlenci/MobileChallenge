package com.example.mobilechallenge.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.mobilechallenge.database.entities.TreeEntity

@Dao
interface TreeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTree(tree: TreeEntity)

    @Update
    fun updateUser(tree: TreeEntity)


}