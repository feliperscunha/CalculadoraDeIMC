# Calculadora de IMC

Aplicativo Android nativo desenvolvido em Kotlin que permite aos usuários calcular e acompanhar importantes métricas de saúde.

## ✒️ Funcionalidades

O aplicativo oferece as seguintes funcionalidades:

-   **Cálculo de IMC (Índice de Massa Corporal):** Com base no peso e altura, o app calcula o IMC e fornece uma classificação (e.g., Abaixo do peso, Normal, Sobrepeso).
-   **Métricas Adicionais de Saúde:** Além do IMC, o aplicativo calcula:
    -   **TMB (Taxa Metabólica Basal):** A quantidade de calorias que o corpo queima em repouso.
    -   **Peso Ideal:** Uma faixa de peso considerada saudável para a altura informada.
    -   **Calorias Diárias:** Estimativa do consumo calórico diário necessário para manter o peso atual.
-   **Histórico de Cálculos:** Todos os resultados são salvos localmente, permitindo que o usuário acompanhe seu progresso ao longo do tempo.
-   **Visualização de Detalhes:** Cada item do histórico pode ser acessado para exibir todos os dados do cálculo.

## 📱 Telas

A interface do usuário é simples e intuitiva, dividida em três telas principais:

1.  **Home:** A tela inicial onde o usuário insere seu peso e altura para realizar um novo cálculo. Os resultados são exibidos imediatamente.
2.  **Histórico:** Apresenta uma lista com todos os cálculos já realizados, mostrando um resumo de cada um.
3.  **Detalhes:** Ao selecionar um item no histórico, esta tela é aberta, exibindo todas as informações detalhadas daquele cálculo específico, como IMC, TMB, peso ideal, etc.

## 🛠️ Arquitetura e Tecnologias

O projeto foi desenvolvido seguindo as melhores práticas e as tecnologias mais recentes do ecossistema Android:

-   **Linguagem:** 100% [Kotlin](https://kotlinlang.org/).
-   **Interface de Usuário:** [Jetpack Compose](https://developer.android.com/jetpack/compose), o toolkit moderno do Android para construir UIs nativas.
-   **Arquitetura:** **MVVM (Model-View-ViewModel)**, que separa a lógica de negócio da interface do usuário, facilitando a manutenção e testabilidade.
-   **Injeção de Dependência:** [Hilt](https://dagger.dev/hilt/), para gerenciar as dependências e o ciclo de vida dos objetos.
-   **Persistência de Dados:** [Room](https://developer.android.com/training/data-storage/room), uma biblioteca de persistência que fornece uma camada de abstração sobre o SQLite para permitir acesso robusto ao banco de dados.
-   **Navegação:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation), para gerenciar a navegação entre as telas do aplicativo.