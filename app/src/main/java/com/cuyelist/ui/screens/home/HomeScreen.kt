package com.cuyelist.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuyelist.R

@Composable
fun HomeScreen(onCreateTask: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3978F6)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.cuyes),
            contentDescription = "cuye (porquinho) icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        Button(
            onClick = onCreateTask,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF2D6498),
                contentColor = Color.White,
            ),
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                text = "Criar lista de tarefas",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(16.dp),
            )
        }

        Text(
            text = "Cuye list seu app de anotações ",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            ),
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .align(Alignment.BottomCenter),
        )
    }
}