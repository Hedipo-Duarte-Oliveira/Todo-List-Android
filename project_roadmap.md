# Guia de Início do Projeto: To Do List (Android Senior)

Este documento descreve a estratégia de desenvolvimento, arquitetura e padrões para o projeto.

## 1. Arquitetura Proposta: Clean Architecture + MVI + Jetpack Compose
Seguiremos a separação em três camadas principais:

### A. Camada de Dados (Data)
- **Local:** Database Room, DAOs e Entities.
- **Repository Impl:** Implementação da lógica de acesso aos dados.

### B. Camada de Domínio (Domain)
- **Models:** Objetos de negócio puros (Data Classes).
- **Repositories:** Interfaces que definem o contrato de dados.

### C. Camada de Apresentação (UI)
- **Jetpack Compose:** Interface moderna, declarativa e reativa.
- **MVI (Model-View-Intent):**
    - **State:** `TaskListState` (Imutável).
    - **Intent:** `TaskListIntent`.
    - **ViewModel:** Processa intenções e atualiza o estado.
- **Material 3:** Design moderno com suporte a cores dinâmicas.

---

## 2. Estrutura de Diretórios
```text
com.example.todolist/
├── di/                     # Injeção de Dependência (Hilt Modules)
├── data/
│   ├── local/              # Room: AppDatabase.kt, TaskDao.kt, TaskEntity.kt
│   └── repository/         # TaskRepositoryImpl.kt
├── domain/
│   ├── model/              # TaskModel.kt (Domínio)
│   └── repository/         # TaskRepository.kt (Interface)
└── ui/
    ├── task_list/          # Lista de tarefas (Compose)
    │   ├── TaskListScreen.kt
    │   ├── TaskListViewModel.kt
    │   └── TaskListState.kt
    ├── task_add/           # Adição de tarefas (Compose)
    └── theme/              # Tema do Compose (Material 3)
```

---

## 3. Fluxograma de Desenvolvimento Sugerido

1.  **Fase 1: Estrutura Base e Data Layer (Concluído ✅)**
    - Definição da Entidade `TaskEntity` e `TaskDao`.
    - Implementação do `TaskRepositoryImpl`.
    - Configuração do Hilt.

2.  **Fase 2: Infraestrutura de UI e Compose (Concluído ✅)**
    - Configuração do Jetpack Compose e Material 3.
    - Implementação de `MainActivity` com `setContent`.
    - Criação do `ToDoListTheme`.

3.  **Fase 3: Implementação da Listagem e Adição (Concluído ✅)**
    - Implementação de `TaskListScreen` e `TaskAddScreen`.
    - Integração com `TaskListViewModel` e `TaskAddViewModel`.
    - Navegação via Compose Navigation.

4.  **Fase 4: Refinamentos e UX (Próximo Passo ⏳)**
    - Adicionar Diálogo de Confirmação para exclusão.
    - Implementar Filtros (Todas, Pendentes, Concluídas).
    - Melhorar o Estado Vazio (Empty State).

---

## 5. Documentação Técnica: Data Layer

### TaskEntity (`data/local/TaskEntity.kt`)
Representa a tabela `tasks` no SQLite via Room.

### TaskDao (`data/local/TaskDao.kt`)
Contrato de acesso aos dados com Room.
