# 📝 ToDo List - Android Modern Architecture

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)

Um aplicativo de lista de tarefas robusto, desenvolvido com as práticas mais modernas do ecossistema Android. Este projeto foi focado em **escalabilidade**, **testabilidade** e **experiência do usuário (UX)**.

---

## 🚀 Funcionalidades

- [x] **CRUD Completo**: Criação, leitura, edição e exclusão de tarefas.
- [x] **Categorias Customizadas**: Organize suas tarefas por tags coloridas (Trabalho, Pessoal, Saúde, etc).
- [x] **Prazos (Deadlines)**: Definição de datas com seletores visuais modernos.
- [x] **Busca e Filtros**: Encontre tarefas rapidamente por texto ou status (Pendentes/Concluídas).
- [x] **Animações Fluidas**: Transições suaves e feedback visual tátil usando Jetpack Compose.
- [x] **Splash Screen Moderna**: Experiência de abertura fluida seguindo os padrões do Android 12+.

---

## 🛠 Tech Stack & Arquitetura

Este projeto segue os princípios da **Clean Architecture** dividida em camadas:

- **UI**: Jetpack Compose com **Material 3** e suporte a **Dynamic Colors**.
- **Padrão de Apresentação**: **MVI (Model-View-Intent)** para um estado de tela previsível e reativo.
- **Dependency Injection**: **Hilt** para uma injeção de dependência desacoplada.
- **Local Database**: **Room Database** com suporte a fluxos reativos (`Flow`).
- **Processing**: **KSP (Kotlin Symbol Processing)** para compilação otimizada.

---

## 🧪 Qualidade de Código (Testes)

O projeto possui uma suite de testes rigorosa com **14 testes ativos**:

- **Unit Tests**: Validação da lógica de negócio nos ViewModels e Repositórios (JUnit, MockK, Turbine).
- **Integration Tests**: Testes do banco de dados SQLite no dispositivo real.
- **UI Tests**: Testes automatizados de interface com Compose Rule.

---

## 📸 Demonstração

| Lista de Tarefas | Cadastro & Edição | Filtros & Busca |
| :---: | :---: | :---: |
| <img src="art/list_screen.png" width="200" /> | <img src="art/add_screen.png" width="200" /> | <img src="art/search_animation.gif" width="200" /> |

*(Nota: Adicione as imagens na pasta `/art` do seu repositório)*

---

## ⚙️ Como executar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/Hedipo-Duarte-Oliveira/Todo-List-Android.git
   ```
2. Abra no **Android Studio (Ladybug ou superior)**.
3. Certifique-se de que o **JDK 17** está selecionado nas configurações do Gradle.
4. Execute o projeto em um emulador ou dispositivo físico com Android 8.0+.

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---
Desenvolvido com ❤️ por [Hedipo Duarte](https://github.com/Hedipo-Duarte-Oliveira)
