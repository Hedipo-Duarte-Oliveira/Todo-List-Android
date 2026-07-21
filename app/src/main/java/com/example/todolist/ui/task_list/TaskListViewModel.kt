package com.example.todolist.ui.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que gerencia o estado da lista de tarefas seguindo o padrão MVI.
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TaskListState())
    val state: StateFlow<TaskListState> = _state.asStateFlow()

    init {
        handleIntent(TaskListIntent.LoadTasks)
    }

    /**
     * Processa as intenções do usuário.
     */
    fun handleIntent(intent: TaskListIntent) {
        when (intent) {
            is TaskListIntent.LoadTasks -> fetchTasks()
            is TaskListIntent.ToggleTaskStatus -> updateTaskStatus(intent.taskId, intent.isCompleted)
            is TaskListIntent.DeleteTask -> removeTask(intent.task)
        }
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            repository.getTasks()
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { error -> _state.update { it.copy(isLoading = false, errorMessage = error.message) } }
                .collect { tasks ->
                    _state.update { it.copy(isLoading = false, tasks = tasks, errorMessage = null) }
                }
        }
    }

    private fun updateTaskStatus(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateTaskStatus(id, isCompleted)
        }
    }

    private fun removeTask(task: com.example.todolist.domain.model.TaskModel) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
