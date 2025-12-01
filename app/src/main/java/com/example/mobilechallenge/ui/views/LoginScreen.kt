package com.example.mobilechallenge.ui.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mobilechallenge.LoginState
import com.example.mobilechallenge.ui.LoginInput
import com.example.mobilechallenge.ui.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: (String) -> Unit
) {

    val username = loginViewModel.username.collectAsState()
    val password = loginViewModel.password.collectAsState()
    val state = loginViewModel.state.collectAsState()

    val context = LocalContext.current

    var usernameIsEmpty: Boolean? by remember { mutableStateOf(null) }
    var passwordIsEmpty: Boolean? by remember { mutableStateOf(null) }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.value) {
        when (val loginState = state.value) {
            is LoginState.Success<*> -> {
                navigateToHome(loginState.data as String)
                loginViewModel.updateState(LoginState.Idle)
            }
            is LoginState.Error -> {
                Log.e(
                    "Login error",
                    "Error message: ${loginState.message} / Error code: ${loginState.code}"
                )
                showDialog = true
                loginViewModel.updateState(LoginState.Idle)
            }
            else -> {}
        }
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = {showDialog = false},
            modifier = Modifier
                .clip(RoundedCornerShape(40.dp))
                .size(300.dp, 200.dp)
                .background(Color.LightGray)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = "Login inválido",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "Usuário ou senha incorretos. \nTente novamente.",
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
                    Text("Ok")
                }
            }
        }
    }

    Column(
        modifier = modifier
            .background(Color(0xFFFF325F)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(color = Color(0xFFFF325F))
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topStart = 50.dp,
                            topEnd = 10.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 50.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Person icon",
                    colorFilter = ColorFilter.tint(Color(0xFFFF325F)),
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 150.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Welcome",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF325F),
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                )
            }

            LoginInput(
                text = username.value,
                onValueChange = { loginViewModel.setUsernameValue(it) },
                textIsEmpty = usernameIsEmpty
            )

            Spacer(modifier = Modifier.size(20.dp))

            LoginInput(
                text = password.value,
                onValueChange = { loginViewModel.setPasswordValue(it) },
                isPassword = true,
                textIsEmpty = passwordIsEmpty,
            )

            Spacer(modifier = Modifier.size(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        val usernameEmpty = username.value.isEmpty()
                        val passwordEmpty = password.value.isEmpty()

                        usernameIsEmpty = usernameEmpty
                        passwordIsEmpty = passwordEmpty

                        if (!usernameEmpty && !passwordEmpty) {
                            loginViewModel.getToken()
                        }
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
                    if (state.value == LoginState.Loading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(
                            text = "Login",
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
}