package com.example.mobilechallenge.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mobilechallenge.repositories.TreeNode

@Entity
data class TreeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val tag: String?,
    val type: String?,
    val level: Int?,
    val order: Int,
    val children: List<TreeNode>
)


