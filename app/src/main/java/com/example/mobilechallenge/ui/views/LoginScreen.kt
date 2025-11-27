package com.example.mobilechallenge.ui.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mobilechallenge.LoginState
import com.example.mobilechallenge.ui.LoginInput
import com.example.mobilechallenge.ui.viewmodels.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.log

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: (String) -> Unit
) {

    val username = loginViewModel.username.collectAsState()
    val password = loginViewModel.password.collectAsState()
    val state = loginViewModel.state.collectAsState()

    Column(
        modifier = modifier
            .background(Color(0xFFFF325F)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .background(color = Color(0xFFFF325F))
                .fillMaxWidth()
                .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
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

            Column(
                modifier = Modifier.size(width = 320.dp, height = 100.dp),
            )
            {
                Text(
                    modifier = Modifier.padding(start = 20.dp, bottom = 4.dp),
                    text = "User",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF325F),
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                )

                LoginInput(
                    text = username.value,
                    onValueChange = { loginViewModel.setUsernameValue(it) },
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            Column(
                modifier = Modifier.size(width = 320.dp, height = 100.dp)
            )
            {
                Text(
                    modifier = Modifier.padding(start = 20.dp, bottom = 4.dp),
                    text = "Password",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF325F),
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                )

                LoginInput(
                    text = password.value,
                    onValueChange = { loginViewModel.setPasswordValue(it) },
                    inputType = PasswordVisualTransformation()
                )
            }

            Spacer(modifier = Modifier.size(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Button(
                    onClick = {
                        loginViewModel.getToken()
                        when (val loginState = state.value) {
                            is LoginState.Error -> Log.e("Login", "Login Inválido")
                            is LoginState.Success<*> -> navigateToHome(loginState.data as String)
                            LoginState.Idle -> {}
                            LoginState.Loading -> {}
                        }
                    },
                    shape = RoundedCornerShape(40.dp),
                    modifier = Modifier
                        .size(
                            width = 320.dp,
                            height = 56.dp
                        ),
                    colors = ButtonColors(
                        containerColor = Color(0xFFFF325F),
                        contentColor = Color(0xFFFF325F),
                        disabledContainerColor = Color(0xFFFF325F),
                        disabledContentColor = Color(0xFFFF325F)
                    )
                ) {
                    if (loginViewModel.state == LoginState.Loading) {
                        CircularProgressIndicator()
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