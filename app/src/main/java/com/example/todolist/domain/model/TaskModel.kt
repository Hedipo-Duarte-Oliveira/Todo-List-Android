package com.example.todolist.domain.model

/**
 * Modelo de dados de domínio que representa uma Tarefa.
 * Esta classe é independente de frameworks (como Room) e é usada em toda a UI.
 */
data class TaskModel(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val category: TaskCategory = TaskCategory.DEFAULT
)
