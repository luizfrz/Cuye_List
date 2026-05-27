package com.example.checked_list

import android.R
import android.R.attr.label
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.checked_list.ui.theme.Checked_ListTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Checked_ListTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home",
                ){
                    composable("home"){
                        Home(navController)
                    }
                    composable("New Task"){
                        List(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Home(navController: NavHostController) {
    Box(
        modifier = Modifier
            .background(Color(0xFFF3978F6)),
        ){
//        Image(
//            painter = painterResource(id = R.drawable.cuye),
//            contentDescription = "CuyeList",
//            modifier = Modifier,
//            contentScale = ContentScale.Crop
//        ){}

        Text(
            text = "Cuye List", color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                   navController.navigate("New Task")
                },
            ) {
                Text(
                    text = "Criar uma nova tarefa",
                    modifier = Modifier
                        .background(Color(0xFFF2D6498))
                        .fillMaxWidth(0.3f)
                        .height(50.dp)
                        .width(50.dp)
                        .padding( bottom = 5.dp)
                    )
            }
        }
    }
}
data class TaskCheck(
    val name: String,
    val CheckedList : Boolean = false
)
@Composable
fun List(navController: NavHostController) {
    Box(
        modifier = Modifier
            .background(Color(0xFFF3978F6)),
    ){
        Button(onClick = { navController.navigate("Home") },
                modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
            ) {
            Text(
                text = "Voltar ",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(20.dp)
                    .width(10.dp)
                    .padding( bottom = 5.dp)
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
            label = { Text("Criar uma nova atividade", color = Color.White,) },
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
             }
         )
         {
             Text("Adicionar uma tarefa")
         }
            Button(
                onClick = {
                    lisTask.clear()
                }
            ) {
                Text("Limpar Tarefas")
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
            lisTask.forEachIndexed  { index, task ->

            Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                    .fillMaxWidth()
            ){
                Checkbox(
                    checked = task.CheckedList,

                    onCheckedChange = { isChecked ->

                        lisTask[index] = task.copy(
                            CheckedList = isChecked
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(50.dp)
                        .width(50.dp)
                        .padding( bottom = 5.dp)
                        .border(2.dp, Color.Black, CircleShape)
                ) {
                    Text(
                        text = task.name,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
              }
            }
          }
        }
    }
}



