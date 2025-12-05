package com.example.mobilechallenge.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mobilechallenge.LoginState
import com.example.mobilechallenge.states.TreeUiState
import com.example.mobilechallenge.ui.viewmodels.TreeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreeScreen(
    modifier: Modifier = Modifier,
    treeViewModel: TreeViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
    ) {

    val uiState = treeViewModel.uiState.collectAsState()

    var showTree by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.value) {
        when (val treeState = uiState.value) {
            is TreeUiState.Success<*> -> {
                showTree = true
                Log.d("success", "Se entrar no success o log tai")
                treeViewModel.updateState(TreeUiState.Loading)
            }
            is TreeUiState.Error -> {
                Log.e(
                    "Login error",
                    "Error message: ${treeState.message} / Error code: ${treeState.code}"
                )
                treeViewModel.updateState(TreeUiState.Loading)
            }
            else -> {}
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {showDialog = false},
            modifier = Modifier
                .clip(RoundedCornerShape(40.dp))
                .size(300.dp, 200.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = "Logout",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "Deseja realmente sair da sua conta?",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

            }

            Row(
                modifier = Modifier.padding(bottom = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { showDialog = false }) {
                    Text("Não")
                }

                TextButton(onClick = {
                    navigateToLogin()
                    showDialog = false
                }) {
                    Text("Sim")
                }
            }
        }
    }

    Column(
        modifier = modifier
            .background(Color(0xFFFF325F))
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFFF325F))
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 12.dp, bottom = 20.dp),
        ) {
            IconButton(
                modifier = Modifier.padding(bottom = 24.dp),
                onClick = { showDialog = true }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    tint = Color.Black,
                    contentDescription = "Logout"

                )
            }

            Column(
                modifier = Modifier.padding(start = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "Hello",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                )

                Text(
                    text = "Username",
                    style = TextStyle(
                        fontSize = 28.sp,
                        color = Color.White,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    treeViewModel.getTree()
                },
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .size(
                        width = 320.dp,
                        height = 56.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF325F),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFFF325F),
                    disabledContentColor = Color(0xFFFF325F)
                )
            ) {
                if (uiState.value == LoginState.Loading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(
                        text = "Mostrar árvore",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                        )
                    )
                }
            }
        }
    }
}