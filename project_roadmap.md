# Guia de Início do Projeto: To Do List (Android Senior)

Este documento descreve a estratégia de desenvolvimento, arquitetura e padrões para o projeto.

## 1. Arquitetura Proposta: Clean Architecture + MVI
Seguiremos a separação em três camadas principais:

### A. Camada de Dados (Data)
- **Local:** Database Room, DAOs e Entities.
- **Repository Impl:** Implementação da lógica de acesso aos dados.

### B. Camada de Domínio (Domain)
- **Models:** Objetos de negócio puros (Data Classes).
- **Repositories:** Interfaces que definem o contrato de dados.
- **Use Cases:** (Opcional) Lógica específica para cada ação (ex: `GetCompletedTasksUseCase`).

### C. Camada de Apresentação (UI)
- **MVI (Model-View-Intent):**
    - **State:** Um objeto que representa tudo o que a tela mostra.
    - **Intent:** Ações do usuário (ex: `AddTask`, `DeleteTask`).
    - **ViewModel:** Gerencia o estado e processa as intenções.
- **Material 3:** Design moderno com cores dinâmicas.

---

## 2. Estrutura de Diretórios
```text
com.example.todolist/
├── di/                     # Injeção de Dependência (Hilt Modules)
├── data/
│   ├── local/              # Room: AppDatabase.kt, TaskDao.kt, TaskEntity.kt
│   └── repository/         # TaskRepositoryImpl.kt
├── domain/
│   ├── model/              # Task.kt (Data Class pura)
│   └── repository/         # TaskRepository.kt (Interface)
└── ui/
    ├── task_list/          # Lista de tarefas
    │   ├── TaskListFragment.kt
    │   ├── TaskListViewModel.kt
    │   └── TaskListState.kt
    ├── task_detail/        # Criação/Edição
    └── theme/              # Configurações de UI (Material 3)
```

---

## 3. Fluxograma de Desenvolvimento Sugerido

1.  **Fase 1: Estrutura Base e Data Layer (Concluído ✅)**
    - Definição da Entidade `TaskEntity`.
    - Implementação do `TaskDao` com operações CRUD e `Flow`.
    - Organização dos pacotes `data/local`.
    - Implementação do `TaskRepositoryImpl`.
    - Configuração do Hilt (`DatabaseModule`, `TodoApplication`).

2.  **Fase 2: Infraestrutura de UI e Navegação (Concluído ✅)**
    - Configuração de dependências para XML e Fragments.
    - Implementação de `MainActivity` com `NavHostFragment`.
    - Criação do gráfico de navegação (`nav_graph.xml`).
    - Configuração de **ViewBinding**.

3.  **Fase 3: Implementação da Listagem (Concluído ✅)**
    - Definição de Estado e Intenções MVI (`TaskListState`, `TaskListIntent`).
    - Implementação do `TaskListViewModel`.
    - Criação do `TaskListFragment` e `TaskAdapter`.
    - Integração com o Repositório reativo.

4.  **Fase 4: Criação de Tarefas (Próximo Passo ⏳)**
    - Criar `TaskAddFragment` e layout XML.
    - Implementar `TaskAddViewModel` e fluxo MVI.
    - Configurar navegação no `nav_graph.xml`.

---

## 5. Documentação Técnica: Data Layer

### TaskEntity (`data/local/TaskEntity.kt`)
Representa a tabela `tasks` no SQLite via Room.
- `id`: Chave primária autogerada.
- `title`: Título da tarefa.
- `description`: Detalhes.
- `isCompleted`: Status de conclusão.
- `createdAt`: Timestamp para ordenação.

### TaskDao (`data/local/TaskDao.kt`)
Contrato de acesso aos dados com Room.
- Observação reativa via `Flow` para atualizações em tempo real.
- Suporte a operações assíncronas (`suspend`).
- Estratégia de conflito `REPLACE` para simplificar inserções e edições.
