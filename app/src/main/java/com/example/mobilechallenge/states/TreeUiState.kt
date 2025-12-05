package com.example.mobilechallenge.states

import com.example.mobilechallenge.repositories.TreeNode

sealed class TreeUiState<out T> {

    data class Success<out T>(val tree: List<TreeNode>) : TreeUiState<T>()

    data class Error(val message: String, val code: Int? = null) : TreeUiState<Nothing>()

    object Loading : TreeUiState<Nothing>()

}