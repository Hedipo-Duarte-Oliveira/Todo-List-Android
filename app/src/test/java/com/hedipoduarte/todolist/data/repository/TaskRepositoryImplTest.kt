package com.hedipoduarte.todolist.data.repository

import com.hedipoduarte.todolist.data.local.TaskDao
import com.hedipoduarte.todolist.data.local.TaskEntity
import com.hedipoduarte.todolist.domain.model.TaskCategory
import com.hedipoduarte.todolist.domain.model.TaskModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Testes Unitários para o TaskRepositoryImpl.
 * 
 * POR QUE TESTAR O REPOSITÓRIO?
 * O repositório é a ponte entre o seu mundo de negócios (Domain) e o mundo técnico (Data/Room).
 * O principal objetivo deste teste é garantir que os "Mappers" (conversores) estão funcionando.
 * Se salvarmos uma tarefa com a categoria 'WORK', o banco deve receber a string "WORK".
 * Se o banco retornar uma string inválida, o app deve saber lidar com isso (resiliência).
 */
class TaskRepositoryImplTest {

    /**
     * MOCKK (Dublê de Teste):
     * Não testamos com o banco de dados real aqui (SQLite) porque seria lento.
     * Usamos o `mockk` para criar um DAO "de mentira" que apenas finge que salva e busca dados.
     * `relaxed = true`: Isso permite que o mock não quebre caso chamemos uma função que não 
     * configuramos explicitamente.
     */
    private val taskDao: TaskDao = mockk(relaxed = true)
    
    /**
     * SUT (System Under Test):
     * Esta é a classe real que estamos testando. Ela receberá o DAO "mentiroso" via injeção.
     */
    private lateinit var repository: TaskRepositoryImpl

    /**
     * SETUP:
     * Inicializa o repositório antes de cada teste, garantindo que cada teste rode
     * em uma instância limpa e isolada.
     */
    @Before
    fun setup() {
        repository = TaskRepositoryImpl(taskDao)
    }

    /**
     * CENÁRIO: Buscar todas as tarefas.
     * PARA QUE SERVE: Garantir que a lista que vem do Banco (TaskEntity) é transformada
     * corretamente na lista que a UI entende (TaskModel).
     */
    @Test
    fun `getTasks deve retornar lista de modelos de dominio mapeados corretamente`() = runTest {
        /**
         * 1. ARRANGE (Organizar):
         * Criamos dados no formato do Banco (Entity).
         * Note que no banco a categoria é uma String bruta.
         */
        val entities = listOf(
            TaskEntity(id = 1, title = "T1", description = "D1", category = "WORK"),
            TaskEntity(id = 2, title = "T2", description = "D2", category = "PERSONAL")
        )
        
        /**
         * coEvery: 'co' significa Coroutine.
         * Como o DAO trabalha com Coroutines/Flow, usamos coEvery para dizer ao mock:
         * "Sempre que chamarem getAllTasks, responda com este Flow contendo minhas entidades".
         */
        coEvery { taskDao.getAllTasks() } returns flowOf(entities)

        /**
         * 2. ACT (Agir):
         * Chamamos a função real do repositório.
         * `.first()` pega a primeira emissão do Flow para podermos validar os dados.
         */
        val result = repository.getTasks().first()

        /**
         * 3. ASSERT (Afirmar):
         * Conferimos se a mágica aconteceu.
         * O ID 1 que era Entity agora é um TaskModel com a categoria Enum WORK?
         */
        assertEquals(2, result.size)
        assertEquals("T1", result[0].title)
        assertEquals(TaskCategory.WORK, result[0].category) // Validando se o Mapper converteu String para Enum
        assertEquals("T2", result[1].title)
        assertEquals(TaskCategory.PERSONAL, result[1].category)
    }

    /**
     * CENÁRIO: Salvar uma nova tarefa.
     * PARA QUE SERVE: Garantir que quando a UI envia um objeto limpo (Model),
     * o repositório o "suja" com os detalhes técnicos (Entity) antes de mandar pro Room.
     */
    @Test
    fun `saveTask deve converter modelo de dominio para entidade e chamar o DAO`() = runTest {
        // 1. Arrange: Modelo de Domínio (O que vem da UI)
        val taskModel = TaskModel(title = "Estudar", description = "Kotlin", category = TaskCategory.HEALTH)

        // 2. Act: Executamos a ação de salvar
        repository.saveTask(taskModel)

        /**
         * 3. Assert (coVerify):
         * Aqui não checamos um retorno, mas sim um COMPORTAMENTO.
         * Verificamos se a função `saveTask` do DAO foi realmente chamada e se os dados
         * dentro dela foram convertidos corretamente (ex: Enum HEALTH virou String "HEALTH").
         */
        coVerify { 
            taskDao.saveTask(match { 
                it.title == "Estudar" && it.category == "HEALTH" 
            }) 
        }
    }

    /**
     * CENÁRIO: Buscar tarefa por ID quando ela não existe.
     * PARA QUE SERVE: Garantir a resiliência do app. O repositório não deve quebrar
     * se o banco retornar nulo; ele deve apenas repassar o nulo com segurança.
     */
    @Test
    fun `getTaskById deve retornar null quando o DAO nao encontrar a tarefa`() = runTest {
        // 1. Arrange: Configuramos o DAO para retornar nulo para qualquer ID
        coEvery { taskDao.getTaskById(any()) } returns null

        // 2. Act: Tentamos buscar o ID 99
        val result = repository.getTaskById(99)

        // 3. Assert: O resultado deve ser nulo, sem exceptions.
        assertEquals(null, result)
    }
}
