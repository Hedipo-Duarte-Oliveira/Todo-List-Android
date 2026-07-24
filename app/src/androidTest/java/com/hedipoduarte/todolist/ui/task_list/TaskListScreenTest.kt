package com.hedipoduarte.todolist.ui.task_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.hedipoduarte.todolist.domain.model.TaskModel
import com.hedipoduarte.todolist.ui.theme.ToDoListTheme
import org.junit.Rule
import org.junit.Test

/**
 * TESTES DE UI COM JETPACK COMPOSE
 * 
 * Como um desenvolvedor Senior, você deve separar a lógica da visualização.
 * Aqui testamos se os componentes visuais reagem corretamente a diferentes estados,
 * sem precisar de um ViewModel real (usamos estados fixos).
 */
class TaskListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * CENÁRIO: Lista sem nenhuma tarefa.
     * VERIFICAÇÃO: Se o texto amigável de "vazio" aparece para o usuário.
     */
    @Test
    fun whenListIsEmpty_shouldShowNoTasksMessage() {
        // Arrange
        val emptyState = TaskListState(tasks = emptyList(), isLoading = false)

        // Act
        composeTestRule.setContent {
            ToDoListTheme {
                TaskListScreenContent(
                    state = emptyState,
                    onNavigateToAddTask = {},
                    onNavigateToEditTask = {},
                    onIntent = {}
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithText("Nenhuma tarefa encontrada").assertIsDisplayed()
    }

    /**
     * CENÁRIO: Lista com tarefas carregadas.
     * VERIFICAÇÃO: Se o título da tarefa aparece no Card.
     */
    @Test
    fun whenListHasTasks_shouldDisplayThem() {
        // Arrange
        val tasks = listOf(TaskModel(id = 1, title = "Aprender Teste UI", description = "Compose Rule"))
        val state = TaskListState(tasks = tasks, filteredTasks = tasks, isLoading = false)

        // Act
        composeTestRule.setContent {
            ToDoListTheme {
                TaskListScreenContent(
                    state = state,
                    onNavigateToAddTask = {},
                    onNavigateToEditTask = {},
                    onIntent = {}
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithText("Aprender Teste UI").assertIsDisplayed()
    }

    /**
     * CENÁRIO: Clique no botão flutuante (FAB).
     * VERIFICAÇÃO: Se a função de navegação para a tela de cadastro é chamada.
     */
    @Test
    fun whenFabIsClicked_shouldTriggerNavigation() {
        var navigated = false

        // Act
        composeTestRule.setContent {
            ToDoListTheme {
                TaskListScreenContent(
                    state = TaskListState(),
                    onNavigateToAddTask = { navigated = true },
                    onNavigateToEditTask = {},
                    onIntent = {}
                )
            }
        }

        // Simula o clique
        composeTestRule.onNodeWithContentDescription("Adicionar Tarefa").performClick()

        // Assert
        assert(navigated)
    }
}
