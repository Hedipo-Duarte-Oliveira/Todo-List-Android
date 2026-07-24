package com.hedipoduarte.todolist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define o contrato de acesso ao banco de dados SQLite.
 * Seguindo o padrão DAO (Data Access Object) do Room.
 */
@Dao
interface TaskDao {

    /**
     * Retorna um Flow que emite uma nova lista sempre que houver mudanças no banco.
     * O uso de Flow garante que a UI seja reativa e sempre exiba dados atualizados.
     */
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    /**
     * Busca uma tarefa específica pelo seu ID único.
     */
    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity?

    /**
     * Insere uma nova tarefa ou substitui uma existente caso o ID seja o mesmo.
     * 'suspend' garante que a operação seja executada fora da Main Thread para não travar o app.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: TaskEntity)

    /**
     * Atualização parcial otimizada para mudar apenas o status de conclusão.
     * Evita a necessidade de carregar o objeto inteiro na memória para uma alteração simples.
     */
    @Query("UPDATE tasks SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateStatus(id: Int, isCompleted: Boolean)

    /**
     * Remove fisicamente o registro da tabela 'tasks'.
     */
    @Delete
    suspend fun deleteTask(task: TaskEntity)
}
