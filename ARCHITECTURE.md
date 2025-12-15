# Arquitetura do Projeto

## Visão Geral

Este documento descreve as decisões arquiteturais tomadas no desenvolvimento da Calculadora de IMC e Métricas de Saúde.

## Padrão Arquitetural: MVVM + Clean Architecture

O projeto adota uma combinação de **MVVM (Model-View-ViewModel)** com princípios de **Clean Architecture**, organizando o código em três camadas principais:

### 1. Camada de Domínio (Domain Layer)
**Responsabilidade**: Contém a lógica de negócios pura, independente de frameworks

#### Componentes:
- **Models**: Classes de dados que representam conceitos do domínio
  - `Measurement`: Representa uma medição completa de saúde
  - `ValidationResult`: Resultado de validação de entradas
  - `Gender`, `ActivityLevel`: Enums para tipos específicos

- **Use Cases**: Encapsulam regras de negócio específicas
  - `CalculateBMIUseCase`: Cálculo de IMC
  - `CalculateBMRUseCase`: Cálculo de Taxa Metabólica Basal
  - `CalculateBodyFatUseCase`: Cálculo de percentual de gordura
  - `CalculateIdealWeightUseCase`: Cálculo de peso ideal
  - `CalculateDailyCaloricNeedsUseCase`: Necessidades calóricas
  - `ValidateInputUseCase`: Validação de entradas do usuário

#### Vantagens:
- ✅ Testabilidade: Lógica de negócio isolada e fácil de testar
- ✅ Reutilização: Use cases podem ser usados em diferentes contextos
- ✅ Independência: Não depende de frameworks ou bibliotecas específicas
- ✅ Clareza: Cada use case tem uma responsabilidade única e bem definida

### 2. Camada de Dados (Data Layer)
**Responsabilidade**: Gerencia a persistência e recuperação de dados

#### Componentes:

**Banco de Dados Local (Room)**
- `MeasurementEntity`: Entidade Room para armazenar medições
- `MeasurementDao`: Interface de acesso a dados com queries SQL
- `AppDatabase`: Configuração do banco de dados Room

**Repositórios**
- `MeasurementRepository`: Interface do repositório (abstração)
- `MeasurementRepositoryImpl`: Implementação concreta usando Room

**Conversores**
- Funções de extensão para converter entre `Entity` ↔ `Model`

#### Vantagens:
- ✅ Abstração: ViewModels não conhecem os detalhes de implementação
- ✅ Flexibilidade: Fácil trocar Room por outra solução sem afetar outras camadas
- ✅ Single Source of Truth: Room garante consistência dos dados
- ✅ Reatividade: Flow permite observação reativa de mudanças

### 3. Camada de Apresentação (Presentation Layer)
**Responsabilidade**: Gerencia a UI e interação com o usuário

#### Componentes:

**ViewModels**
- `HomeViewModel`: Gerencia estado da tela principal
- `HistoryViewModel`: Gerencia estado do histórico
- `DetailViewModel`: Gerencia estado dos detalhes

**Screens (Composables)**
- `HomeScreen`: Tela de entrada de dados e cálculos
- `HistoryScreen`: Tela de listagem de medições
- `DetailScreen`: Tela de detalhes de uma medição

**Components**
- `InputField`: Campo de entrada reutilizável
- `MetricCard`: Card para exibir métricas

**Navigation**
- `NavGraph`: Configuração de navegação
- `Screen`: Definição de rotas

#### Vantagens:
- ✅ Separação de Responsabilidades: UI separada da lógica
- ✅ Estado Reativo: StateFlow permite UI sempre atualizada
- ✅ Sobrevivência a Mudanças de Configuração: ViewModels persistem
- ✅ Reutilização: Componentes podem ser usados em múltiplas telas

## Fluxo de Dados

### Sentido UI → Dados (Escrita)

```
User Input
    ↓
Composable (Screen)
    ↓
Event Handler
    ↓
ViewModel
    ↓
Use Case (validação/cálculo)
    ↓
Repository
    ↓
DAO
    ↓
Room Database
```

### Sentido Dados → UI (Leitura)

```
Room Database
    ↓
DAO (Flow)
    ↓
Repository (Flow)
    ↓
ViewModel (StateFlow)
    ↓
Composable (collectAsState)
    ↓
UI (Recomposição Automática)
```

## Tecnologias e Bibliotecas

### Jetpack Compose
**Por quê?**
- UI declarativa moderna
- Menos código boilerplate
- Preview instantâneo
- Integração nativa com Android
- Mais fácil de testar

### Room Database
**Por quê?**
- Abstração sobre SQLite
- Verificação de queries em tempo de compilação
- Suporte nativo a Flows para observação reativa
- Migrações facilitadas
- Recomendado oficialmente pelo Google

### Navigation Compose
**Por quê?**
- Integração perfeita com Compose
- Type-safe navigation
- Suporte a deep links
- Gerenciamento automático de back stack

### StateFlow
**Por quê?**
- Estado reativo otimizado para UI
- Hot stream (sempre tem um valor)
- Lifecycle-aware
- Substitui LiveData com melhor performance

### Coroutines
**Por quê?**
- Operações assíncronas simplificadas
- Structured concurrency
- Melhor gerenciamento de lifecycle
- Integração nativa com Room e outras libs

## Princípios Aplicados

### SOLID

1. **Single Responsibility Principle (SRP)**
   - Cada Use Case tem uma única responsabilidade
   - ViewModels gerenciam apenas o estado de uma tela
   - Repository apenas gerencia dados

2. **Open/Closed Principle (OCP)**
   - Use Cases são extensíveis via herança
   - Interfaces permitem novas implementações sem modificar código existente

3. **Liskov Substitution Principle (LSP)**
   - Implementações de Repository podem ser substituídas
   - Mock implementations para testes

4. **Interface Segregation Principle (ISP)**
   - Interfaces focadas e específicas
   - Repository expõe apenas métodos necessários

5. **Dependency Inversion Principle (DIP)**
   - ViewModels dependem de abstrações (Repository interface)
   - Não dependem de implementações concretas

### DRY (Don't Repeat Yourself)
- Componentes reutilizáveis (InputField, MetricCard)
- Use Cases compartilhados entre ViewModels
- Funções de conversão entre Entity e Model

### KISS (Keep It Simple, Stupid)
- Lógica clara e direta
- Evita over-engineering
- Código legível e auto-documentado

### YAGNI (You Aren't Gonna Need It)
- Implementa apenas funcionalidades necessárias
- Sem abstrações desnecessárias
- Foco no MVP funcional

## Gerenciamento de Estado

### HomeViewModel State
```kotlin
data class HomeUiState(
    val weight: String = "",
    val height: String = "",
    val age: String = "",
    val gender: Gender? = null,
    // ... outros campos
    val calculationResult: Measurement? = null
)
```

**Vantagens**:
- Estado imutável e previsível
- Facilita testes
- Single source of truth
- Recomposição eficiente

### HistoryViewModel State
```kotlin
sealed class HistoryUiState {
    object Loading : HistoryUiState()
    object Empty : HistoryUiState()
    data class Success(val measurements: List<Measurement>) : HistoryUiState()
    data class Error(val message: String) : HistoryUiState()
}
```

**Vantagens**:
- Representa todos os estados possíveis da tela
- Type-safe
- Força tratamento de todos os casos
- Evita estados inconsistentes

## Validação de Dados

Implementada em duas camadas:

1. **UI Level**: Feedback imediato visual
   - Campos em vermelho
   - Mensagens de erro específicas

2. **Domain Level**: Validação robusta
   - ValidateInputUseCase
   - Limites realistas
   - Prevenção de dados inválidos

## Persistência de Dados

### Estratégia
- **Local First**: Todos os dados são armazenados localmente
- **Automatic Save**: Medições salvas automaticamente após cálculo
- **Histórico Completo**: Todas as medições são mantidas
- **Ordenação**: Por data, mais recente primeiro

### Modelo de Dados

```kotlin
@Entity(tableName = "measurements")
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val weight: Double,
    val height: Double,
    // ... outros campos
)
```

## Navegação

### Estrutura
```
Home Screen (Tela Principal)
    ↓ (Botão Histórico)
History Screen (Lista de Medições)
    ↓ (Clique em Item)
Detail Screen (Detalhes da Medição)
```

### Implementação
- Routes definidas em `Screen` sealed class
- NavGraph centralizado
- Type-safe arguments
- Back stack gerenciado automaticamente

## Testes (Preparado para)

### Unit Tests
- Use Cases são fáceis de testar (lógica pura)
- ViewModels testáveis com coroutines test
- Repository mock para isolar testes

### Integration Tests
- Room com banco em memória
- Repository completo testável

### UI Tests
- Compose testing APIs
- Testes de navegação
- Testes de interação

## Melhorias Futuras na Arquitetura

### Injeção de Dependências
**Hilt ou Koin**
- Gerenciamento automático de dependências
- Scoping adequado (ViewModels, Repository, etc.)
- Facilita testes com mocks

### UseCase com Result Pattern
```kotlin
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}
```

### Multi-módulo
- `:domain` - Lógica de negócios
- `:data` - Persistência
- `:presentation` - UI
- `:app` - Aplicação principal

## Conclusão

A arquitetura escolhida proporciona:
- ✅ **Manutenibilidade**: Fácil adicionar novas funcionalidades
- ✅ **Testabilidade**: Cada camada pode ser testada independentemente
- ✅ **Escalabilidade**: Preparado para crescimento
- ✅ **Clareza**: Código organizado e auto-documentado
- ✅ **Performance**: Recomposição eficiente e operações otimizadas
- ✅ **Boas Práticas**: Seguindo guidelines oficiais do Android

Esta arquitetura foi escolhida por equilibrar simplicidade com robustez, sendo adequada tanto para o tamanho atual do projeto quanto para futuras expansões.
