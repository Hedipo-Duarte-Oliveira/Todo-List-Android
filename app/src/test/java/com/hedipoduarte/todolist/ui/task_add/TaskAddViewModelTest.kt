package com.hedipoduarte.todolist.ui.task_add

import app.cash.turbine.test
import com.hedipoduarte.todolist.domain.model.TaskCategory
import com.hedipoduarte.todolist.domain.model.TaskModel
import com.hedipoduarte.todolist.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
 * TESTES PARA ADIÇÃO E EDIÇÃO DE TAREFAS
 * 
 * POR QUE TESTAR ESTA CLASSE?
 * Esta classe contém validações críticas (como impedir títulos vazios) e decide se o app deve
 * voltar para a tela anterior após salvar. Testar aqui evita que bugs de formulário cheguem ao usuário.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TaskAddViewModelTest {

    /**
     * MOCKK (O "Fake"):
     * `relaxed = true`: É uma configuração do Mockk que diz: "Se chamarem uma função que eu não
     * configurei, apenas retorne um valor padrão e não quebre o teste". Isso é ótimo para
     * funções que não retornam nada (Unit).
     */
    private val repository: TaskRepository = mockk(relaxed = true)
    
    private lateinit var viewModel: TaskAddViewModel

    /**
     * DISPATCHER (O "Maestro"):
     * Permite que controlemos como o tempo passa dentro do teste. Sem ele, o teste rodaria
     * mais rápido que o processamento do ViewModel, resultando em falsos negativos.
     */
    private val testDispatcher = StandardTestDispatcher()

    /**
     * SETUP:
     * Prepara o terreno antes de cada teste unitário.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TaskAddViewModel(repository)
    }

    /**
     * TEARDOWN:
     * Limpa as configurações após a execução de cada teste.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CENÁRIO: Usuário tenta salvar sem digitar o título.
     * PARA QUE SERVE: Validar que a regra de negócio de "campo obrigatório" está funcionando.
     */
    @Test
    fun `quando o titulo esta em branco, salvar tarefa deve atualizar estado com erro`() = runTest {
        // 1. Arrange: Simulamos que o título foi apagado
        viewModel.handleIntent(TaskAddIntent.OnTitleChanged(""))

        // 2. Act: Tentamos executar a ação de salvar
        viewModel.handleIntent(TaskAddIntent.SaveTask)

        /**
         * 3. Assert (Turbine):
         * Espiamos o fluxo de estado da tela.
         * Verificamos se a mensagem de erro correta apareceu no estado.
         */
        viewModel.state.test {
            val state = awaitItem() 
            assertEquals("O título não pode estar vazio", state.errorMessage)
            
            /**
             * coVerify:
             * Garante que o banco de dados NÃO foi chamado (exactly = 0).
             * Se o título está errado, não devemos nem tentar salvar.
             */
            coVerify(exactly = 0) { repository.saveTask(any()) }
        }
    }

    /**
     * CENÁRIO: Usuário salva uma tarefa válida.
     * PARA QUE SERVE: Garantir o caminho feliz de sucesso do aplicativo.
     */
    @Test
    fun `quando uma tarefa valida e fornecida, salvar tarefa deve chamar o repositorio e marcar sucesso`() = runTest {
        // 1. Arrange: Preenchemos os campos com dados corretos
        val title = "Nova Tarefa"
        val description = "Descrição de teste"
        viewModel.handleIntent(TaskAddIntent.OnTitleChanged(title))
        viewModel.handleIntent(TaskAddIntent.OnDescriptionChanged(description))

        // 2. Act: Clicamos em salvar
        viewModel.handleIntent(TaskAddIntent.SaveTask)
        
        /**
         * advanceUntilIdle():
         * Como salvar é uma operação assíncrona, forçamos o relógio do teste a 
         * avançar até que o banco termine de processar.
         */
        advanceUntilIdle() 

        // 3. Assert: Validamos o resultado final
        viewModel.state.test {
            val state = awaitItem()
            // O sinal de 'isSaved' deve ser verdadeiro para fechar a tela
            assertEquals(true, state.isSaved)
            
            // coVerify: Checamos se o repositório recebeu exatamente o que digitamos
            coVerify(exactly = 1) { 
                repository.saveTask(match { it.title == title && it.description == description }) 
            }
        }
    }

    /**
     * CENÁRIO: Carregamento de uma tarefa para edição.
     * PARA QUE SERVE: Garantir que quando clicamos em uma tarefa na lista, 
     * os campos da tela de edição abram já preenchidos corretamente.
     */
    @Test
    fun `quando carregar tarefa e chamado, o estado deve ser atualizado com os dados existentes`() = runTest {
        // 1. Arrange: Configuramos o Mock para devolver uma tarefa específica
        val taskId = 10
        val existingTask = TaskModel(
            id = taskId,
            title = "Tarefa Existente",
            description = "Desc",
            category = TaskCategory.WORK
        )
        coEvery { repository.getTaskById(taskId) } returns existingTask

        // 2. Act: Disparamos a intenção de carregar pelo ID
        viewModel.handleIntent(TaskAddIntent.LoadTask(taskId))
        advanceUntilIdle()

        // 3. Assert: Verificamos se o ViewModel repassou os dados para o estado da UI
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(existingTask.title, state.title)
            assertEquals(existingTask.description, state.description)
            assertEquals(existingTask.category, state.category)
            assertEquals(taskId, state.taskId)
        }
    }
}
