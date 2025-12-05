package com.example.mobilechallenge.ui.viewmodels

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilechallenge.LoginState
import com.example.mobilechallenge.repositories.TreeNode
import com.example.mobilechallenge.repositories.TreeRepository
import com.example.mobilechallenge.states.TreeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TreeViewModel @Inject constructor(
    private val repository: TreeRepository,
): ViewModel() {
    private val  _uiState = MutableStateFlow<TreeUiState<List<TreeNode>>>(TreeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getTree() {
        viewModelScope.launch {

            _uiState.value = TreeUiState.Loading
            val result = repository.getLocalTree(
                token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzY0OTM4OTQ0LCJpYXQiOjE3NjQ5Mzc3NDQsImp0aSI6IjdmNDM1ZDFkMWFhNDRlNTY4ZmZhZTYwNGQ4MWUyYmIwIiwidXNlcl9pZCI6MTQ3Mn0.h789WcFB0P_6uOSaIuZMeehtYCgDIV_4GQ3wee8Azcs",
                siteId = 20640
            )
            _uiState.value = result
        }
    }

    fun updateState(newState: TreeUiState<List<TreeNode>>) {
        _uiState.value = newState
    }

}