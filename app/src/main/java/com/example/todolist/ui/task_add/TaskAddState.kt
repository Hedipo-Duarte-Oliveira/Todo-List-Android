package com.example.todolist.ui.task_add

/**
 * Estado da tela de adição de tarefa.
 */
data class TaskAddState(
    val taskId: Int? = null,
    val title: String = "",
    val description: String = "",
    val isSaved: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
