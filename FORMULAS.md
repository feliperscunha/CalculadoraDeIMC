# Fórmulas e Cálculos de Saúde

Este documento detalha todas as fórmulas utilizadas no aplicativo Calculadora de IMC para calcular os diferentes indicadores de saúde.

## 1. IMC (Índice de Massa Corporal) / BMI (Body Mass Index)

### Fórmula
```
IMC = peso (kg) / altura² (m²)
```

### Classificação (OMS)
| Faixa de IMC | Classificação |
|--------------|---------------|
| < 18.5 | Abaixo do Peso |
| 18.5 - 24.9 | Peso Normal |
| 25.0 - 29.9 | Sobrepeso |
| 30.0 - 34.9 | Obesidade (Grau 1) |
| 35.0 - 39.9 | Obesidade Severa (Grau 2) |
| ≥ 40.0 | Obesidade Mórbida (Grau 3) |

### Implementação
- Arquivo: `domain/usecase/CalculateBMIUseCase.kt`
- O peso deve estar em quilogramas
- A altura é convertida de centímetros para metros (dividindo por 100)
- O resultado é classificado de acordo com as faixas da OMS

---

## 2. TMB (Taxa Metabólica Basal) / BMR (Basal Metabolic Rate)

### Fórmula: Mifflin-St Jeor (1990)

Esta é a equação mais precisa para populações modernas.

#### Para Homens:
```
TMB = (10 × peso em kg) + (6.25 × altura em cm) - (5 × idade em anos) + 5
```

#### Para Mulheres:
```
TMB = (10 × peso em kg) + (6.25 × altura em cm) - (5 × idade em anos) - 161
```

### Descrição
A TMB representa o número de calorias que seu corpo queima em repouso para manter funções vitais como:
- Respiração
- Circulação sanguínea
- Temperatura corporal
- Produção de células
- Processamento de nutrientes

### Implementação
- Arquivo: `domain/usecase/CalculateBMRUseCase.kt`
- Requer: peso, altura, idade e sexo
- Resultado em calorias por dia

---

## 3. Percentual de Gordura Corporal

### Fórmula: Método da Marinha dos EUA (U.S. Navy Method)

Este método utiliza circunferências corporais para estimar o percentual de gordura.

#### Para Homens:
```
% Gordura = 86.010 × log₁₀(cintura - pescoço) - 70.041 × log₁₀(altura) + 36.76
```

#### Para Mulheres:
```
% Gordura = 163.205 × log₁₀(cintura + quadril - pescoço) - 97.684 × log₁₀(altura) - 78.387
```

Onde:
- Todas as medidas são em centímetros
- Cintura: medida na altura do umbigo
- Pescoço: medida logo abaixo do pomo de Adão
- Quadril (mulheres): medida na parte mais larga dos quadris

### Classificação

#### Homens:
| Categoria | % Gordura |
|-----------|-----------|
| Gordura Essencial | 2-5% |
| Atleta | 6-13% |
| Fitness | 14-17% |
| Média | 18-24% |
| Obeso | 25%+ |

#### Mulheres:
| Categoria | % Gordura |
|-----------|-----------|
| Gordura Essencial | 10-13% |
| Atleta | 14-20% |
| Fitness | 21-24% |
| Média | 25-31% |
| Obeso | 32%+ |

### Implementação
- Arquivo: `domain/usecase/CalculateBodyFatUseCase.kt`
- Requer: cintura, pescoço, altura, sexo
- Para mulheres: também requer medida do quadril
- Resultado em porcentagem

---

## 4. Peso Ideal

### Fórmula: Fórmula de Devine (1974)

Esta é uma das fórmulas mais utilizadas em contextos médicos.

#### Para Homens (altura ≥ 152.4 cm):
```
Peso Ideal = 50 + 2.3 × (altura em polegadas - 60)
```

#### Para Mulheres (altura ≥ 152.4 cm):
```
Peso Ideal = 45.5 + 2.3 × (altura em polegadas - 60)
```

Para alturas menores que 152.4 cm (5 pés):
- Homens: 50 kg
- Mulheres: 45.5 kg

### Conversão
1 polegada = 2.54 cm

### Implementação
- Arquivo: `domain/usecase/CalculateIdealWeightUseCase.kt`
- Requer: altura e sexo
- Altura é convertida de centímetros para polegadas
- Resultado em quilogramas

---

## 5. Necessidade Calórica Diária (TDEE)

### Fórmula: Total Daily Energy Expenditure
```
TDEE = TMB × Fator de Atividade
```

### Fatores de Atividade

| Nível de Atividade | Fator | Descrição |
|--------------------|-------|-----------|
| Sedentário | 1.2 | Pouco ou nenhum exercício |
| Levemente Ativo | 1.375 | Exercício leve 1-3 dias/semana |
| Moderadamente Ativo | 1.55 | Exercício moderado 3-5 dias/semana |
| Muito Ativo | 1.725 | Exercício intenso 6-7 dias/semana |
| Extremamente Ativo | 1.9 | Exercício muito intenso, trabalho físico |

### Metas Calóricas

Com base no TDEE, podem ser estabelecidas as seguintes metas:

- **Manutenção de Peso**: TDEE (sem modificação)
- **Perda de Peso**: TDEE - 500 calorias/dia
  - Resulta em aproximadamente 0.5 kg de perda por semana
- **Ganho de Peso**: TDEE + 500 calorias/dia
  - Resulta em aproximadamente 0.5 kg de ganho por semana

### Implementação
- Arquivo: `domain/usecase/CalculateDailyCaloricNeedsUseCase.kt`
- Requer: TMB e nível de atividade física
- Resultado em calorias por dia

---

## Validação de Entradas

O aplicativo implementa validação rigorosa para garantir que os dados inseridos sejam realistas e seguros:

### Limites de Validação

| Campo | Mínimo | Máximo |
|-------|--------|--------|
| Peso | 20 kg | 300 kg |
| Altura | 50 cm | 250 cm |
| Idade | 1 ano | 120 anos |
| Circunferências | 10 cm | 200 cm |

### Implementação
- Arquivo: `domain/usecase/ValidateInputUseCase.kt`
- Previne valores negativos, nulos ou fora dos limites razoáveis
- Fornece mensagens de erro claras e específicas

---

## Referências

1. **IMC/BMI**: World Health Organization (WHO)
2. **TMB**: Mifflin, M. D., et al. (1990). "A new predictive equation for resting energy expenditure in healthy individuals." The American Journal of Clinical Nutrition.
3. **Gordura Corporal**: U.S. Navy body fat calculator
4. **Peso Ideal**: Devine, B. J. (1974). "Gentamicin therapy." Drug Intelligence & Clinical Pharmacy.
5. **TDEE**: Katch, F., McArdle, W. (1996). "Introduction to Nutrition, Exercise, and Health."

---

## Notas Importantes

⚠️ **Aviso Médico**: Todos os cálculos fornecidos são estimativas baseadas em fórmulas amplamente aceitas, mas não substituem uma avaliação médica profissional. Para questões de saúde específicas, sempre consulte um médico, nutricionista ou outro profissional de saúde qualificado.

⚠️ **Limitações**:
- Os cálculos podem não ser precisos para:
  - Atletas de alto rendimento
  - Crianças e adolescentes em crescimento
  - Mulheres grávidas ou lactantes
  - Pessoas com condições médicas específicas
  - Idosos com perda de massa muscular significativa

⚠️ **Gordura Corporal**:
- O método da Marinha é uma estimativa e pode ter margem de erro
- Para medições mais precisas, considere métodos como DEXA, bioimpedância ou plicometria profissional
