package com.example.todolist.domain.model

/**
 * Categorias disponíveis para as tarefas.
 */
enum class TaskCategory(val displayName: String) {
    DEFAULT("Geral"),
    WORK("Trabalho"),
    PERSONAL("Pessoal"),
    HEALTH("Saúde"),
    SHOPPING("Compras")
}
