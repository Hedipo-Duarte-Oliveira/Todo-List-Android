package com.example.todolist.ui.task_add

/**
 * Intenções do usuário na tela de adição de tarefa.
 */
sealed class TaskAddIntent {
    data class OnTitleChanged(val title: String) : TaskAddIntent()
    data class OnDescriptionChanged(val description: String) : TaskAddIntent()
    object SaveTask : TaskAddIntent()
}
