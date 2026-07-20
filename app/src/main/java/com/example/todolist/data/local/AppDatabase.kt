package com.example.todolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Ponto de acesso principal ao banco de dados SQLite do aplicativo.
 * Gerencia a versão do esquema e fornece acesso aos DAOs.
 */
@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Fornece acesso às operações de dados da tabela de tarefas.
     */
    abstract fun taskDao(): TaskDao
}
