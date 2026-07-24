package com.hedipoduarte.todolist.ui.task_add

import com.hedipoduarte.todolist.domain.model.TaskCategory

/**
 * Estado da tela de adição de tarefa.
 */
data class TaskAddState(
    val taskId: Int? = null,
    val title: String = "",
    val description: String = "",
    val category: TaskCategory = TaskCategory.DEFAULT,
    val dueDate: Long? = null,
    val isSaved: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
