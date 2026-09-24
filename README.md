# Snake KMP

Mini game da cobrinha com **Kotlin Multiplatform** e **Compose Multiplatform** para Android e iOS.

## Estrutura

- `composeApp`: módulo compartilhado com lógica e UI do jogo.
  - `commonMain`: estado, regras do jogo e interface Compose.
  - `androidMain`: `MainActivity` e manifesto Android.
  - `iosMain`: `MainViewController` para integração com SwiftUI.

## Requisitos

- JDK 21 para executar o Gradle 8.7 (aceita Java 8 a 21; Java 25 não é compatível)
- Android SDK (compileSdk 34)

O JDK do Gradle está definido como `jbr-21` em `.idea/gradle.xml`. No Android Studio ou no IntelliJ, confira em **Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JDK** se **jbr-21** (ou outro JDK 21) está selecionado. Se essa opção não aparecer, baixe o JetBrains Runtime 21 ou instale um JDK 21 e selecione-o.

## Build

```bash
./gradlew :composeApp:assembleDebug
```

## iOS

O módulo exporta `MainViewController()` em `iosMain`, que pode ser usado em um app SwiftUI/UIViewControllerRepresentable.
