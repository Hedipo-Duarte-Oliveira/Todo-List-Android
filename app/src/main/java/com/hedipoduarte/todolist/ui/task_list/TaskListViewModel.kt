package com.hedipoduarte.todolist.ui.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hedipoduarte.todolist.domain.model.TaskModel
import com.hedipoduarte.todolist.domain.repository.TaskRepository
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
            is TaskListIntent.ShowDeleteConfirmation -> _state.update { it.copy(taskToDelete = intent.task) }
            is TaskListIntent.HideDeleteConfirmation -> _state.update { it.copy(taskToDelete = null) }
            is TaskListIntent.FilterTasks -> updateFilter(intent.filter)
            is TaskListIntent.SearchTasks -> updateSearchQuery(intent.query)
        }
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            repository.getTasks()
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { error -> _state.update { it.copy(isLoading = false, errorMessage = error.message) } }
                .collect { tasks ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            tasks = tasks,
                            filteredTasks = applyFilterAndSearch(tasks, it.selectedFilter, it.searchQuery),
                            errorMessage = null
                        )
                    }
                }
        }
    }

    private fun updateFilter(filter: TaskFilter) {
        _state.update {
            it.copy(
                selectedFilter = filter,
                filteredTasks = applyFilterAndSearch(it.tasks, filter, it.searchQuery)
            )
        }
    }

    private fun updateSearchQuery(query: String) {
        _state.update {
            it.copy(
                searchQuery = query,
                filteredTasks = applyFilterAndSearch(it.tasks, it.selectedFilter, query)
            )
        }
    }

    private fun applyFilterAndSearch(
        tasks: List<TaskModel>,
        filter: TaskFilter,
        query: String
    ): List<TaskModel> {
        val filteredByStatus = when (filter) {
            TaskFilter.ALL -> tasks
            TaskFilter.PENDING -> tasks.filter { !it.isCompleted }
            TaskFilter.COMPLETED -> tasks.filter { it.isCompleted }
        }
        
        return if (query.isBlank()) {
            filteredByStatus
        } else {
            filteredByStatus.filter { 
                it.title.contains(query, ignoreCase = true) || 
                it.description.contains(query, ignoreCase = true) 
            }
        }
    }

    private fun updateTaskStatus(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateTaskStatus(id, isCompleted)
        }
    }

    private fun removeTask(task: TaskModel) {
        viewModelScope.launch {
            repository.deleteTask(task)
            _state.update { it.copy(taskToDelete = null) }
        }
    }
}
