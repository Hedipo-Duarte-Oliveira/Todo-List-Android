# 📝 Todo List Android

> Um aplicativo de lista de tarefas moderno desenvolvido com **Clean Architecture** e as melhores práticas do ecossistema Android.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Hilt](https://img.shields.io/badge/Hilt-4285F4?style=for-the-badge&logo=android&logoColor=white)
![Room](https://img.shields.io/badge/Room%20Database-00897B?style=for-the-badge&logo=database&logoColor=white)

## 📋 Sumário

- [Sobre](#sobre)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Tech Stack](#tech-stack)
- [Testes](#testes)
- [Como Executar](#como-executar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Licença](#licença)

## Sobre

Este projeto foi desenvolvido como estudo aprofundado em arquitetura limpa e desenvolvimento Android moderno. O foco principal é implementar as melhores práticas da indústria, garantindo **escalabilidade**, **testabilidade** e **manutenibilidade**.

## Funcionalidades

✅ **CRUD Completo** - Criar, ler, editar e deletar tarefas  
✅ **Categorias Customizadas** - Organize tarefas com tags coloridas  
✅ **Deadlines** - Defina datas de vencimento com calendário integrado  
✅ **Busca e Filtros** - Encontre tarefas rapidamente  
✅ **Animações Fluidas** - Interface responsiva com Jetpack Compose  
✅ **Dark Mode** - Suporte nativo a Dynamic Colors (Android 12+)  
✅ **Persistência Local** - Dados sincronizados com Room Database  

## Arquitetura

O projeto segue os princípios da **Clean Architecture** organizada em camadas bem definidas:

```
┌─────────────────────────────────────┐
│          UI (Presentation)          │
│    Jetpack Compose + Material 3     │
├─────────────────────────────────────┤
│       ViewModel + MVI Pattern        │
├─────────────────────────────────────┤
│     Domain (Business Logic)         │
│    Use Cases + Repository Pattern    │
├─────────────────────────────────────┤
│     Data (Local & Remote)           │
│   Room Database + Local Storage     │
└─────────────────────────────────────┘
```

## Tech Stack

| Tecnologia | Versão | Propósito |
|-----------|--------|----------|
| **Kotlin** | 1.9+ | Linguagem principal |
| **Jetpack Compose** | Latest | UI declarativa |
| **Material Design 3** | Latest | Design system |
| **Hilt** | Latest | Dependency Injection |
| **Room Database** | Latest | Persistência local |
| **Flow/Coroutines** | Latest | Programação reativa |
| **KSP** | Latest | Kotlin Symbol Processing |
| **JUnit 4** | 4.13+ | Testes unitários |
| **MockK** | Latest | Mock objects |
| **Compose Rule** | Latest | Testes de UI |

## Testes

O projeto possui uma suite abrangente de testes:

- 🧪 **Unit Tests** - Lógica de negócio com JUnit, MockK e Turbine
- 🔗 **Integration Tests** - Testes com Room Database
- 🎨 **UI Tests** - Testes automatizados com Compose Testing

**Total de testes:** 14+ testes ativos com boa cobertura

```bash
# Execute os testes
./gradlew test                    # Testes unitários
./gradlew connectedAndroidTest    # Testes instrumentados
```

## Como Executar

### Pré-requisitos

- Android Studio **Ladybug** ou superior
- JDK **17** ou superior
- Android SDK com API **34+**
- Gradle **8.0+**

### Passos

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Hedipo-Duarte-Oliveira/Todo-List-Android.git
   cd Todo-List-Android
   ```

2. **Abra no Android Studio**
   ```bash
   # Ou simplesmente arraste a pasta para o Android Studio
   ```

3. **Configure o JDK**
   - File → Project Structure → SDK Location
   - Selecione JDK 17+

4. **Execute o projeto**
   - Escolha um emulador ou dispositivo físico (Android 8.0+)
   - Clique em **Run** ou pressione `Shift + F10`

## Estrutura do Projeto

```
app/
├── src/main/
│   ├── java/com/hedipo/todolist/
│   │   ├── presentation/     # UI com Compose
│   │   ├── domain/          # Lógica de negócio
│   │   ├── data/            # Repositórios e Data Sources
│   │   ├── di/              # Dependency Injection (Hilt)
│   │   └── MainActivity.kt
│   └── res/                 # Resources
├── src/test/                # Testes unitários
└── src/androidTest/         # Testes instrumentados
```

## Padrões Utilizados

- 🏗️ **Clean Architecture** - Separação de responsabilidades
- 🎯 **MVI (Model-View-Intent)** - Padrão reativo
- 💉 **Dependency Injection** - Com Hilt
- 📦 **Repository Pattern** - Abstração de dados
- ♻️ **Reactive Programming** - Flow + Coroutines

## Contribuição

Sugestões e melhorias são bem-vindas! Sinta-se à vontade para:

1. Fazer um Fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abrir um Pull Request

## Licença

Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

<div align="center">

**Desenvolvido com ❤️ por [Hedipo Duarte](https://github.com/Hedipo-Duarte-Oliveira)**

[GitHub](https://github.com/Hedipo-Duarte-Oliveira) • [LinkedIn](#) • [Portfolio](#)

⭐ Se esse projeto foi útil para você, considere dar uma estrela!

</div>
