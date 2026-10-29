# Cuye List
<div align="center">
<img width="200" height="200" alt="cuyes" src="https://github.com/user-attachments/assets/f5065b7b-153a-4ec6-80f5-fe8683457e39" />
</div>

**Cuye List** é um aplicativo Android para anotar, priorizar e acompanhar seus recados (notas e tarefas), feito com Jetpack Compose.

<p>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?logo=kotlin&logoColor=white" />
  <img alt="Android" src="https://img.shields.io/badge/Android-minSdk%2024-3DDC84?logo=android&logoColor=white" />
  <img alt="Jetpack Compose" src="https://img.shields.io/badge/Jetpack%20Compose-UI-4285F4?logo=jetpackcompose&logoColor=white" />
  <img alt="Material 3" src="https://img.shields.io/badge/Material%203-Design-757575?logo=materialdesign&logoColor=white" />
  <img alt="Gradle" src="https://img.shields.io/badge/Gradle-Kotlin%20DSL-02303A?logo=gradle&logoColor=white" />
  <img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-IDE-3DDC84?logo=androidstudio&logoColor=white" />
  <img alt="JUnit" src="https://img.shields.io/badge/JUnit-4-25A162?logo=junit5&logoColor=white" />
</p>

- **Kotlin** + **Jetpack Compose** com **Material 3** para a interface.
- **Navigation Compose** para a navegação e **ViewModel** para o estado das telas.
- **SharedPreferences + JSON** para a persistência local.
- **Gradle (Kotlin DSL)** com catálogo de versões e **JUnit** para os testes unitários.

## Recursos

- Criar recados com **título**, **detalhes** opcionais e **prioridade** (baixa, média, alta).
- Marcar como concluído, **editar** e **excluir** (com opção de **desfazer**).
- **Busca** por título e detalhes, e filtros **Todos / Pendentes / Concluídos**.
- Ordenação automática: pendentes primeiro, depois prioridade e mais recentes.
- **Barra de progresso** com o total de recados concluídos.
- **Limpar concluídos** de uma só vez (também com desfazer).
- Visual novo: cabeçalho com gradiente e progresso, cards com faixa de prioridade, folha inferior para criar/editar e **deslizar para excluir**.
- **Compartilhar** um recado com outros apps.
- **Persistência local**: os recados continuam salvos ao fechar o app.

## Arquitetura

O projeto usa uma arquitetura simples orientada por responsabilidade (UI → ViewModel → repositório):

```text
app/src/main/java/com/cuyelist/
├── MainActivity.kt
├── data/
│   └── RecadoRepository.kt        ➞ persistência (SharedPreferences + JSON)
├── model/
│   └── Recado.kt                  ➞ Recado, Priority, RecadoFilter e regras de busca/ordenação
├── navigation/
│   └── AppNavigation.kt           ➞ rotas e navegação
└── ui/
    ├── screens/
    │   ├── home/HomeScreen.kt     ➞ tela inicial (mascote, resumo e atalho para os recados)
    │   └── recado/
    │       ├── RecadoScreen.kt    ➞ tela da lista: busca, filtros, swipe para excluir e desfazer
    │       ├── RecadoComponents.kt➞ cabeçalho com progresso, cards, estado vazio e folha de criar/editar
    │       └── RecadoViewModel.kt ➞ estado da tela e escrita no repositório
    └── theme/                     ➞ cores, tipografia e tema Material 3
```

### Responsabilidades

- `MainActivity`: ponto de entrada Android. Inicializa o tema e chama a navegação.
- `navigation`: define as rotas e conecta as telas, mantendo a navegação fora da UI.
- `model`: modelos de dados e regras puras (filtro, busca e ordenação), cobertas por testes unitários.
- `data`: salva e carrega os recados localmente.
- `ui/screens`: telas Compose organizadas por fluxo; o estado vive no `ViewModel`.
- `ui/theme`: concentra cores, tipografia e configuração do tema Material 3.

## Fluxo da aplicação

```mermaid
flowchart TD
    A[MainActivity] --> B[CuyeListTheme]
    B --> C[AppNavigation]
    C --> D[HomeScreen]
    D -->|Meus recados| E[RecadoScreen]
    E -->|Voltar| D
    E --> F[RecadoViewModel]
    F --> G[RecadoRepository]
    F --> H[Recado]
```

## Convenções

- Pacote-base: `com.cuyelist`.
- Pacotes e arquivos usam nomes em minúsculas; componentes Compose usam `PascalCase`.
- Cada tela recebe callbacks de navegação, evitando que a UI conheça detalhes do `NavController`.
- Rotas ficam centralizadas em `navigation/AppNavigation.kt`.
- Novas telas devem ser adicionadas em `ui/screens/<fluxo>/`.
- Modelos e regras puras ficam em `model/`; acesso a armazenamento em `data/`.

## Como executar

Abra o projeto no Android Studio ou execute:

```bash
./gradlew :app:assembleDebug
```

Para validar apenas a compilação Kotlin:

```bash
./gradlew :app:compileDebugKotlin
```


Para rodar os testes unitários:

```bash
./gradlew :app:testDebugUnitTest
```
