package com.example.todolist.ui.task_add

/**
 * Intenções do usuário na tela de adição/edição de tarefa.
 */
sealed class TaskAddIntent {
    data class LoadTask(val taskId: Int) : TaskAddIntent()
    data class OnTitleChanged(val title: String) : TaskAddIntent()
    data class OnDescriptionChanged(val description: String) : TaskAddIntent()
    object SaveTask : TaskAddIntent()
}
