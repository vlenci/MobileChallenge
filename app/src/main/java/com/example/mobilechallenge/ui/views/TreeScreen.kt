package com.example.mobilechallenge.ui.views

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
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobilechallenge.ui.theme.fontFamily

@Composable
fun TreeScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .background(Color(0xFFFF325F))
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFFF325F))
                .fillMaxWidth()
                .height(200.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Hello",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                )

                Spacer(Modifier.size(16.dp))

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
        }
    }
}