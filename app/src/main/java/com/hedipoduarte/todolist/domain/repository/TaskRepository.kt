package com.hedipoduarte.todolist.domain.repository

import com.hedipoduarte.todolist.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define o contrato de negócio para manipulação de tarefas.
 * A implementação real (Data Layer) deve seguir este contrato.
 */
interface TaskRepository {

    /**
     * Retorna um fluxo reativo da lista de tarefas.
     */
    fun getTasks(): Flow<List<TaskModel>>

    /**
     * Busca uma tarefa pelo ID.
     */
    suspend fun getTaskById(id: Int): TaskModel?

    /**
     * Salva uma nova tarefa ou atualiza uma existente.
     */
    suspend fun saveTask(task: TaskModel)

    /**
     * Remove uma tarefa permanentemente.
     */
    suspend fun deleteTask(task: TaskModel)

    /**
     * Atualiza apenas o status de conclusão de uma tarefa pelo ID.
     */
    suspend fun updateTaskStatus(id: Int, isCompleted: Boolean)
}
