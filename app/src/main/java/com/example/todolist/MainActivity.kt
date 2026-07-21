package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.ui.task_add.TaskAddScreen
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.ui.task_list.TaskListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                TodoNavigation()
            }
        }
    }
}

@Composable
fun TodoNavigation() {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "task_list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("task_list") {
                TaskListScreen(
                    onNavigateToAddTask = { navController.navigate("task_add") }
                )
            }
            composable("task_add") {
                TaskAddScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
