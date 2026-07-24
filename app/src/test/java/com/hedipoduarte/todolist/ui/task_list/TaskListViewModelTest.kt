package com.hedipoduarte.todolist.ui.task_list

import app.cash.turbine.test
import com.hedipoduarte.todolist.domain.model.TaskModel
import com.hedipoduarte.todolist.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Suite de Testes Unitários para a Lista de Tarefas.
 * Agora cobrindo filtros, exclusão e tratamento de erros.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TaskListViewModelTest {

    private val repository: TaskRepository = mockk()
    private lateinit var viewModel: TaskListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `quando carregar tarefas, deve exibir lista completa inicial`() = runTest {
        val mockTasks = listOf(
            TaskModel(id = 1, title = "T1", description = "D1", isCompleted = false),
            TaskModel(id = 2, title = "T2", description = "D2", isCompleted = true)
        )
        coEvery { repository.getTasks() } returns flowOf(mockTasks)

        viewModel = TaskListViewModel(repository)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(mockTasks, state.tasks)
            assertEquals(mockTasks, state.filteredTasks)
        }
    }

    @Test
    fun `quando selecionar filtro COMPLETED, deve mostrar apenas tarefas concluidas`() = runTest {
        val tasks = listOf(
            TaskModel(id = 1, title = "Pendente", description = "", isCompleted = false),
            TaskModel(id = 2, title = "Concluida", description = "", isCompleted = true)
        )
        coEvery { repository.getTasks() } returns flowOf(tasks)
        viewModel = TaskListViewModel(repository)
        advanceUntilIdle()

        viewModel.handleIntent(TaskListIntent.FilterTasks(TaskFilter.COMPLETED))

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(1, state.filteredTasks.size)
            assertEquals(true, state.filteredTasks[0].isCompleted)
            assertEquals(TaskFilter.COMPLETED, state.selectedFilter)
        }
    }

    @Test
    fun `quando clicar em excluir, deve mostrar dialogo de confirmacao`() = runTest {
        coEvery { repository.getTasks() } returns flowOf(emptyList())
        viewModel = TaskListViewModel(repository)
        val taskToDelete = TaskModel(id = 1, title = "Apagar", description = "")

        viewModel.handleIntent(TaskListIntent.ShowDeleteConfirmation(taskToDelete))

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(taskToDelete, state.taskToDelete)
        }
    }

    @Test
    fun `quando o repositorio falhar, deve atualizar estado com mensagem de erro`() = runTest {
        val errorMessage = "Erro de conexão"
        // Simulando um Flow que lança erro
        coEvery { repository.getTasks() } returns flow { throw Exception(errorMessage) }

        viewModel = TaskListViewModel(repository)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.errorMessage)
            assertEquals(false, state.isLoading)
        }
    }
}
