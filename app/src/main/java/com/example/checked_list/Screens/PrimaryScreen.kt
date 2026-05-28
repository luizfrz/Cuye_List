package com.example.checked_list.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.checked_list.R


@Composable
fun PrimaryScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .background(Color(0xFFF3978F6))
    ){
        Image(
            painter = painterResource(id = R.drawable.cuyes),
            contentDescription = "image app",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .width(90.dp)
                .padding(16.dp)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                   navController.navigate("New Task")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF2D6498),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Criar lista de tarefas",
                    style = TextStyle (
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .width(60.dp)
                        .padding(16.dp)
                    )
            }
        }
        Text(
            text = "Cuye list seu app de anotações ",
            textAlign = TextAlign.Center,
            style = TextStyle (
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            ),
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .height(90.dp)
                .width(400.dp)
                .align(Alignment.BottomCenter)
        )
    }
}



