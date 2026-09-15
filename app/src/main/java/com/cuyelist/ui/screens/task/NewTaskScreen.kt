package com.cuyelist.ui.screens.task

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.cuyelist.model.Task

@Composable
fun NewTaskScreen(onBack: () -> Unit) {
    val tasks = remember { mutableStateListOf<Task>() }
    var textInput by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3978F6)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Digite uma nova tarefa", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Yellow,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.Yellow,
                    focusedContainerColor = Color(0xFF2D2D2D),
                    unfocusedContainerColor = Color(0xFF2D2D2D),
                ),
                singleLine = true,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ActionButton(
                    text = "Adicionar",
                    onClick = {
                        if (textInput.isNotBlank()) {
                            tasks.add(Task(name = textInput))
                            textInput = ""
                        }
                    },
                )
                ActionButton(text = "Limpar Tudo", onClick = tasks::clear)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2D6498))
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                tasks.forEachIndexed { index, task ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = { isDone ->
                                tasks[index] = task.copy(isDone = isDone)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Green,
                                uncheckedColor = Color.Red,
                                checkmarkColor = Color.White,
                            ),
                        )
                        Text(
                            text = task.name,
                            textAlign = TextAlign.Center,
                            textDecoration = if (task.isDone) {
                                TextDecoration.LineThrough
                            } else {
                                TextDecoration.None
                            },
                            color = Color.White,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFFFC0C2C9))
                                .padding(8.dp),
                        )
                        IconButton(onClick = { tasks.removeAt(index) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Excluir tarefa",
                                tint = Color.Red,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2D6498),
                contentColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
        ) {
            Text(
                text = "Voltar",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                ),
                fontSize = 15.sp,
            )
        }
    }
}

@Composable
private fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2D6498),
            contentColor = Color.White,
        ),
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            ),
            fontSize = 14.sp,
        )
    }
}