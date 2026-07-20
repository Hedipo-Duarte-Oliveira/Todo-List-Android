package com.example.todolist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe de Application necessária para o funcionamento do Hilt.
 * Responsável por iniciar a geração de código da árvore de dependências.
 */
@HiltAndroidApp
class TodoApplication : Application()
