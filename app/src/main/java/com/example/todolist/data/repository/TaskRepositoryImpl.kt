package com.example.todolist.data.repository

import com.example.todolist.data.local.TaskDao
import com.example.todolist.data.local.TaskEntity
import com.example.todolist.domain.model.TaskModel
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementação do repositório de tarefas.
 * Responsável por mediar o acesso aos dados entre o Domínio e a Camada Local (Room).
 */
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getTasks(): Flow<List<TaskModel>> {
        return taskDao.getAllTasks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTaskById(id: Int): TaskModel? {
        return taskDao.getTaskById(id)?.toDomain()
    }

    override suspend fun saveTask(task: TaskModel) {
        taskDao.saveTask(task.toEntity())
    }

    override suspend fun deleteTask(task: TaskModel) {
        taskDao.deleteTask(task.toEntity())
    }

    override suspend fun updateTaskStatus(id: Int, isCompleted: Boolean) {
        taskDao.updateStatus(id, isCompleted)
    }

    // Mappers internos para converter entre tipos de dados
    private fun TaskEntity.toDomain() = TaskModel(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted
    )

    private fun TaskModel.toEntity() = TaskEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted
    )
}
