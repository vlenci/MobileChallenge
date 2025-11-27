package com.example.mobilechallenge.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginInput(
    text: String,
    onValueChange: (String) -> Unit = { },
    isPassword: Boolean = false,
) {

    var visualTransformation by remember {
        mutableStateOf(
            if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
    }

    TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        maxLines = 1,
        shape = RoundedCornerShape(40.dp),
        modifier = Modifier.size(
            width = 320.dp,
            height = 56.dp
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (isPassword) {
                Box {
                    Icon(
                        imageVector =
                            if (visualTransformation == PasswordVisualTransformation()) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                        contentDescription = "Visibility",
                        modifier = Modifier
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
        }

    )
    
    
}