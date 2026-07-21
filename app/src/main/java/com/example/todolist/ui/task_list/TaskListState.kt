package com.example.todolist.ui.task_list

import com.example.todolist.domain.model.TaskModel

/**
 * Representa o estado imutável da tela de lista de tarefas.
 */
data class TaskListState(
    val isLoading: Boolean = false,
    val tasks: List<TaskModel> = emptyList(),
    val errorMessage: String? = null
)
