package com.example.todolist.ui.task_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.model.TaskModel
import com.example.todolist.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para a tela de adição de tarefas.
 */
@HiltViewModel
class TaskAddViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TaskAddState())
    val state = _state.asStateFlow()

    fun handleIntent(intent: TaskAddIntent) {
        when (intent) {
            is TaskAddIntent.OnTitleChanged -> _state.update { it.copy(title = intent.title) }
            is TaskAddIntent.OnDescriptionChanged -> _state.update { it.copy(description = intent.description) }
            TaskAddIntent.SaveTask -> saveTask()
        }
    }

    private fun saveTask() {
        val title = _state.value.title
        if (title.isBlank()) {
            _state.update { it.copy(errorMessage = "O título não pode estar vazio") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                repository.saveTask(
                    TaskModel(
                        title = title,
                        description = _state.value.description
                    )
                )
                _state.update { it.copy(isSaved = true, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}
