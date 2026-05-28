package com.example.checked_list.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


data class TaskCheck(
    val name: String,
    val checkedList : Boolean = false,
    val isDone: Boolean =  false
)

@Composable
fun SecoundScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .background(Color(0xFFF3978F6)),
    ){
        Button(onClick = { navController.navigate("Home")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2D6498),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(
                text = "Voltar ",
                textAlign = TextAlign.Center,
                style = TextStyle (
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                ),
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(20.dp)
                    .width(10.dp)
                    .padding( bottom = 4.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {

            var textInput by remember { mutableStateOf("") }
            val lisTask = remember {
                mutableStateListOf<TaskCheck>()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
            {
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { newText -> textInput = newText },
                    label = { Text("Digite uma nova tarefa", color = Color.White,) },
                    modifier = Modifier
                        .fillMaxWidth() // ocupa toda largura
                        .height(110.dp)  // altura maior
                        .padding(16.dp),

                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Yellow,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.Yellow,
                        focusedContainerColor = Color(0xFF2D2D2D),
                        unfocusedContainerColor = Color(0xFF2D2D2D)
                    ),

                    singleLine = true
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (textInput.isNotBlank()) {
                                lisTask.add(TaskCheck(name =  textInput))
                                textInput = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2D6498),
                            contentColor = Color.White
                        )
                    ) {
                        Text (
                        text = "Adicionar ",
                        textAlign = TextAlign.Center,
                        style = TextStyle (
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                        ),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(20.dp)
                            .width(10.dp)
                            .padding( bottom = 5.dp)
                        )
                    }
                    Button(
                        onClick = {
                            lisTask.clear()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2D6498),
                            contentColor = Color.White
                        ),
                    ) {
                        Text (
                            text = "Limpar ",
                            textAlign = TextAlign.Center,
                            style = TextStyle (
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                            ),
                            fontSize = 12.sp,
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .height(20.dp)
                                .width(10.dp)
                                .padding( bottom = 5.dp)
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFFF2D6498))
                    .size(400.dp)
            )
            {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
            lisTask.forEachIndexed  { index, task ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Checkbox(
                        checked = task.checkedList && task.isDone,
                        onCheckedChange = { isChecked ->

                            lisTask[index] = task.copy(
                                checkedList = isChecked,
                                isDone = isChecked
                            )
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(50.dp)
                            .width(50.dp)
                            .background(Color(0xFFF2D6498))
                            .border(2.dp, Color(0xFFF2D6498), CircleShape)
                    ) {
                        Text(
                            text = task.name,

                            textDecoration =
                                if (task.isDone)
                                    TextDecoration.LineThrough
                                else
                                    TextDecoration.None,
                            color = Color.White,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            lisTask.removeAt(index)
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir tarefa",
                            tint = Color.Red
                        )
                    }
                }
                }
            }
            }
            }
        }
    }

