package com.hedipoduarte.todolist.ui.task_list

import com.hedipoduarte.todolist.domain.model.TaskModel

/**
 * Representa as intenções (ações) do usuário na tela de lista de tarefas.
 */
sealed class TaskListIntent {
    object LoadTasks : TaskListIntent()
    data class ToggleTaskStatus(val taskId: Int, val isCompleted: Boolean) : TaskListIntent()
    data class DeleteTask(val task: TaskModel) : TaskListIntent()
    data class ShowDeleteConfirmation(val task: TaskModel) : TaskListIntent()
    object HideDeleteConfirmation : TaskListIntent()
    data class FilterTasks(val filter: TaskFilter) : TaskListIntent()
    data class SearchTasks(val query: String) : TaskListIntent()
}

/**
 * Filtros disponíveis para a lista de tarefas.
 */
enum class TaskFilter {
    ALL, PENDING, COMPLETED
}
