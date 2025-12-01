package com.example.mobilechallenge.ui.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mobilechallenge.LoginState
import com.example.mobilechallenge.ui.LoginInput
import com.example.mobilechallenge.ui.viewmodels.LoginViewModel

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
                Toast.makeText(
                    context,
                    "Nome de usuário ou senha incorretos",
                    Toast.LENGTH_SHORT
                ).show()
                loginViewModel.updateState(LoginState.Idle)
            }
            else -> {}
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