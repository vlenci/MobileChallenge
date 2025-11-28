package com.example.mobilechallenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginInput(
    text: String,
    onValueChange: (String) -> Unit = { },
    isPassword: Boolean = false,
    textIsEmpty: Boolean? = null,
) {

    var visualTransformation by remember {
        mutableStateOf(
            if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
    }

    val loginInputModifier = if (textIsEmpty == true) {
        Modifier
            .size(
                width = 320.dp,
                height = 56.dp
            )
            .border(1.dp, color = Color.Red, shape = RoundedCornerShape(40.dp))

    } else {
        Modifier.size(
            width = 320.dp,
            height = 56.dp
        )
    }

    Column(
        modifier = Modifier.size(width = 320.dp, height = 120.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp, bottom = 4.dp),
            text = if (isPassword) "Password" else "User",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF325F),
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
            )
        )

        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            maxLines = 1,
            shape = RoundedCornerShape(40.dp),
            modifier = loginInputModifier,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            visualTransformation = visualTransformation,
            trailingIcon = {
                if (isPassword) {
                    Icon(
                        imageVector =
                            if (visualTransformation == PasswordVisualTransformation()) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                        contentDescription = "Visibility",
                        modifier = Modifier
                            .clip(RoundedCornerShape(40.dp))
                            .clickable(
                                onClick = {
                                    visualTransformation =
                                        if (visualTransformation == PasswordVisualTransformation()) {
                                            VisualTransformation.None
                                        } else {
                                            PasswordVisualTransformation()
                                        }
                                }
                            )
                            .padding(10.dp)
                    )
                }
            }
        )

        if (textIsEmpty == true) {
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 4.dp, bottom = 0.dp),
                text = if (isPassword) {
                    "Insira a senha antes de efetuar o login"
                } else {
                    "Insira o nome de usuário antes de efetuar o login"
                },
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF325F),
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                )
            )
        }
    }


}