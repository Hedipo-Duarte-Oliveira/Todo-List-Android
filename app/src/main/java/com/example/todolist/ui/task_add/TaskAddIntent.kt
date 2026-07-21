package com.example.todolist.ui.task_add

import com.example.todolist.domain.model.TaskCategory

/**
 * Intenções do usuário na tela de adição/edição de tarefa.
 */
sealed class TaskAddIntent {
    data class LoadTask(val taskId: Int) : TaskAddIntent()
    data class OnTitleChanged(val title: String) : TaskAddIntent()
    data class OnDescriptionChanged(val description: String) : TaskAddIntent()
    data class OnCategoryChanged(val category: TaskCategory) : TaskAddIntent()
    object SaveTask : TaskAddIntent()
}
