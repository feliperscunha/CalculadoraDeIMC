# Calculadora de IMC e Métricas de Saúde

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)

Aplicativo Android moderno para cálculo de IMC (Índice de Massa Corporal) e outras métricas importantes de saúde, desenvolvido com as melhores práticas de desenvolvimento Android.

## 📋 Índice

- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Cálculos Implementados](#-cálculos-implementados)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como Usar](#-como-usar)
- [Requisitos](#-requisitos)
- [Instalação](#-instalação)
- [Melhorias Futuras](#-melhorias-futuras)
- [Licença](#-licença)

## ✨ Funcionalidades

### Cálculos de Saúde

O aplicativo oferece os seguintes cálculos:

1. **IMC (Índice de Massa Corporal)**
   - Classificação baseada nos padrões da OMS
   - Interpretação detalhada dos resultados

2. **TMB (Taxa Metabólica Basal)**
   - Fórmula Mifflin-St Jeor (mais precisa)
   - Estimativa de calorias em repouso

3. **Peso Ideal**
   - Fórmula de Devine
   - Comparação com peso atual

4. **Percentual de Gordura Corporal**
   - Método da Marinha dos EUA
   - Classificação por categoria

5. **Necessidade Calórica Diária**
   - Baseada na TMB e nível de atividade
   - Recomendações para manutenção, perda e ganho de peso

### Recursos do Aplicativo

- ✅ **Validação de Entrada**: Previne valores inválidos ou fora de limites razoáveis
- ✅ **Persistência Local**: Histórico completo de medições com Room Database
- ✅ **Navegação Intuitiva**: Navegação fluida entre telas
- ✅ **Tela de Histórico**: Lista todas as medições ordenadas por data
- ✅ **Tela de Detalhes**: Visualização completa de uma medição específica
- ✅ **Interpretações**: Textos explicativos para cada métrica
- ✅ **Interface Moderna**: UI desenvolvida com Jetpack Compose
- ✅ **Material Design 3**: Seguindo as diretrizes do Material You

## 🛠 Tecnologias

### Core
- **Kotlin**: Linguagem principal
- **Jetpack Compose**: Framework de UI moderna e declarativa
- **Material Design 3**: Sistema de design moderno

### Arquitetura e Padrões
- **MVVM**: Model-View-ViewModel para separação de responsabilidades
- **Clean Architecture**: Organização em camadas (Domain, Data, Presentation)
- **Use Cases**: Encapsulamento da lógica de negócios
- **Repository Pattern**: Abstração da camada de dados

### Bibliotecas Android
- **Room**: Persistência local de dados
- **Navigation Compose**: Navegação entre telas
- **Lifecycle**: Gerenciamento de ciclo de vida
- **ViewModel**: Gerenciamento de estado
- **StateFlow**: Observação de estados reativos
- **Coroutines**: Programação assíncrona
- **KSP**: Processamento de anotações

## 🏗 Arquitetura

O projeto segue a arquitetura **MVVM** com **Clean Architecture**, organizada em camadas:

```
app/
├── domain/                 # Camada de Domínio (Regras de Negócio)
│   ├── model/             # Modelos de domínio
│   │   ├── Measurement.kt
│   │   └── ValidationResult.kt
│   └── usecase/           # Casos de uso (lógica de negócio)
│       ├── CalculateBMIUseCase.kt
│       ├── CalculateBMRUseCase.kt
│       ├── CalculateBodyFatUseCase.kt
│       ├── CalculateIdealWeightUseCase.kt
│       ├── CalculateDailyCaloricNeedsUseCase.kt
│       └── ValidateInputUseCase.kt
│
├── data/                   # Camada de Dados
│   ├── local/
│   │   ├── entity/        # Entidades Room
│   │   │   └── MeasurementEntity.kt
│   │   ├── dao/           # Data Access Objects
│   │   │   └── MeasurementDao.kt
│   │   └── database/      # Configuração do banco
│   │       └── AppDatabase.kt
│   └── repository/        # Implementação de repositórios
│       ├── MeasurementRepository.kt
│       └── MeasurementRepositoryImpl.kt
│
└── presentation/           # Camada de Apresentação (UI)
    ├── viewmodel/         # ViewModels
    │   ├── HomeViewModel.kt
    │   ├── HistoryViewModel.kt
    │   └── DetailViewModel.kt
    ├── screen/            # Telas Composables
    │   ├── HomeScreen.kt
    │   ├── HistoryScreen.kt
    │   └── DetailScreen.kt
    ├── components/        # Componentes reutilizáveis
    │   ├── InputField.kt
    │   └── MetricCard.kt
    └── navigation/        # Navegação
        ├── NavGraph.kt
        └── Screen.kt
```

### Fluxo de Dados

```
UI (Composables) → ViewModel → Use Case → Repository → Room Database
                      ↓
                  StateFlow
                      ↓
                UI (Recomposição)
```

## 📊 Cálculos Implementados

Todos os cálculos seguem fórmulas cientificamente validadas e amplamente aceitas. Consulte o arquivo [FORMULAS.md](FORMULAS.md) para detalhes completos sobre cada fórmula, incluindo referências bibliográficas.

### Resumo dos Cálculos

| Métrica | Fórmula | Requisitos |
|---------|---------|------------|
| IMC | peso / altura² | Peso, Altura |
| TMB | Mifflin-St Jeor | Peso, Altura, Idade, Sexo |
| Peso Ideal | Devine | Altura, Sexo |
| % Gordura | Navy Method | Cintura, Pescoço, Altura, Sexo (+ Quadril para mulheres) |
| Calorias Diárias | TMB × Fator Atividade | TMB, Nível de Atividade |

## 📱 Estrutura do Projeto

### Telas

1. **Tela Principal (Home)**
   - Entrada de dados básicos (peso, altura)
   - Dados opcionais (idade, sexo, circunferências, nível de atividade)
   - Cálculo e exibição de resultados
   - Navegação para histórico

2. **Tela de Histórico**
   - Lista de todas as medições
   - Ordenação por data (mais recente primeiro)
   - Navegação para detalhes
   - Opção de limpar histórico

3. **Tela de Detalhes**
   - Visualização completa de uma medição
   - Todos os indicadores calculados
   - Interpretações detalhadas

### Componentes Reutilizáveis

- **InputField**: Campo de entrada com validação
- **MetricCard**: Card para exibição de métricas

## 🎯 Como Usar

### Calculando Métricas

1. **Medidas Básicas (Obrigatórias)**
   - Insira sua altura em centímetros
   - Insira seu peso em quilogramas
   - Clique em "CALCULAR"

2. **Dados Adicionais (Opcionais)**
   - Para cálculos avançados, preencha:
     - Idade
     - Sexo
     - Circunferências (cintura, pescoço, quadril)
     - Nível de atividade física

3. **Visualizando Resultados**
   - Os resultados aparecem logo após o cálculo
   - Cada métrica inclui interpretação detalhada
   - A medição é automaticamente salva no histórico

### Consultando Histórico

1. Clique no ícone de histórico (⏱) no topo da tela principal
2. Visualize todas as suas medições anteriores
3. Toque em qualquer item para ver detalhes completos

### Validação de Entradas

O aplicativo valida automaticamente:
- ❌ Valores negativos ou zero
- ❌ Pesos fora da faixa 20-300 kg
- ❌ Alturas fora da faixa 50-250 cm
- ❌ Idades fora da faixa 1-120 anos

## 💻 Requisitos

### Desenvolvimento
- Android Studio Hedgehog ou superior
- JDK 11 ou superior
- Gradle 8.0+
- Kotlin 1.9+

### Execução
- Android 7.0 (API 24) ou superior
- ~20 MB de espaço livre

## 🚀 Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/feliperscunha/CalculadoraDeIMC.git
cd CalculadoraDeIMC
```

2. **Abra no Android Studio**
   - File → Open → Selecione a pasta do projeto

3. **Sincronize as dependências**
   - O Android Studio sincronizará automaticamente
   - Ou: Tools → Android → Sync Project with Gradle Files

4. **Execute o aplicativo**
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique em Run (▶️) ou pressione Shift + F10

## 🔄 Melhorias Futuras

### Funcionalidades Planejadas

- [ ] **Gráficos de Evolução**
  - Visualização gráfica da evolução do IMC ao longo do tempo
  - Gráfico de peso e outras métricas
  - Biblioteca: MPAndroidChart ou Vico

- [ ] **Metas e Objetivos**
  - Definição de metas de peso
  - Acompanhamento de progresso
  - Notificações de lembretes

- [ ] **Exportação de Dados**
  - Exportar histórico em CSV/PDF
  - Compartilhamento de relatórios

- [ ] **Temas e Personalização**
  - Modo escuro/claro
  - Personalização de cores
  - Preferências de unidades (kg/lb, cm/ft)

- [ ] **Calculadoras Adicionais**
  - Índice de Massa Magra
  - Razão Cintura-Quadril
  - Índice de Conicidade

- [ ] **Backup em Nuvem**
  - Sincronização com Firebase
  - Backup automático dos dados

- [ ] **Suporte Multilíngue**
  - Inglês
  - Espanhol

### Melhorias Técnicas

- [ ] Testes unitários completos
- [ ] Testes de UI com Compose Testing
- [ ] CI/CD com GitHub Actions
- [ ] Injeção de dependências (Hilt/Koin)
- [ ] Migração para Kotlin Multiplatform

## 📄 Licença

Este projeto foi desenvolvido como parte de um trabalho acadêmico.

## 👤 Autor

**Felipe Cunha**
- GitHub: [@feliperscunha](https://github.com/feliperscunha)

## ⚠️ Aviso Legal

Este aplicativo fornece estimativas baseadas em fórmulas científicas amplamente aceitas, mas **não substitui orientação médica profissional**. Para questões relacionadas à saúde, sempre consulte um médico, nutricionista ou outro profissional de saúde qualificado.

## 🙏 Agradecimentos

- Fórmulas baseadas em pesquisas científicas reconhecidas
- Interface inspirada no Material Design 3
- Comunidade Android e Kotlin

---

**Desenvolvido com ❤️ usando Kotlin e Jetpack Compose**
