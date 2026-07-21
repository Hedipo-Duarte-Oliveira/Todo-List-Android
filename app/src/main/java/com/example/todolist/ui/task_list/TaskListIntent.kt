package com.example.todolist.ui.task_list

import com.example.todolist.domain.model.TaskModel

/**
 * Representa as intenções (ações) do usuário na tela de lista de tarefas.
 */
sealed class TaskListIntent {
    object LoadTasks : TaskListIntent()
    data class ToggleTaskStatus(val taskId: Int, val isCompleted: Boolean) : TaskListIntent()
    data class DeleteTask(val task: TaskModel) : TaskListIntent()
}
