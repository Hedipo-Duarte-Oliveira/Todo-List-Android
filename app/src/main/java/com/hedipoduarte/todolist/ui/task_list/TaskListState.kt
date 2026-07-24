package com.hedipoduarte.todolist.ui.task_list

import com.hedipoduarte.todolist.domain.model.TaskModel

/**
 * Representa o estado imutável da tela de lista de tarefas.
 */
data class TaskListState(
    val isLoading: Boolean = false,
    val tasks: List<TaskModel> = emptyList(),
    val filteredTasks: List<TaskModel> = emptyList(),
    val taskToDelete: TaskModel? = null,
    val selectedFilter: TaskFilter = TaskFilter.ALL,
    val searchQuery: String = "",
    val errorMessage: String? = null
)
