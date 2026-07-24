package com.hedipoduarte.todolist.ui.task_add

import com.hedipoduarte.todolist.domain.model.TaskCategory

/**
 * Intenções do usuário na tela de adição/edição de tarefa.
 */
sealed class TaskAddIntent {
    data class LoadTask(val taskId: Int) : TaskAddIntent()
    data class OnTitleChanged(val title: String) : TaskAddIntent()
    data class OnDescriptionChanged(val description: String) : TaskAddIntent()
    data class OnCategoryChanged(val category: TaskCategory) : TaskAddIntent()
    data class OnDueDateChanged(val dueDate: Long?) : TaskAddIntent()
    object SaveTask : TaskAddIntent()
}
