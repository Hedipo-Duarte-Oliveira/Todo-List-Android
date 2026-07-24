package com.hedipoduarte.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hedipoduarte.todolist.ui.task_add.TaskAddScreen
import com.hedipoduarte.todolist.ui.theme.ToDoListTheme
import com.hedipoduarte.todolist.ui.task_list.TaskListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
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
                    onNavigateToAddTask = { navController.navigate("task_add") },
                    onNavigateToEditTask = { taskId -> navController.navigate("task_edit/$taskId") }
                )
            }
            composable("task_add") {
                TaskAddScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "task_edit/{taskId}",
                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getInt("taskId")
                TaskAddScreen(
                    taskId = taskId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
