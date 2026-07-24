package com.hedipoduarte.todolist.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TESTES INSTRUMENTADOS DO BANCO DE DADOS (ROOM)
 * 
 * POR QUE TESTAR O DAO NO DISPOSITIVO REAL?
 * Diferente da lógica de negócio pura, o DAO executa SQL no motor SQLite do Android. 
 * Testar aqui garante que suas queries (@Query) e restrições de banco (como Chaves Primárias)
 * funcionam exatamente como o usuário final experimentará, o que não pode ser simulado 100% na JVM.
 */
@RunWith(AndroidJUnit4::class) // Define que o teste rodará no ambiente Android
class TaskDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var taskDao: TaskDao

    /**
     * SETUP: Executado antes de CADA teste.
     * 
     * O QUE É O IN-MEMORY DATABASE?
     * Usamos o `inMemoryDatabaseBuilder`. Isso cria o banco na memória RAM.
     * VANTAGEM: Ele é destruído assim que o processo termina, garantindo que o teste
     * seja rápido e não deixe "lixo" no banco real do seu aplicativo.
     * 
     * POR QUE allowMainThreadQueries()?
     * Em testes instrumentados, às vezes queremos simplificar a execução. 
     * Isso permite rodar o banco na thread principal apenas para validação rápida.
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries() 
         .build()
        
        taskDao = database.taskDao()
    }

    /**
     * TEARDOWN: Executado após CADA teste.
     * 
     * Fecha a conexão com o banco para liberar memória e evitar vazamentos (memory leaks).
     */
    @After
    fun closeDb() {
        database.close()
    }

    /**
     * CENÁRIO: Inserção e Leitura básica.
     * OBJETIVO: Validar se o mapeamento entre o objeto Kotlin e a tabela SQL está íntegro.
     */
    @Test
    fun insertTaskAndGetAllTasks() = runTest {
        // 1. Arrange: Cria uma entidade fake
        val task = TaskEntity(
            id = 1,
            title = "Teste de Integração",
            description = "Validando SQLite",
            category = "WORK"
        )

        // 2. Act: Salva e recupera
        taskDao.saveTask(task)
        
        /**
         * Flow.first():
         * Como o DAO retorna um Flow reativo, usamos .first() para "tirar uma foto"
         * da primeira lista que o banco emitir.
         */
        val allTasks = taskDao.getAllTasks().first()

        // 3. Assert: O dado gravado deve ser idêntico ao recuperado
        assertEquals(1, allTasks.size)
        assertEquals("Teste de Integração", allTasks[0].title)
    }

    /**
     * CENÁRIO: Atualização de campo específico.
     * OBJETIVO: Garantir que a @Query("UPDATE...") personalizada altere apenas o necessário.
     */
    @Test
    fun updateTaskStatusChangesBooleanInDatabase() = runTest {
        // Arrange: Começa com tarefa pendente (false)
        val task = TaskEntity(id = 1, title = "Original", description = "", isCompleted = false)
        taskDao.saveTask(task)

        // Act: Executa o comando de atualização de status
        taskDao.updateStatus(1, true)
        val updatedTask = taskDao.getAllTasks().first()[0]

        // Assert: Verifica se o banco agora reflete a conclusão
        assertTrue(updatedTask.isCompleted)
    }

    /**
     * CENÁRIO: Conflito de Chave Primária (Duplicate ID).
     * OBJETIVO: Validar nossa estratégia 'OnConflictStrategy.REPLACE'. 
     * Se tentarmos inserir o mesmo ID duas vezes, ele deve sobrescrever em vez de dar erro.
     */
    @Test
    fun saveTaskWithSameIdShouldReplaceOldData() = runTest {
        // 1. Salva a versão 1
        val task1 = TaskEntity(id = 10, title = "Versão 1", description = "Antiga")
        taskDao.saveTask(task1)

        // 2. Salva a versão 2 com o MESMO ID
        val task2 = TaskEntity(id = 10, title = "Versão 2", description = "Editada")
        taskDao.saveTask(task2)

        val allTasks = taskDao.getAllTasks().first()

        // 3. O banco deve conter apenas 1 registro e deve ser a "Versão 2"
        assertEquals(1, allTasks.size)
        assertEquals("Versão 2", allTasks[0].title)
    }
}
