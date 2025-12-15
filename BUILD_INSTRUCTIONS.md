# Instruções de Build - Calculadora de IMC

## Requisitos do Sistema

### Software Necessário
- **Android Studio**: Hedgehog (2023.1.1) ou superior
- **JDK**: 11 ou superior
- **Gradle**: 8.0+ (incluído no wrapper)
- **SDK Android**: API 24 (Android 7.0) ou superior
- **Conexão à Internet**: Necessária para download de dependências

### Sistema Operacional
- Windows 10/11
- macOS 10.14+
- Linux (Ubuntu 20.04+ ou equivalente)

## Passo a Passo

### 1. Clonar o Repositório

```bash
git clone https://github.com/feliperscunha/CalculadoraDeIMC.git
cd CalculadoraDeIMC
```

### 2. Abrir no Android Studio

1. Abra o Android Studio
2. Selecione `File → Open`
3. Navegue até a pasta do projeto
4. Clique em `OK`
5. Aguarde o Android Studio indexar o projeto

### 3. Sincronizar Dependências

O Android Studio automaticamente tentará sincronizar as dependências. Se não, siga:

1. Clique em `File → Sync Project with Gradle Files`
2. Ou clique no ícone de sincronização 🔄 na barra de ferramentas
3. Aguarde o download de todas as dependências

**Tempo estimado**: 2-5 minutos (dependendo da conexão)

### 4. Configurar SDK

Se o Android Studio solicitar, configure o SDK:

1. `Tools → SDK Manager`
2. Na aba `SDK Platforms`, instale:
   - Android 14.0 (API 35) - recomendado
   - Android 7.0 (API 24) - mínimo
3. Na aba `SDK Tools`, verifique se está instalado:
   - Android SDK Build-Tools
   - Android SDK Platform-Tools
   - Android Emulator

### 5. Construir o Projeto

#### Opção A: Via Interface Gráfica
1. Clique em `Build → Make Project` ou pressione `Ctrl+F9` (Cmd+F9 no Mac)
2. Aguarde a compilação

#### Opção B: Via Linha de Comando
```bash
# No Windows
.\gradlew.bat assembleDebug

# No macOS/Linux
./gradlew assembleDebug
```

**Tempo estimado**: 3-10 minutos na primeira vez

### 6. Executar o Aplicativo

#### Em um Emulador
1. Clique em `Tools → Device Manager`
2. Crie um novo dispositivo virtual:
   - Recomendado: Pixel 5 com API 35
   - Mínimo: Qualquer dispositivo com API 24+
3. Inicie o emulador
4. Clique no botão `Run` (▶️) ou pressione `Shift+F10`

#### Em um Dispositivo Físico
1. Habilite o modo desenvolvedor no seu Android:
   - `Configurações → Sobre o telefone`
   - Toque 7 vezes em "Número da compilação"
2. Habilite `Depuração USB`:
   - `Configurações → Sistema → Opções do desenvolvedor`
   - Ative "Depuração USB"
3. Conecte o dispositivo via USB
4. Aceite a permissão no dispositivo
5. Clique no botão `Run` (▶️) no Android Studio

## Resolução de Problemas

### Problema: "Plugin not found"

**Sintoma**: Erro ao sincronizar Gradle mencionando que plugins não foram encontrados.

**Solução**:
1. Verifique sua conexão com a internet
2. Verifique se você consegue acessar:
   - `https://dl.google.com`
   - `https://repo.maven.apache.org`
3. Se estiver atrás de um proxy, configure:
   - `File → Settings → Appearance & Behavior → System Settings → HTTP Proxy`

### Problema: "SDK not found"

**Sintoma**: Erro mencionando que o Android SDK não foi encontrado.

**Solução**:
1. `File → Project Structure → SDK Location`
2. Verifique se o caminho do SDK está correto
3. Se não estiver configurado, clique em "Edit" e instale o SDK

### Problema: "Gradle sync failed"

**Sintoma**: A sincronização do Gradle falha repetidamente.

**Soluções**:
1. Limpe o cache do Gradle:
   ```bash
   # Windows
   .\gradlew.bat clean
   
   # macOS/Linux
   ./gradlew clean
   ```

2. Invalide caches do Android Studio:
   - `File → Invalidate Caches → Invalidate and Restart`

3. Delete pastas de build:
   - Delete `.gradle` na pasta do projeto
   - Delete `build` em `app/build`

### Problema: "Out of Memory"

**Sintoma**: Erro de memória durante a compilação.

**Solução**:
Edite `gradle.properties` e adicione/aumente:
```properties
org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m
```

### Problema: "Manifest merger failed"

**Sintoma**: Erro ao mesclar manifestos.

**Solução**:
Verifique se não há conflitos no `AndroidManifest.xml`. Se houver, adicione ferramentas de merge no manifesto.

### Problema: Compilação lenta

**Soluções**:
1. Habilite builds paralelos em `gradle.properties`:
   ```properties
   org.gradle.parallel=true
   org.gradle.caching=true
   ```

2. Use o daemon do Gradle:
   ```properties
   org.gradle.daemon=true
   ```

## Verificação da Instalação

Após o build bem-sucedido, você deve ver:

```
BUILD SUCCESSFUL in Xs
```

E o APK deve estar em:
```
app/build/outputs/apk/debug/app-debug.apk
```

## Testando o Aplicativo

### Teste Básico
1. Abra o aplicativo
2. Insira:
   - Altura: 170 cm
   - Peso: 70 kg
3. Clique em "CALCULAR"
4. Verifique se o IMC é exibido (~24.22)

### Teste de Histórico
1. Faça pelo menos 2 cálculos diferentes
2. Clique no ícone de histórico (⏱)
3. Verifique se as medições aparecem
4. Clique em uma medição para ver detalhes

### Teste de Validação
1. Tente inserir valores inválidos:
   - Altura: 0 → Deve mostrar erro
   - Peso: -10 → Deve mostrar erro
   - Altura: 300 → Deve mostrar erro

## Estrutura de Build

### Módulos
- `:app` - Aplicação principal

### Variantes de Build
- **debug** - Build de desenvolvimento (com logs)
- **release** - Build para produção (otimizado)

### Tarefas Gradle Úteis

```bash
# Compilar debug
./gradlew assembleDebug

# Compilar release
./gradlew assembleRelease

# Limpar build
./gradlew clean

# Executar testes unitários
./gradlew test

# Executar testes instrumentados
./gradlew connectedAndroidTest

# Gerar relatório de dependências
./gradlew app:dependencies

# Verificar atualizações de dependências
./gradlew dependencyUpdates
```

## Configurações de Build

### Versões Importantes

Arquivo: `gradle/libs.versions.toml`

```toml
[versions]
agp = "8.6.0"                      # Android Gradle Plugin
kotlin = "2.0.21"                  # Kotlin
ksp = "2.0.21-1.0.28"             # Kotlin Symbol Processing
room = "2.6.1"                     # Room Database
navigationCompose = "2.8.5"        # Navigation Compose
```

### SDK Target

Arquivo: `app/build.gradle.kts`

```kotlin
compileSdk = 35
targetSdk = 35
minSdk = 24
```

**Compatibilidade**: Android 7.0 (API 24) até Android 15 (API 35)

## Build para Produção

### Gerar APK Release

```bash
./gradlew assembleRelease
```

APK estará em: `app/build/outputs/apk/release/`

### Assinar o APK

Para distribuir na Play Store, você precisa assinar o APK:

1. Crie uma keystore:
```bash
keytool -genkey -v -keystore release.keystore -alias calculadora_imc -keyalg RSA -keysize 2048 -validity 10000
```

2. Configure `app/build.gradle.kts`:
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("release.keystore")
            storePassword = "sua_senha"
            keyAlias = "calculadora_imc"
            keyPassword = "sua_senha"
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

3. Build:
```bash
./gradlew assembleRelease
```

⚠️ **Importante**: Nunca comite a keystore ou senhas no Git!

## Depuração

### Logs do Aplicativo

Use Logcat no Android Studio:
1. `View → Tool Windows → Logcat`
2. Filtre por tag: `CalculadoraIMC`

### Debugging

1. Coloque breakpoints clicando na margem esquerda do editor
2. Execute em modo debug (🐛) em vez de run (▶️)
3. O app pausará nos breakpoints

### Inspecionar Banco de Dados

1. `View → Tool Windows → App Inspection`
2. Selecione o dispositivo em execução
3. Navegue até `Database Inspector`
4. Veja a tabela `measurements`

## Performance

### Primeira Compilação
- **Tempo estimado**: 5-10 minutos
- Baixa todas as dependências
- Constrói todo o projeto

### Compilações Subsequentes
- **Tempo estimado**: 30-60 segundos
- Usa cache do Gradle
- Apenas recompila o que mudou

## Suporte

Se você encontrar problemas não listados aqui:

1. Verifique o [Stack Overflow](https://stackoverflow.com/questions/tagged/android-studio)
2. Consulte a [documentação oficial do Android](https://developer.android.com)
3. Abra uma issue no repositório do GitHub

## Referências

- [Documentação do Android Studio](https://developer.android.com/studio/intro)
- [Guia do Gradle](https://docs.gradle.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/training/data-storage/room)

---

**Última atualização**: Dezembro 2024  
**Versão do projeto**: 1.0.0
